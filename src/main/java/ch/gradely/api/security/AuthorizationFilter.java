package ch.gradely.api.security;

import ch.gradely.api.database.objects.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationFilter extends OncePerRequestFilter {

	private static final String HEADER = "Token";
	private static final String PREFIX = "Bearer ";
	private static final String SECRET = "-8IU2wt^y|j;~PBe)nTKY7CUeXYyx4p20lzEnQEYE59>65FaFqQnZ$4rHpeo*>n";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (checkToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}

	private boolean checkToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
	}

	private Claims validateToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
	}

	private void setUpAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	public static int getTokenId(Token token) {
		String tokenString = token.getToken().replace(PREFIX, "");
		int position = tokenString.lastIndexOf('.');
		tokenString = tokenString.substring(0, position + 1);
		return Integer.parseInt(Jwts.parser().parseClaimsJwt(tokenString).getBody().getId());
	}

	public static Token createToken(User user) {
		List<GrantedAuthority> grantedAuthorities;
		if (user.isAdmin()) {
			grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		} else {
			grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_USER");
		}

		String token = Jwts
				.builder()
				.setId(Integer.toString(user.getId()))
				.setSubject(user.getUsername())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						SECRET.getBytes()).compact();

		return new Token(PREFIX + token);
	}
}
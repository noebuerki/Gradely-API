create table if not exists user
(
    id       int auto_increment
        primary key,
    username varchar(50)          not null,
    password varchar(255)         not null,
    isAdmin  tinyint(1) default 0 not null,
    constraint unique_username
        unique (username)
);

create table if not exists school
(
    id       int auto_increment
        primary key,
    name     varchar(50) not null,
    semester int         not null,
    user_id  int         not null,
    constraint school_user
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table if not exists subject
(
    id        int auto_increment
        primary key,
    name      varchar(50) not null,
    user_id   int         null,
    school_id int         not null,
    constraint subject_school
        foreign key (school_id) references school (id)
            on update cascade on delete cascade,
    constraint subject_user
        foreign key (user_id) references user (id)
);

create table if not exists semester
(
    id         int auto_increment
        primary key,
    user_id    int not null,
    subject_id int not null,
    semester   int not null,
    constraint semester_subject
        foreign key (subject_id) references subject (id)
            on update cascade on delete cascade,
    constraint semester_user
        foreign key (user_id) references user (id)
);

create table if not exists grade
(
    id          int auto_increment
        primary key,
    user_id     int         not null,
    semester_id int         not null,
    name        varchar(45) not null,
    value       double      not null,
    weight      double      not null,
    constraint grade_semester
        foreign key (semester_id) references semester (id)
            on update cascade on delete cascade,
    constraint grade_user
        foreign key (user_id) references user (id)
);
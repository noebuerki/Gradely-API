CREATE SCHEMA gradely;

CREATE TABLE gradely.`user` ( 
	id                   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	username             varchar(50)  NOT NULL    ,
	email                varchar(100)  NOT NULL    ,
	password             varchar(255)  NOT NULL    ,
	admin                boolean  NOT NULL DEFAULT false   
 ) engine=InnoDB;

CREATE TABLE gradely.school ( 
	id                   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	name                 varchar(50)  NOT NULL    ,
	user_id              int  NOT NULL    ,
	semester             int  NOT NULL    
 ) engine=InnoDB;

CREATE TABLE gradely.subject ( 
	id                   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	name                 varchar(50)  NOT NULL    ,
	school_id            int  NOT NULL    
 ) engine=InnoDB;

CREATE TABLE gradely.`subject-semester` ( 
	id                   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	subject_id           int  NOT NULL    ,
	semester             int  NOT NULL    ,
	CONSTRAINT `unique-subject-semester` UNIQUE ( subject_id, semester ) 
 ) engine=InnoDB;

CREATE TABLE gradely.grade ( 
	id                   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	user_id              int  NOT NULL    ,
	semester_id          int  NOT NULL    ,
	name                 varchar(50)  NOT NULL    ,
	value                double  NOT NULL    ,
	weight               double  NOT NULL    
 ) engine=InnoDB;

ALTER TABLE gradely.grade ADD CONSTRAINT grade_semester FOREIGN KEY ( semester_id ) REFERENCES gradely.`subject-semester`( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE gradely.grade ADD CONSTRAINT grade_user FOREIGN KEY ( user_id ) REFERENCES gradely.`user`( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE gradely.school ADD CONSTRAINT school_user FOREIGN KEY ( user_id ) REFERENCES gradely.`user`( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE gradely.subject ADD CONSTRAINT subject_school FOREIGN KEY ( school_id ) REFERENCES gradely.school( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE gradely.`subject-semester` ADD CONSTRAINT `fk_subject-semester_school` FOREIGN KEY ( subject_id ) REFERENCES gradely.subject( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;


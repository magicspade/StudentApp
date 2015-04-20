
    create table Project (
        projectID bigint not null auto_increment,
        ownerID bigint,
        title varchar(255),
        primary key (projectID)
    );

    create table Student (
        studentID bigint not null auto_increment,
        address varchar(255),
        age integer not null,
        name varchar(255),
        primary key (studentID)
    );

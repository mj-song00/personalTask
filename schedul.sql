CREATE TABLE `post`(
 id INT not null primary key auto_increment,
 contents varchar(100) not null,
 password varchar(20) not null,
 manager varchar(10) not null,
 createdAt varchar(12) not null,
 updatedAt varchar(12) not null
);


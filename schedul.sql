CREATE TABLE `post`(
 id INT not null primary key auto_increment,
 contents varchar(500) not null,
 password varchar(60) not null,
 manager varchar(100) not null,
 createdAt varchar(300),
 updatedAt varchar(300)
);


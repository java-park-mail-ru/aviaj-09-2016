CREATE TABLE User(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    login VARCHAR(30) NOT NULL,
                    email VARCHAR(50) NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    rating BIGINT NOT NULL DEFAULT 0);

CREATE TABLE Plane(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(20) NOT NULL,
                    source VARCHAR(50) NOT NULL);

CREATE TABLE Plane(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                   title VARCHAR(50) NOT NULL,
                   source VARCHAR(50) NOT NULL);
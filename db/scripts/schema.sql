CREATE TABLE employee (
    id NUMBER AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    age NUMBER NOT NULL,
    expiration_date DATE NOT NULL,
    PRIMARY KEY (id)
)
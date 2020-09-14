CREATE TABLE employee (
    id SERIAL primary KEY,
    firstName VARCHAR(200) NOT NULL,
    lastName VARCHAR(200) NOT NULL,
    age INTEGER NOT NULL
);

create table contract (
    employee_id INTEGER PRIMARY KEY REFERENCES employee(id),
	type VARCHAR(50) NOT NULL,
    expiration_date DATE NOT NULL
);
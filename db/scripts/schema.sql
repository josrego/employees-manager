CREATE TABLE employee (
    id SERIAL primary KEY,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    age INTEGER NOT NULL
);

create table contract (
    employee_id INTEGER PRIMARY KEY REFERENCES employee(id),
    expiration_date DATE NOT NULL
);
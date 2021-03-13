INSERT INTO employee (first_name, last_name, date_of_birth)
VALUES ('Name 1', 'Last Name 1', '2000-01-01'),
('Name 2', 'Last Name 2', '2000-02-02'),
('Name 3', 'Last Name 3', '2000-03-03');

INSERT INTO address (employee_id, line1, city, state, country, zip_code) VALUES
(select id from employee where first_name = 'Name 1', 'Line 1', 'City 1', 'State 1', 'Country 1', 'ZipCode 1');

INSERT INTO address (employee_id, line1, city, state, country, zip_code) VALUES
(select id from employee where first_name = 'Name 2', 'Line 2', 'City 2', 'State 2', 'Country 2', 'ZipCode 2');

INSERT INTO address (employee_id, line1, city, state, country, zip_code) VALUES
(select id from employee where first_name = 'Name 3', 'Line 3', 'City 3', 'State 3', 'Country 3', 'ZipCode 3');
create extension tablefunc;

create table CarSales
(
	car_name varchar(50),
	month varchar(20),
	amount integer
);

INSERT INTO CarSales (car_name, month, amount) VALUES
('Maruthi Suzuki', 'January', 100),
('Maruthi Suzuki', 'February', 150),
('Maruthi Suzuki', 'March', 200),
('Hyundai', 'January', 50),
('Hyundai', 'February', 75),
('Hyundai', 'March', 120),
('Toyota', 'January', 80),
('Toyota', 'February', 95),
('Toyota', 'March', 130);

select * from CarSales

SELECT * FROM crosstab(
    'SELECT car_name, month, amount FROM CarSales ORDER BY 1, 2'
) AS pivot_table(car_name VARCHAR, "January" INTEGER, "February" INTEGER, "March" INTEGER);

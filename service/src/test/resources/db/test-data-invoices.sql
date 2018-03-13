create table T_INVOICES (ID integer identity primary key, NAME varchar(255), CATEGORY_ID integer, DETAILS varchar(255), CREATION date, LAST_MODIFICATION date)

insert into T_INVOICES (ID, NAME, CATEGORY_ID, DETAILS, CREATION, LAST_MODIFICATION) values (1, 'Nazov#1', 1, 'Detail#1', '2018-03-11', '2018-03-11')

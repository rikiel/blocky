CREATE TABLE T_INVOICES (
  ID                INTEGER IDENTITY PRIMARY KEY,
  NAME              VARCHAR(255),
  CATEGORY_ID       INTEGER,
  DETAILS           VARCHAR(255),
  CREATION          DATE,
  LAST_MODIFICATION DATE NULL
);

INSERT INTO T_INVOICES (ID, NAME, CATEGORY_ID, DETAILS, CREATION, LAST_MODIFICATION) VALUES (1, 'Nazov#1', 123, 'Detail#1', '2018-03-11', '2018-03-11')

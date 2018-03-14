CREATE TABLE T_ATTACHMENTS (
  ID           INTEGER IDENTITY PRIMARY KEY,
  INVOICE_ID   INTEGER,
  NAME         VARCHAR(255),
  FILE_NAME    VARCHAR(255),
  MIME_TYPE    VARCHAR(255),
  TYPE         INTEGER,
  FILE_CONTENT BLOB
);

INSERT INTO T_ATTACHMENTS (ID, INVOICE_ID, NAME, FILE_NAME, MIME_TYPE, TYPE, FILE_CONTENT)
VALUES (1, 1, 'Name#1', 'FileName#1', 'MimeType', 1, hextoraw('41484f4a'))

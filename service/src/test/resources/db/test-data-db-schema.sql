DROP TABLE T_ATTACHMENTS IF EXISTS;
DROP TABLE T_CST_CATEGORY IF EXISTS;
DROP TABLE T_INVOICES IF EXISTS;
DROP TABLE DUAL_INVOICE_ID IF EXISTS;
DROP TABLE DUAL_ATTACHMENT_ID IF EXISTS;
DROP SEQUENCE S_INVOICE_ID IF EXISTS;
DROP SEQUENCE S_ATTACHMENT_ID IF EXISTS;

CREATE TABLE T_ATTACHMENTS (
  ID           INTEGER IDENTITY PRIMARY KEY,
  INVOICE_ID   INTEGER,
  NAME         VARCHAR(255),
  FILE_NAME    VARCHAR(255),
  MIME_TYPE    VARCHAR(255),
  TYPE         INTEGER,
  FILE_CONTENT BLOB
);

CREATE TABLE T_CST_CATEGORY (
  ID    INTEGER IDENTITY PRIMARY KEY,
  NAME  VARCHAR(255),
  DESCR VARCHAR(255)
);

CREATE TABLE T_INVOICES (
  ID                INTEGER IDENTITY PRIMARY KEY,
  NAME              VARCHAR(255),
  CATEGORY_ID       INTEGER,
  DETAILS           VARCHAR(255),
  CREATION          DATE,
  LAST_MODIFICATION DATE NULL
);

CREATE SEQUENCE S_INVOICE_ID START WITH 10;
CREATE TABLE DUAL_INVOICE_ID (ZERO INTEGER);
INSERT INTO DUAL_INVOICE_ID VALUES(0);

CREATE SEQUENCE S_ATTACHMENT_ID START WITH 10;
CREATE TABLE DUAL_ATTACHMENT_ID (ZERO INTEGER);
INSERT INTO DUAL_ATTACHMENT_ID VALUES(0);

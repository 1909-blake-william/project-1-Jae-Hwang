DROP USER ers_user CASCADE;

CREATE USER ers_user
IDENTIFIED BY ers_pass
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT CONNECT TO ers_user;
GRANT RESOURCE TO ers_user;
GRANT CREATE SESSION TO ers_user;
GRANT CREATE TABLE TO ers_user;
GRANT CREATE VIEW TO ers_user;

conn ers_user/ers_pass;

/************************************
Tables and sequences
************************************/
CREATE SEQUENCE ers_user_roles_id_seq;
CREATE TABLE ers_user_roles (
    ers_user_role_id NUMBER PRIMARY KEY,
    user_role VARCHAR2(10) NOT NULL
);

CREATE SEQUENCE ers_user_id_seq;
CREATE TABLE ers_users (
    ers_user_id NUMBER PRIMARY KEY,
    ers_username VARCHAR2(50) NOT NULL UNIQUE,
    ers_password VARCHAR2(50) NOT NULL,
    user_first_name VARCHAR2(100) NOT NULL,
    user_last_name VARCHAR2(100) NOT NULL,
    user_email VARCHAR2(150) NOT NULL UNIQUE,
    user_role_id NUMBER NOT NULL REFERENCES ers_user_roles(ers_user_role_id)
);

CREATE SEQUENCE ers_reimb_type_id_seq;
CREATE TABLE ers_reimbursement_type (
    reimb_type_id NUMBER PRIMARY KEY,
    reimb_type VARCHAR2(10) NOT NULL
);

CREATE SEQUENCE ers_reimb_status_id_seq;
CREATE TABLE ers_reimbursement_status (
    reimb_status_id NUMBER PRIMARY KEY,
    reimb_status VARCHAR2(10) NOT NULL
);

/***********************************************************************
Data for status, needed to create default data for reimb table
***********************************************************************/
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Requested');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Pending');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Denied');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Approved');

/************************************
rest of Tables and sequences
************************************/
CREATE SEQUENCE ers_reimb_id_seq;
CREATE TABLE ers_reimbursement (
    reimb_id NUMBER PRIMARY KEY,
    reimb_amount NUMBER NOT NULL,
    reimb_submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    reimb_resolved TIMESTAMP,
    reimb_description VARCHAR2(250),
    reimb_author NUMBER NOT NULL REFERENCES ers_users(ers_user_id),
    reimb_resolver NUMBER REFERENCES ers_users(ers_user_id),
    reimb_status_id NUMBER DEFAULT 1 NOT NULL REFERENCES ers_reimbursement_status(reimb_status_id),
    reimb_type_id NUMBER NOT NULL REFERENCES ers_reimbursement_type(reimb_type_id)
);

/*******************************************************
Stored Procedures
*******************************************************/
CREATE OR REPLACE PROCEDURE regist_reimbursement 
(amount IN NUMBER,
username IN VARCHAR2,
type_str IN VARCHAR2,
des IN VARCHAR2)
IS
a_id NUMBER;
type_id NUMBER;
BEGIN
    SELECT ers_user_id INTO a_id FROM ers_users
        WHERE ers_username = username;
    SELECT reimb_type_id INTO type_id FROM ers_reimbursement_type
        WHERE reimb_type = type_str;
    INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_description, reimb_author, reimb_type_id)
        VALUES (ers_reimb_id_seq.NEXTVAL, amount, des, a_id, type_id);
END;
/

/************************************
Rest of Data
************************************/

INSERT INTO ers_user_roles (ers_user_role_id, user_role)
    VALUES (ers_user_roles_id_seq.NEXTVAL, 'Employee');

INSERT INTO ers_user_roles (ers_user_role_id, user_role)
    VALUES (ers_user_roles_id_seq.NEXTVAL, 'Manager');


INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
    VALUES (ers_reimb_type_id_seq.NEXTVAL, 'Lodging');

INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
    VALUES (ers_reimb_type_id_seq.NEXTVAL, 'Food');

INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
    VALUES (ers_reimb_type_id_seq.NEXTVAL, 'Travel');

INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type)
    VALUES (ers_reimb_type_id_seq.NEXTVAL, 'Other');

INSERT INTO ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (ers_user_id_seq.NEXTVAL, 'potato', 'pass', 'pot', 'ato', 'pot@to.roots', 1);

INSERT INTO ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (ers_user_id_seq.NEXTVAL, 'lettuce', 'pass', 'let', 'tuce', 'let@tuce.roots', 1);

INSERT INTO ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (ers_user_id_seq.NEXTVAL, 'adm', 'adm', 'ad', 'min', 'ad@min.roots', 2);


INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 500, CURRENT_TIMESTAMP, 1, 1, 1);
    
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 1000, CURRENT_TIMESTAMP, 1, 1, 1);

INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 300, CURRENT_TIMESTAMP, 2, 1, 1);
    
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 2000, CURRENT_TIMESTAMP, 2, 1, 2);



/*******************************************************
Pagination stuff
*******************************************************/ /*
SELECT 
    reimb_id, 
    reimb_amount, 
    reimb_submitted, 
    reimb_resolved, 
    reimb_description, 
    auth.ers_username author, 
    res.ers_username resolver, 
    reimb_type,
    reimb_status
FROM     
(SELECT reimb.*,row_number() 
    over (ORDER BY reimb.reimb_id ASC) line_number
    FROM ers_reimbursement reimb)
JOIN ers_users auth ON reimb_author = auth.ers_user_id
FULL JOIN ers_users res ON reimb_resolver = res.ers_user_id
JOIN ers_reimbursement_status status USING (reimb_status_id)
JOIN ers_reimbursement_type type USING (reimb_type_id)
WHERE line_number BETWEEN 0 AND 200  ORDER BY line_number;

SELECT count(*) FROM ers_reimbursement;
*/
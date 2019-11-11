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

CREATE SEQUENCE ers_reimb_id_seq;
CREATE TABLE ers_reimbursement (
    reimb_id NUMBER PRIMARY KEY,
    reimb_amount NUMBER NOT NULL,
    reimb_submitted TIMESTAMP NOT NULL,
    reimb_resolved TIMESTAMP,
    reimb_description VARCHAR2(250),
    reimb_author NUMBER NOT NULL REFERENCES ers_users(ers_user_id),
    reimb_resolver NUMBER REFERENCES ers_users(ers_user_id),
    reimb_status_id NUMBER NOT NULL REFERENCES ers_reimbursement_status(reimb_status_id),
    reimb_type_id NUMBER NOT NULL REFERENCES ers_reimbursement_type(reimb_type_id)
);

/************************************
Tables and sequences
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
    VALUES (ers_reimb_type_id_seq.NEXTVAL, 'Others');


INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Requested');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Pending');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Denied');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status)
    VALUES (ers_reimb_status_id_seq.NEXTVAL, 'Approved');


INSERT INTO ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (ers_user_id_seq.NEXTVAL, 'potato', 'pass', 'pot', 'ato', 'pot@to.roots', 1);

INSERT INTO ers_users (ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (ers_user_id_seq.NEXTVAL, 'lettuce', 'pass', 'let', 'tuce', 'let@tuce.roots', 2);


INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 500, CURRENT_TIMESTAMP, 1, 1, 1);
    
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id)
    VALUES (ers_reimb_id_seq.NEXTVAL, 1000, CURRENT_TIMESTAMP, 1, 1, 1);
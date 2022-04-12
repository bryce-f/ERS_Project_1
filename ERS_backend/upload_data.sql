drop table if exists ers_users CASCADE;
drop table if exists ers_reimbursement;
drop table if exists ers_reimbursement_status;
drop table if exists ers_reimbursement_type;
drop table if exists ers_user_roles;

create table ers_reimbursement_status (
	reimb_status_id SERIAL primary key,
	reimb_status VARCHAR(10)

);

create table ers_reimbursement_type (
	reimb_type_id SERIAL primary key,
	reimb_type VARCHAR(10)
	
);

create table ers_user_roles (
	ers_user_role_id SERIAL primary key,
	user_role VARCHAR(10)
);


CREATE TABLE ers_users (
	ers_user_id SERIAL PRIMARY KEY,
	ers_username VARCHAR(50) UNIQUE,
	ers_password VARCHAR(50),
	user_first_name VARCHAR(100),
	user_last_name VARCHAR(100),
	user_email VARCHAR(100) UNIQUE,
	user_role_id INTEGER
);

CREATE TABLE ers_reimbursement (
	reimb_id SERIAL PRIMARY KEY,
	reimb_amount DECIMAL(16, 2),
	reimb_submitted TIMESTAMP default NOW(),
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt VARCHAR(250),
	reimb_author INTEGER,
	reimb_resolver INTEGER,
	reimb_status_id INTEGER default 1,
	reimb_type_id INTEGER,
	constraint ers_users_fk_auth foreign key (reimb_author) references ers_users(ers_user_id),
	constraint ers_users_fk_reslvr foreign key (reimb_resolver) references ers_users(ers_user_id),
	constraint ers_reimbursement_status_fk foreign key (reimb_status_id) references ers_reimbursement_status(reimb_status_id),
	constraint ers_reimbursement_type_fk foreign key (reimb_type_id) references ers_reimbursement_type(reimb_type_id)
);


insert into ers_reimbursement_status (reimb_status)
values
('pending'),
('approved'),
('denied');


insert into ers_reimbursement_type (reimb_type)
values 
('lodging'),
('travel'),
('food'),
('other');


insert into ers_user_roles (user_role)
values 
('admin'),
('employee');


INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES 
('john_doe1', 'p1', 'John', 'Doe', 'john_doe@email.com', 2),
('jane_doe2', 'p2', 'Jane', 'Doe', 'jane_doe@email.com', 2),
('brad_smith3', 'p3', 'Brad', 'Smith', 'brad_smith@email.com', 2),
('bob_jackson4', 'p4', 'Bob', 'Jackson', 'bob_jack@email.com', 2),
('monica_lewis5', 'p5', 'Monica', 'Lewis', 'monica_lewis@email.com', 2),
('ray_charles1', 'a1', 'Ray', 'Charles', 'ray_charles@email.com', 1),
('steve_wonder2', 'a2', 'Steve', 'Wonder', 'steve_wonder@email.com', 1);


insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, 
reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES 
(100.01, NOW(), null, 'Travel expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 1, null, 1, 2),
(1200.02, NOW(), null, 'expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 2, null, 2, 1),
(1500.03, NOW(), null, 'expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 6, null, 1, 3),
(563.01, NOW(), null, 'Other expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 1, null, 1, 4),
(532.02, NOW(), null, 'Travel expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 1, null, 3, 2),
(6752.03, NOW(), null, 'Travel expenses', 'https://storage.googleapis.com/project-1-bucket-bf/reimb-images/URL_vs_URI.PNG', 1, null, 2, 2);

drop view if exists all_info;

create view all_info as
(select re.reimb_id, re.reimb_amount, re.reimb_submitted, re.reimb_resolved, 
re.reimb_description, re.reimb_receipt, re.reimb_author, re.reimb_resolver, 
re.reimb_status_id, re.reimb_type_id,
rs.reimb_status, rt.reimb_type, usr.user_first_name, usr.user_last_name, usr.user_email, usr2.user_first_name as resolver_first, usr2.user_last_name as resolver_last
from ers_reimbursement re
inner join ers_reimbursement_status rs
on rs.reimb_status_id = re.reimb_status_id 
inner join ers_reimbursement_type rt
on rt.reimb_type_id = re.reimb_type_id
inner join ers_users usr
on usr.ers_user_id = re.reimb_author
inner join ers_user_roles ur
on ur.ers_user_role_id = usr.user_role_id
left join ers_users usr2
on usr2.ers_user_id = re.reimb_resolver 
);
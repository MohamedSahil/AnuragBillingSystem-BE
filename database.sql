create database payment_detail_app;
use payment_detail_app;

create table user(
	id bigint(20) primary key auto_increment ,
	username varchar(255) not null unique,
	password varchar(255) not null,
	phone_no varchar(10) not null,
	is_enabled BIT DEFAULT 1
)
ENGINE=InnoDB
AUTO_INCREMENT = 1;

insert into user(username,password,phone_no,address,name,is_enabled) values('mdsahil','mdsahil','7789861207','tyendakura','sahil',1);


create table client(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	contact_no varchar(10) NOT null default '9938111111' ,
	date_created DATETIME(6) DEFAULT NULL,
    last_updated DATETIME(6) DEFAULT NULL,
    created_by varchar(255) not null,
    updated_by varchar(255),
    active BIT DEFAULT 1,
	PRIMARY KEY (`id`)
)
ENGINE=InnoDB
AUTO_INCREMENT = 1;

insert into client(name,contact_no,date_created) values('sahil','123456789',NOW());

create table billing_detail(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	billing_date DATETIME(6) NOT NULL,
	payment_date DATETIME(6) NOT NULL,
	billing_amount INT not null,
	paid_amount INT default 0,
	date_created DATETIME(6) DEFAULT NULL,
    last_updated DATETIME(6) DEFAULT NULL,
    created_by BIGINT(20) NOT NULL,
    FOREIGN KEY (created_by) REFERENCES user (id),
    updated_by BIGINT(20),
    FOREIGN KEY (updated_by) REFERENCES user (id),
	client_id BIGINT(20) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (client_id) REFERENCES client (id)
)
ENGINE=InnoDB
AUTO_INCREMENT = 1;

insert into billing_detail(billing_date,payment_date,billing_amount,party_id) values(NOW(),now(),10000,1);

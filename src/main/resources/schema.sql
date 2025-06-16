create table bank_account{

    id bigint not null auto_increment,
    account_number varchar(30) not null,
    customer_name varchar(250) not null,
    balance decimal(19,4),
    account_type varchar(100) not null,

    primary key(id)

};
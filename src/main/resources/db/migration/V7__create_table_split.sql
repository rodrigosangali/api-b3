create table split(

    id bigint not null auto_increment,
    produto varchar(10) not null,
    data_split date not null,
    multiplo int not null,

    primary key(id)

);
create table proventos_recebidos(

    id bigint not null auto_increment,
    produto varchar(100) not null,
    data_pagamento date not null,
    tipo_evento varchar(50) not null,
    instituicao varchar(100) not null,
    quantidade bigint not null,
    preco_unitario DECIMAL(20,2) not null,
    valor_operacao DECIMAL(20,2) not null,

    primary key(id)

);
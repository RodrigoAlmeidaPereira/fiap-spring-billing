create table person (
    id bigserial not null,
    active boolean not null,
    doc varchar(255),
    enrollment varchar(255),
    name varchar(255),
    primary key (id)
);

create table transaction_ (
    id bigserial not null,
    cancelled boolean not null,
    date_transaction timestamp not null,
    quantity_plots int4 not null,
    value_transaction float8 not null,
    person_id int8,
    primary key (id)
);

alter table transaction_
   add constraint transaction_person_id_fk
   foreign key (person_id)
   references person;

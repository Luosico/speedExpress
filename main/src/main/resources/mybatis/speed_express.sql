drop
database if exists speed_express;

create table user
(
    username                varchar(20)   not null unique,
    password                varchar(100)  not null,
    phone_number            character(11) not null,
    create_time             datetime default current_timestamp,
    authorities             varchar(100),
    credentials_non_expired tinyint,
    account_non_expired     tinyint,
    account_non_locked      tinyint,
    enabled                 tinyint,
    primary key (username),
    key (username,phone_number)
);

insert into user(username,
                 password,
                 phone_number,
                 authorities,
                 credentials_non_expired,
                 account_non_expired,
                 account_non_locked,
                 enabled)
values ("u1",
        "$2a$10$2uj0zAhH7wK9vJhXZJV7cewxUA0JHZN9vrKNabDV/0gLd0mlYDBsS",
        "12345678910",
        "",
        true,
        true,
        true,
        true);
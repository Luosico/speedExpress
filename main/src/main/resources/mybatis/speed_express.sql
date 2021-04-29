drop
    database if exists speed_express;

create
    database speed_express;

create table user
(
    id                      int           not null auto_increment,
    username                varchar(20)   not null unique,
    password                varchar(100)  not null,
    phone_number            character(11) not null unique,
    create_time             datetime default current_timestamp,
    authorities             varchar(100),
    credentials_non_expired tinyint,
    account_non_expired     tinyint,
    account_non_locked      tinyint,
    enabled                 tinyint,
    primary key (id),
    key (username, phone_number, id),
    key (phone_number, id)
);

create table region
(
    region_id   int auto_increment not null,
    region_name varchar(50)        not null,
    primary key (region_id)
);

create table address
(
    address_id      int auto_increment not null,
    user_id         int                not null,
    region_id       int                not null,
    pick_up_address varchar(50),
    receive_address varchar(50),
    primary key (address_id),
    index (address_id, user_id),
    index (user_id, region_id),
    foreign key (user_id) references user (id),
    foreign key (region_id) references region (region_id)
);

create table pay
(
    pay_id      int auto_increment not null,
    fee         long               not null,
    create_time datetime default current_timestamp,
    pay_status  varchar(20),
    primary key (pay_id)
);

create table courier
(
    courier_id  int auto_increment not null,
    user_id     int                not null,
    identity_id varchar(100)       not null,
    create_time datetime default current_timestamp,
    primary key (courier_id),
    foreign key (user_id) references user (id)
);

create table express
(
    express_id      int auto_increment not null,
    address_id      int                not null,
    name            varchar(30)        not null,
    express_number  varchar(100)       not null,
    express_company varchar(100)       not null,
    express_code    varchar(50)        not null,
    phone_number    varchar(30)        not null,
    express_type    varchar(30)        not null,
    create_time     datetime default current_timestamp,
    fee             long               not null,
    remark          varchar(200),
    primary key (express_id),
    foreign key (address_id) references address (address_id)
);

create table express_order
(
    order_id     int auto_increment not null,
    user_id      int                not null,
    pay_id       int                not null,
    courier_id   int,
    express_id   int                not null,
    order_status varchar(30)        not null,
    create_time  datetime default current_timestamp,
    primary key (order_id),
    index (user_id),
    foreign key (user_id) references user (id),
    foreign key (pay_id) references pay (pay_id),
    foreign key (express_id) references express (express_id),
    foreign key (courier_id) references courier (courier_id)
);

insert into user(username,
                 password,
                 phone_number,
                 authorities,
                 credentials_non_expired,
                 account_non_expired,
                 account_non_locked,
                 enabled)
values ('admin',
        '$2a$10$2uj0zAhH7wK9vJhXZJV7cewxUA0JHZN9vrKNabDV/0gLd0mlYDBsS',
        '1234567890',
        'ROLE_ADMIN',
        1,
        1,
        1,
        1);

insert into region(region_name)
values ('湖南科技大学'),
       ('湘潭大学');
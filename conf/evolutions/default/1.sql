# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cart (
  id                            integer not null,
  total_price                   double,
  constraint pk_cart primary key (id)
);
create sequence cart_seq;

create table catalog (
  id                            integer auto_increment not null,
  userid                        integer,
  name                          varchar(255),
  des                           varchar(255),
  is_closed                     boolean,
  constraint pk_catalog primary key (id)
);

create table donation (
  id                            integer auto_increment not null,
  name                          varchar(255),
  des                           varchar(255),
  seller                        varchar(255),
  charity                       varchar(255),
  constraint pk_donation primary key (id)
);

create table item_info (
  id                            integer auto_increment not null,
  catalogid                     integer,
  name                          varchar(255),
  img_url                       varchar(255),
  price                         double,
  des                           varchar(255),
  inventory                     integer,
  seller                        varchar(255),
  seller_address                varchar(255),
  cartid                        integer,
  num_to_buy                    integer,
  constraint pk_item_info primary key (id)
);

create table payment (
  id                            integer auto_increment not null,
  userid                        integer,
  type                          integer,
  cvv                           varchar(255),
  card_num                      varchar(255),
  exp_month                     varchar(255),
  exp_year                      varchar(255),
  card_name                     varchar(255),
  account_num                   varchar(255),
  routing_num                   varchar(255),
  constraint ck_payment_type check (type in (0,1,2,3,4)),
  constraint pk_payment primary key (id)
);

create table permission (
  id                            integer not null,
  seller                        integer,
  book_keeper                   integer,
  clerk                         integer,
  cashier                       integer,
  constraint pk_permission primary key (id)
);
create sequence permission_seq;

create table receipt (
  id                            integer auto_increment not null,
  total_price                   double,
  payment_id                    integer,
  userid                        integer,
  date                          timestamp not null,
  constraint pk_receipt primary key (id)
);

create table transaction (
  id                            integer auto_increment not null,
  receipt_id                    integer,
  catalog_id                    integer,
  item_id                       integer,
  total_price                   double,
  quantity                      integer,
  constraint pk_transaction primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  address                       varchar(255),
  phone                         varchar(255),
  super_user                    integer,
  sell_admin                    integer,
  seller                        integer,
  book_keeper                   integer,
  cashier                       integer,
  clerk                         integer,
  guest                         integer,
  donator                       integer,
  login_admin                   integer,
  db                            integer,
  is_locked                     boolean,
  constraint ck_user_super_user check (super_user in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_sell_admin check (sell_admin in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_seller check (seller in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_book_keeper check (book_keeper in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_cashier check (cashier in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_clerk check (clerk in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_guest check (guest in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_donator check (donator in (0,1,2,3,4,5,6,7,8,9)),
  constraint ck_user_login_admin check (login_admin in (0,1,2,3,4,5,6,7,8,9)),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists cart;
drop sequence if exists cart_seq;

drop table if exists catalog;

drop table if exists donation;

drop table if exists item_info;

drop table if exists payment;

drop table if exists permission;
drop sequence if exists permission_seq;

drop table if exists receipt;

drop table if exists transaction;

drop table if exists user;


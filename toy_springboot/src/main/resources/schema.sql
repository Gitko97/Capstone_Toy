drop table if exists user;
create table user (
                      idx bigint not null auto_increment,
                      created_date datetime(6),
                      modified_date datetime(6),
                      name varchar(255) not null,
                      phone_number varchar(255) not null,
                      pw varchar(255) not null,
                      user_id varchar(255) not null,
                      primary key (idx)
) engine=InnoDB
create table moonlight_origination
(
    id int auto_increment,
    name varchar(128) default '' not null,
    status int default 0 not null,
    crate_time timestamp default CURRENT_TIMESTAMP null,
    modify_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint origination_id_uindex
        unique (id)
);

alter table moonlight_origination
    add primary key (id);

create table moonlight_project
(
    id int unsigned auto_increment,
    name varchar(200) default '' not null,
    public tinyint(1) default 1 not null,
    status int default 0 null,
    platform varchar(64) default '' null,
    organization_id int default 0 null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    modify_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint moonlight_project_id_uindex
        unique (id)
);

alter table moonlight_project
    add primary key (id);


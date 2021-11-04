create table moonlight.moonlight_organization
(
    name         varchar(64)              not null,
    slug         varchar(50)              not null,
    status       integer                  not null,
    date_added   timestamp  not null,
    default_role varchar(32)              not null,
    flags        bigint                   not null
);


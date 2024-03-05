create table if not exists accounts (
    id           serial  primary key,
    balance      real    not null
);

create table if not exists users (
    id           serial  primary key,
    full_name    varchar not null,
    login        varchar not null unique,
    password     varchar not null,
    birthday     date    not null,
    account_id   bigint  not null references accounts (id) on delete cascade
);

create table if not exists emails (
    id           serial  primary key,
    user_id      bigint  not null references users (id) on delete cascade,
    email        varchar not null,

    constraint user_email_unique UNIQUE (user_id, email)
);

create table if not exists phones (
    id           serial  primary key,
    user_id      bigint  not null references users (id) on delete cascade,
    phone_number varchar not null,

    constraint user_phone_unique UNIQUE (user_id, phone_number)
);
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
    email        varchar not null unique
);

create table if not exists phones (
    id           serial  primary key,
    user_id      bigint  not null references users (id) on delete cascade,
    phone_number varchar not null unique
);

create table if not exists payments (
    id           serial primary key,
    from_acc_id  bigint not null references accounts (id),
    to_acc_id    bigint not null references accounts (id),
    amount       real   not null
);
CREATE TABLE "user" (
    id          serial,
    name        varchar     NOT NULL,
    phone       varchar     NOT NULL,
    user_type   varchar     NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_phone_uq UNIQUE (phone)
);

CREATE TABLE wallet (
    id          serial,
    user_id     int         NOT NULL,
    balance     int         NOT NULL DEFAULT 0,

    CONSTRAINT wallet_pk PRIMARY KEY (id)
);

CREATE TABLE "transaction" (
    id                  serial,
    wallet_id           int         NOT NULL,
    amount              int         NOT NULL DEFAULT 0,
    description         varchar     NOT NULL,
    transaction_type    varchar     NOT NULL,
    transaction_date    timestamp   NOT NULL DEFAULT now(),

    CONSTRAINT transaction_pk PRIMARY KEY (id)
);
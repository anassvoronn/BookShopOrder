-- Table: public.orders

-- DROP TABLE IF EXISTS public.orders;

CREATE SEQUENCE orders_id_seq START 1;
CREATE SEQUENCE orderitem_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.orders
(
    id integer NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    user_id integer NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.orders
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.orderitem
(
    id integer NOT NULL DEFAULT nextval('orderitem_id_seq'::regclass),
    book_id integer NOT NULL,
    quantity integer NOT NULL,
    price numeric(10,2) NOT NULL,
    order_id integer NOT NULL,
    CONSTRAINT orderitem_pkey PRIMARY KEY (id),
    CONSTRAINT orderitem_order_id_fkey FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.orderitem
    OWNER to postgres;

ALTER TABLE public.orders
ADD COLUMN status VARCHAR NOT NULL DEFAULT 'NEW';


CREATE SEQUENCE transactions_history_id_seq START 1;

CREATE TABLE IF NOT EXISTS public.transactions_history
(
    id integer NOT NULL DEFAULT nextval('transactions_history_id_seq'::regclass),
    amount numeric(15,2) NOT NULL,
    operation_type character varying(20) COLLATE pg_catalog."default" NOT NULL,
    date timestamp with time zone NOT NULL DEFAULT now(),
    balance numeric(15,2) NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT transactions_history_pkey PRIMARY KEY (id),
    CONSTRAINT transactions_history_operation_type_check CHECK (operation_type::text = ANY (ARRAY['DEPOSIT'::character varying, 'WITHDRAWAL'::character varying, 'PAYMENT'::character varying]::text[])),
    CONSTRAINT transactions_history_balance_check CHECK (balance >= 0::numeric)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transactions_history
    OWNER to postgres;
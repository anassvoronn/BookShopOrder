-- Table: public.orders

-- DROP TABLE IF EXISTS public.orders;

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
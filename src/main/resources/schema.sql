create table TRDBOOK (
   trade_id integer auto_increment,
   trd_no char(10),
   isin char(12) not null,
   member_id char(5) not null,
   buy_sell_typ char(1),
   quantity bigint,
   amount bigint,
   price bigint,
   currency char(3),
   stl_date date,
   ins_time timestamp default now(),
   constraint trade_pkey primary key(trade_id)
);

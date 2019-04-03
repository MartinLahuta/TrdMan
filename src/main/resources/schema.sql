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

create table POSITION (
  position_id integer auto_increment,
  npu_id char(9) not null,
  currency char(3) not null,
  buy_sell_typ char(1) not null,
  quantity bigint not null,
  amount bigint not null,
  price bigint not null,
  member_id char(5) not null,
  isin char(12) not null,
  stl_date date not null,
  ins_time timestamp default now(),
  constraint position_pkey primary key(position_id)
);

create table POSITION_TRADES (
  id serial,
  trade_id int,
  position_id int,
  offset_quantity numeric(19,6) not null,
  offset_amount numeric(19,6) not null,
  surplus_quantity numeric(19,6) not null,
  surplus_amount numeric(19,6) not null,
  constraint position_trades_pkey primary key(id),
  constraint position_trades_fkey foreign key(position_id) references POSITION(position_id)
);

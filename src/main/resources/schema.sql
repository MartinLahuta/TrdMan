create table TRDBOOK (
   id integer auto_increment,
   isin char(12) not null,
   membid char(5) not null,
   bsflg char(1),
   qty bigint,
   amnt bigint,
   prc bigint,
   curr char(3),
   stldate date
);

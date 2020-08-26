# --- First database schema

# --- !Ups
create table todo (
  id           bigint not null auto_increment,
  name         varchar(255) not null,
  constraint pk_todo primary key (id)
);

create sequence todo_seq start with 1000;
insert into todo (id,name) values (1,'旅行の計画');
insert into todo (id,name) values (2,'断捨離');
insert into todo (id,name) values (3,'チケット予約');

# --- !Downs
-- Downsは手動では実行されず、Upsの内容が変更された際に実行されます。
-- Downsが実行されてからUpsが実行されるため、結果としてデータベースのスキーマが更新されることになります。
drop table if exists todo;
drop sequence if exists todo_seq;
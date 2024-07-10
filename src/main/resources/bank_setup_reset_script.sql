drop table if exists "user";

create table "user"(
	username text,
	password text
);

insert into "user" values ('admin', '1234');

drop table if exists "account";

create table "account"(
	username text,
	solaris int,
	spice int
);

insert into "account" values ('admin', 100 ,100 );
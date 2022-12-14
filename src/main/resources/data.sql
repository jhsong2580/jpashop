insert into member(member_id, member_create_date, member_update_date, city, street, zip_code, name,
                   password)
values (default, now(), now(), 'seoul', 'kangnam', '06284', '10149424', '10149424');

insert into item values('M',default, '드래곤길들이기', 18000, 10);
insert into movie values('드래곤1','드래곤2',1);

insert into item values('M', default, '씨비스트', 16000, 4);
insert into movie values('드래곤1','드래곤2',2);

insert into item values('B', default, '책1', 28000, 10);
insert into book values('저자1','18982',3);

insert into item values('B', default, '책2', 38000, 4);
insert into book values('저자2','28982',4);

insert into item values('A', default, '앨범1', 58000, 10);
insert into album values('아티스트1','etc1',5);


insert into item values('A', default, '앨범2', 58000, 10);
insert into album values('아티스트2','etc2',6);

alter SEQUENCE  DELIVERY_SEQ increment by 3;

insert into delivery values(1, 'seoul', 'kuro', '06284', 'READY');
insert into orders values(40000, now(), now(), 'ORDER', 1, 1);
insert into order_item values(2, 36000, 40000, 1);


insert into delivery values(2, 'seoul', 'kuro', '06284', 'READY');
insert into orders values(30000, now(), now(), 'ORDER', 2, 1);
insert into order_item values(3, 80000, 30000, 4);
insert into order_item values(1, 80000, 30000, 2);

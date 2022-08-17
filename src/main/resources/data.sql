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
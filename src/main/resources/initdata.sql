

-- 유저 등록
insert into users (user_id, name, point, created_at, updated_at) values (1, 'jordan', 10000, now(), now());
insert into users (user_id, name, point, created_at, updated_at) values (2, 'michel', 20000, now(), now());
insert into users (user_id, name, point, created_at, updated_at) values (3, 'sonny', 30000, now(), now());

-- 메뉴 등록
insert into menu (menu_id, name, price, created_at, updated_at) values (1, 'ice americano', 5000, now(), now());
insert into menu (menu_id, name, price, created_at, updated_at) values (2, 'cafe latte', 6000, now(), now());
insert into menu (menu_id, name, price, created_at, updated_at) values (3, 'dolce latte', 7000, now(), now());
insert into menu (menu_id, name, price, created_at, updated_at) values (4, 'hot milk', 3000, now(), now());
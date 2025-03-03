DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);
INSERT INTO meals (user_id,datetime, description, calories)
VALUES (100000,'2020-01-30 10:00','Завтрак',1000),
       (100001,'2023-06-21 07:40','Обед',2000),
       (100002,'2025-03-02 11:02','Ужин',1500);

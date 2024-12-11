insert into categories (id,is_deleted, name, description) values (1, false, 'lol info','some description');
insert into categories (id,is_deleted, name, description) values (2, false, 'lol info1','some description');

insert into books (id,is_deleted, title, isbn, price, author) values (1,false,'Some Title1','12345', 100, 'Jhon kik 1');
insert into books (id,is_deleted, title, isbn, price, author) values (2,false,'Some Title2','12346', 120, 'Jhon kik 2');
insert into books (id,is_deleted, title, isbn, price, author) values (3,false,'Title3','12347', 130, 'Jhon kik 3');
insert into books (id,is_deleted, title, isbn, price, author) values (4,false,'Title4','12348', 140, 'Jhon kik 4');

insert into books_categories (book_id, categories_id) values (1,1);
insert into books_categories (book_id, categories_id) values (1,2);
insert into books_categories (book_id, categories_id) values (2,1);


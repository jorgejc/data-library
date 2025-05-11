-- Table Categories
create table categories(
	category_id int primary key auto_increment,
	name varchar(50) not null,
	description varchar(200)
);

insert into categories (name, description) values 
('Novela', 'obras de ficcion en verso'),
('Ciencia ficción', 'Historias basadas en impactos de la ciencia'),
('Historia', 'Libros sobre eventos históricos');


select * from categories;
select * from categories where category_id = 2;
update categories set name = 'Novela', description = 'obras de ficcion en verso' where category_id = 1;
delete from categories where category_id = 1;


-- Tabla de Authors
create table authors(
	author_id int primary key auto_increment,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	birth_date date
);

insert into authors (firstname, lastname, birth_date) values 
('Gabriel', 'García Márquez', '1927-03-06'),
('Isaac', 'Asimov', '1920-01-02'),
('Yuval Noah', 'Harari', '1976-02-24');

select * from authors ;
select * from authors;

-- table books
create table books(
	book_id int primary key auto_increment,
	title varchar(100) not null,
	author_id int,
	category_id int,
	year_publication int,
	isbn varchar(20) unique, 
	foreign key (author_id) references authors(author_id),
	foreign key (category_id) references categories(category_id)
);

insert into books (title, author_id, category_id, year_publication, isbn) values 
('Cien años de soledad', 1, 1, 1967, '978-0307474728'),
('Fundación', 2, 2, 1951, '978-0553293357'),
('Sapiens', 3, 3, 2014, '978-0062316097');

select * from books;






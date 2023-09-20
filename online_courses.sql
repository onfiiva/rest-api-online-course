CREATE DATABASE onlinecoursev3;

-- Создание таблицы CourseType
CREATE TABLE IF NOT EXISTS CourseType (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы CourseDuration
CREATE TABLE IF NOT EXISTS CourseDuration (
    id SERIAL PRIMARY KEY,
    duration VARCHAR(255) NOT NULL CHECK
    (duration IN ('6 месяцев', '12 месяцев'))
);

-- Создание таблицы CoursePaymentType
CREATE TABLE IF NOT EXISTS CoursePaymentType (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK
    (name IN ('Разовый платеж', 'Банковская рассрочка'))
);

-- Создание таблицы CoursePayment
CREATE TABLE IF NOT EXISTS CoursePayment (
    id SERIAL PRIMARY KEY,
    sum DECIMAL NOT NULL,
    typeId INT,
    FOREIGN KEY (typeId) REFERENCES CoursePaymentType(id)
);

-- Создание таблицы Course
CREATE TABLE IF NOT EXISTS Course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lessons_count INT NOT NULL CHECK
    (lessons_count IN (24, 48)),
    cost DECIMAL CHECK
    (cost IN (35400, 70800)),
	image VARCHAR(500) NOT NULL,
    coursetypeId INT,
    FOREIGN KEY (coursetypeId) REFERENCES CourseType(id),
    coursedurationId INT,
    FOREIGN KEY (coursedurationId) REFERENCES CourseDuration(id),
    coursepaymentId INT,
    FOREIGN KEY (coursepaymentId) REFERENCES CoursePayment(id)
);

-- Создание таблицы User_roles
CREATE TABLE IF NOT EXISTS UserRoles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK
	(name IN ('USER', 'ADMIN', 'STUDENT'))
);

-- Создание таблицы modelUser
CREATE TABLE IF NOT EXISTS modelUser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
	surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NULL,
    active BOOLEAN NOT NULL,
    roleId INT NOT NULL,
	FOREIGN KEY (roleId) REFERENCES UserRoles(id),
	courseId INT,
    FOREIGN KEY (courseId) REFERENCES Course(id)
);

-- Создание таблицы Concert
CREATE TABLE IF NOT EXISTS Concert (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL
);

-- Создание таблицы Student_song
CREATE TABLE IF NOT EXISTS StudentSong (
    id SERIAL PRIMARY KEY,
    userId INT NULL,
    FOREIGN KEY (userId) REFERENCES modelUser(id),
    author VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы ConcertSongs
CREATE TABLE IF NOT EXISTS ConcertSongs (
    id SERIAL PRIMARY KEY,
    concertId INT,
    FOREIGN KEY (concertId) REFERENCES Concert(id),
    studentsongId INT,
    FOREIGN KEY (studentsongId) REFERENCES StudentSong(id)
);

-- Вставка данных

-- Таблица CourseType
INSERT INTO course_type (name)
VALUES
    ('Комфорт'),
    ('Премиум');

SELECT * FROM course_type;

-- Таблица CourseDuration
INSERT INTO course_duration (duration)
VALUES
    ('6 месяцев'),
    ('12 месяцев');
	
SELECT * FROM course_duration;

-- Таблица CoursePaymentType
INSERT INTO course_payment_type (name)
VALUES
    ('Разовый платеж'),
    ('Банковская рассрочка');
	
SELECT * FROM course_payment_type;

-- Таблица CoursePayment
INSERT INTO course_payment (sum, type_id)
VALUES
    (35400, 1),  -- Разовый платеж
    (70800, 2);  -- Банковская рассрочка

SELECT * FROM course_payment;

-- Таблица Course
INSERT INTO course (name, lessons_count, cost, image, coursetype_id, courseduration_id, coursepayment_id)
VALUES
    ('Одна ступень', 24, 35400, 'https://avatars.mds.yandex.net/i?id=d196703385b251b91ac94a29c07f4e9b_l-8496938-images-thumbs&n=13', 1, 1, 1),
    ('Две ступени', 48, 70800, 'https://avatars.mds.yandex.net/i?id=d2e8c5b14db059ed3c35c96cc1af4b58_l-9212707-images-thumbs&n=13', 2, 2, 2);
	
SELECT * FROM course;

-- Таблица UserRoles
INSERT INTO user_roles (name)
VALUES
    ('ADMIN'),
    ('USER'),
	('STUDENT');
	
SELECT * FROM user_roles;

-- Таблица modelUser
INSERT INTO model_user (username, password, surname, name, patronymic, role_id, course_id)
VALUES
    ('user', '123', 'Иванов', 'Петр', 'Александрович', 2, 2),
    ('admin', '123', 'Петрова', 'Анна', 'Ивановна', 1, 3),
	('student', '123', 'Петров', 'Пётр', 'Петрович', 3, 4);
	
SELECT * FROM model_user;

-- Таблица Concert
INSERT INTO concert (date)
VALUES
    ('2023-08-22'),
	('2023-11-21');
	
SELECT * FROM concert;

-- Таблица StudentSong
INSERT INTO student_song (user_id, author, name)
VALUES
    (6, 'System Of A Down', 'Toxicity'),
    (10, 'Slipknot', 'Before I Forget');
	
SELECT * FROM student_song;

-- Таблица ConcertSongs
INSERT INTO concert_songs (concert_id, studentsong_id)
VALUES
    (1, 2),
    (1, 5);
	
SELECT * FROM concert_songs;

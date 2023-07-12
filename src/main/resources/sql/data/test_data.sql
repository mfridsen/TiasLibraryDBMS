-- @author Mattias Fridsén
-- @project LibraryDBMS
-- @date 2/28/2023
-- @contact matfir-1@student.ltu.se

-- Fills tables in database with test data

-- (, '', '', '', ),
INSERT INTO authors (authorID, authorFirstname, authorLastname, biography, deleted)
VALUES (1, 'author1', 'lastname1', 'is the first author', 0),
       (2, 'author2', 'lastname2', 'is the second author', 0),
       (3, 'author3', 'lastname3', 'is the third author', 0),
       (4, 'author4', 'lastname4', 'is the fourth author', 0),
       (5, 'author5', 'lastname5', 'is the fifth author', 0),
       (6, 'author6', 'lastname6', 'is the sixth author', 0),
       (7, 'author7', 'lastname7', 'is the seventh author', 0),
       (8, 'author8', 'lastname8', 'is the eighth author', 0),
       (9, 'author9', 'lastname9', 'is the ninth author', 0),
       (10, 'author10', 'lastname10', 'is the tenth author', 0),
       (11, 'Joe', 'Abercrombie', 'Lord Grimdark', 0);

-- classifications (, '', '', ),
INSERT INTO classifications (classificationID, classificationName, description, deleted)
VALUES (1, 'Physics', 'Scientific literature on the topic of physics.', 0),
       (2, 'Chemistry', 'Scientific literature on the topic of chemistry.', 0),
       (3, 'Mathematics', 'Scientific literature on the topic of mathematics.', 0),
       (4, 'Geography', 'Scientific literature on the topic of geography.', 0),
       (5, 'Geology', 'Scientific literature on the topic of geology.', 0),
       (6, 'Biology', 'Scientific literature on the topic of biology.', 0),
       (7, 'Programming', 'Scientific literature on the topic of programming.', 0),
       (8, 'Horror', 'The best genre.', 0),
       (9, 'Psychology', 'Scientific literature on the topic of psychology.', 0),
       (10, 'Fantasy', 'Tolkien did it best.', 0);

-- items (, '', '', '', , , , , , ),
INSERT INTO items (itemID, title, itemType, barcode, authorID, classificationID, allowedRentalDays, available, deleted)
VALUES (1, 'item1', 'REFERENCE_LITERATURE', '1', 1, 1, 0, 1, 0),
       (2, 'item2', 'MAGAZINE', '2', 2, 2, 0, 1, 0),
       (3, 'item3', 'COURSE_LITERATURE', '3', 3, 3, 14, 1, 0),
       (4, 'item4', 'OTHER_BOOKS', '4', 4, 4, 28, 1, 0),
       (5, 'item5', 'OTHER_BOOKS', '5', 5, 5, 28, 1, 0),
       (6, 'item6', 'FILM', '6', 6, 6, 7, 1, 0),
       (7, 'item7', 'FILM', '7', 7, 7, 7, 1, 0),
       (8, 'item8', 'FILM', '8', 8, 8, 7, 1, 0),
       (9, 'item9', 'FILM', '9', 9, 9, 7, 1, 0),
       (10, 'item10', 'FILM', '10', 10, 10, 7, 1, 0),
       (11, 'The Blade Itself', 'OTHER_BOOKS', '11', 11, 10, 28, 1, 0),
       (12, 'Before They Are Hanged', 'OTHER_BOOKS', '12', 11, 10, 28, 1, 0),
       (13, 'Last Argument Of Kings', 'OTHER_BOOKS', '13', 11, 10, 28, 1, 0),
       (14, 'Best Served Cold', 'OTHER_BOOKS', '14', 11, 10, 28, 1, 0),
       (15, 'The Heroes', 'OTHER_BOOKS', '15', 11, 10, 28, 1, 0),
       (16, 'Red Country', 'OTHER_BOOKS', '16', 11, 10, 28, 1, 0),
       (17, 'Sharp Ends', 'OTHER_BOOKS', '17', 11, 10, 28, 1, 0),
       (18, 'A Little Hatred', 'OTHER_BOOKS', '18', 11, 10, 28, 1, 0),
       (19, 'The Trouble With Peace', 'OTHER_BOOKS', '19', 11, 10, 28, 1, 0),
       (20, 'The Wisdom Of Crowds', 'OTHER_BOOKS', '20', 11, 10, 28, 1, 0);

-- literature (, ''),
INSERT INTO literature (literatureID, ISBN)
VALUES (1, '9783161484100'),
       (2, '9780262134729'),
       (3, '9780123849342'),
       (4, '9780123849359'),
       (5, '9780123849366'),
       (11, '9780123849373'),
       (12, '9780123849380'),
       (13, '9780123849397'),
       (14, '9780123849403'),
       (15, '9780123849410'),
       (16, '9780123849427'),
       (17, '9780123849434'),
       (18, '9780123849441'),
       (19, '9780123849458'),
       (20, '9780123849465');

-- films (, '', '', ''),
INSERT INTO films (filmID, ageRating, countryOfProduction, actors)
VALUES (6, 15, 'USA', 'Actor1, Actor2, Actor3'),
       (7, 18, 'France', 'Actor4, Actor5, Actor6'),
       (8, 12, 'Germany', 'Actor7, Actor8, Actor9'),
       (9, 16, 'Spain', 'Actor10, Actor11, Actor12'),
       (10, 18, 'UK', 'Actor13, Actor14, Actor15');

INSERT INTO users (userID, username, password, userType, email, allowedRentals, currentRentals, lateFee, allowedToRent,
                   deleted)
VALUES (1, 'user1', 'password1', 'ADMIN', 'admin@example.com', 0, 0, 0.0, 0, 0),
       (2, 'user2', 'password2', 'STAFF', 'staff@example.com', 0, 0, 0.0, 0, 0),
       (3, 'user3', 'password3', 'PATRON', 'patron@example.com', 3, 0, 0.0, 1, 0),
       (4, 'user4', 'password4', 'STUDENT', 'student@example.com', 5, 0, 0.0, 1, 0),
       (5, 'user5', 'password5', 'TEACHER', 'teacher@example.com', 10, 0, 0.0, 1, 0),
       (6, 'user6', 'password6', 'RESEARCHER', 'researcher@example.com', 20, 0, 0.0, 1, 0),
       (7, 'user7', 'password7', 'ADMIN', 'admin2@example.com', 0, 0, 0.0, 0, 0),
       (8, 'user8', 'password8', 'PATRON', 'patron2@example.com', 3, 0, 0.0, 1, 0),
       (9, 'user9', 'password9', 'STAFF', 'staff2@example.com', 0, 0, 0.0, 0, 0),
       (10, 'user10', 'password10', 'STUDENT', 'student2@example.com', 5, 0, 0.0, 1, 0);

-- No rental test data because not needed, we create rentals during tests or at runtime
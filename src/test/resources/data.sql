INSERT INTO band (id, name)
VALUES (100, 'Band Number One'),
       (101, 'Second Band'),
       (102, 'Third Band'),
       (103, 'Fourth Band');

INSERT INTO event (id, name, date_time)
VALUES (110, 'First Event', '2023-12-31 10:00'),
       (111, 'Second Event', '2024-01-01 10:00'),
       (112, 'Third Event', '2023-12-31 22:00');

INSERT INTO event_band (event_id, band_id)
VALUES (110, 100),
       (110, 101),
       (110, 102),
       (111, 103),
       (111, 100),
       (112, 101),
       (112, 102);

INSERT INTO musician (id, name, role, band_id)
VALUES (120, 'Musician #120', 'Singer', 100),
       (121, 'Musician #121', 'Guitar player', 100),
       (122, 'Musician #122', 'Bass player', 100),
       (123, 'Musician John #123', 'Drummer', 100),
       (124, 'Musician #124', 'Singer', 101),
       (125, 'Musician John #125', 'Guitar player', 101),
       (126, 'Musician #126', 'One-man-band', 102),
       (127, 'John Musician #127', 'Keyboard player', 101);

INSERT INTO band (id, name)
VALUES (100, 'Band Number One'),
       (101, 'Second Band'),
       (102, 'Third Band'),
       (103, 'Fourth Band');

INSERT INTO event (id, name, date_time)
VALUES (100, 'First Event', '2023-12-31 10:00'),
       (101, 'Second Event', '2024-01-01 10:00'),
       (102, 'Third Event', '2023-12-31 22:00');

INSERT INTO event_band (event_id, band_id)
VALUES (100, 100),
       (100, 101),
       (100, 102),
       (101, 103),
       (101, 100),
       (102, 101),
       (102, 102);

INSERT INTO musician (id, name, role, band_id)
VALUES (100, 'Musician #100', 'Singer', 100),
       (101, 'Musician #101', 'Guitar player', 100),
       (102, 'Musician #102', 'Bass player', 100),
       (103, 'Musician John #103', 'Drummer', 100),
       (104, 'Musician #104', 'Singer', 101),
       (105, 'Musician John #105', 'Guitar player', 101),
       (106, 'Musician #106', 'One-man-band', 102),
       (107, 'John Musician #107', 'Keyboard player', 101);

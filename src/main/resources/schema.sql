CREATE SEQUENCE IF NOT EXISTS main_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS event
(
    id        INTEGER PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    date_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS band
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS event_band
(
    event_id INTEGER REFERENCES event (id) ON DELETE CASCADE,
    band_id  INTEGER REFERENCES band (id) ON DELETE CASCADE,
    UNIQUE (event_id, band_id)
);

CREATE TABLE IF NOT EXISTS musician
(
    id      INTEGER PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    role    VARCHAR(255) NOT NULL,
    band_id INTEGER REFERENCES band (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS equipment
(
    id           INTEGER PRIMARY KEY,
    manufacturer VARCHAR(255),
    name         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS amplifier
(
    equipment_id INTEGER REFERENCES equipment (id) ON DELETE CASCADE,
    channels     INTEGER,
    power        INTEGER
);

CREATE TABLE IF NOT EXISTS mixer
(
    equipment_id    INTEGER REFERENCES equipment (id) ON DELETE CASCADE,
    mic_channels    INTEGER,
    stereo_channels INTEGER
);


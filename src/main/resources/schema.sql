CREATE TABLE IF NOT EXISTS event
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    date_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS band
(
    id   SERIAL PRIMARY KEY,
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
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    role    VARCHAR(255) NOT NULL,
    band_id INTEGER REFERENCES band (id) ON DELETE CASCADE
);




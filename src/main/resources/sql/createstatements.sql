/* These are the CREATE TABLE -statements for the database used by this application */
/* Use only one line comments in this file, with the same comment markings as the ones used here or starting the line with // */

CREATE TABLE IF NOT EXISTS Bookmark (
    id INTEGER PRIMARY KEY,
    title varchar(200),
    addDate datetime default current_timestamp,
    type varchar(1)
);

CREATE TABLE IF NOT EXISTS Blogpost (
    id integer NOT NULL,
    author varchar(100),
    url varchar(400),
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES Bookmark(id)
);

CREATE TABLE IF NOT EXISTS Video (
    id integer NOT NULL,
    url varchar(400),
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES Bookmark(id)
);

/* TODO: Consider adding NOT NULL for some of the columns */
/* TODO: Consider changing some varchar sizes */
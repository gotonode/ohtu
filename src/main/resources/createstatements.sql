
CREATE TABLE IF NOT EXISTS "Bookmark" (
    id INTEGER PRIMARY KEY,
    title varchar(200),
    addDate datetime default current_timestamp,
    type varchar(1)
);

CREATE TABLE IF NOT EXISTS "Blogpost" (
    id integer NOT NULL,
    author varchar(100),
    url varchar(400),
    FOREIGN KEY (id) REFERENCES bookmark(id)
);
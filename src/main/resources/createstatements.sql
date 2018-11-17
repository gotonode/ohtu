
CREATE TABLE Bookmark ( 
    id INTEGER PRIMARY KEY, 
    title varchar(200),
    addDate datetime default current_timestamp,
    type varchar(1)
);  

CREATE TABLE Blogpost (
    id integer,  
    author varchar(100),
    url varchar(400),                                                                                                           
    FOREIGN KEY (id) REFERENCES bookmark(id)); 

CREATE TABLE Bookmark ( 
    id INTEGER PRIMARY KEY, 
    title varchar(100),
    addDate datetime default current_timestamp,
    type varchar(1)
);  

CREATE TABLE Blogpost (
    id integer,  
    author varchar(50),
    url varchar(100),                                                                                                           
    FOREIGN KEY (id) REFERENCES bookmark(id)); 
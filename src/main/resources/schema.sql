CREATE TABLE authors (
  id BIGINT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255),
  age INT,
  PRIMARY KEY (id)
);

CREATE TABLE books (
  isbn VARCHAR(225) NOT NULL,
  title VARCHAR(255),
  author_id BIGINT,
  PRIMARY KEY (isbn),
  CONSTRAINT fk_author FOREIGN KEY (author_id)
   REFERENCES authors(id)
);
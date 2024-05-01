CREATE TABLE IF NOT EXISTS Run(
    id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    location varchar(10) NOT NULL,
    miles INT NOT NULL,
    version INT,
    PRIMARY KEY(id)
);
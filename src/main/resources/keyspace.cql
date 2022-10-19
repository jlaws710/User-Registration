CREATE KEYSPACE user_registration WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE if not exists User (
                    username VARCHAR,
                    email VARCHAR,
                    creditcard VARCHAR,
                    firstname VARCHAR,
                    lastname VARCHAR,
                    "password" VARCHAR,
                    "role" VARCHAR,
                    PRIMARY KEY (username, email)
    );
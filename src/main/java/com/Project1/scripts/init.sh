until printf "" 2>>/dev/null >>/dev/tcp/cassandra/9042; do
    sleep 5;
    echo "Waiting for cassandra...";
done

echo "Creating keyspace and table..."
cqlsh cassandra -u cassandra -p cassandra -e "CREATE KEYSPACE user_registration WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};"
cqlsh cassandra -u cassandra -p cassandra -e "CREATE TABLE if not exists User (
                                                                  username VARCHAR,
                                                                  email VARCHAR,
                                                                  creditcard VARCHAR,
                                                                  firstname VARCHAR,
                                                                  lastname VARCHAR,
                                                                  password VARCHAR,
                                                                  role VARCHAR,
                                                                  PRIMARY KEY (username, email)
                                                  );"
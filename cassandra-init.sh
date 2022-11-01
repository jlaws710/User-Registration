cas_query="create keyspace if not exists user_registration with replication = {'class':'SimpleStrategy', 'replication_factor':1};
use user_registration;
create table if not exists User(username VARCHAR, firstName VARCHAR, lastName VARCHAR, email VARCHAR, password VARCHAR,
role VARCHAR, creditCard VARCHAR, primary key(username, email));"

until echo $cas_query | cqlsh
do
	now=$(date +%T)
	echo "[$now INIT CQLSH]: Node still unavailable, will retry another time"
	sleep 2
done &

exec /usr/local/bin/docker-entrypoint.sh "$@"

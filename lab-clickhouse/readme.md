# Config

## Run images
### Server
`docker run -p 8123:8123 -p 9000:9000 -d --name lab-clickhouse-server --ulimit nofile=262144:262144 -v D:\Projects\Repos\IVT_JavaEE_Labs\lab-clickhouse\resources\users.xml:/etc/clickhouse-server/users.xml --volume=D:\Projects\Repos\IVT_JavaEE_Labs\lab-clickhouse\resources\database:/var/lib/clickhouse clickhouse/clickhouse-server`
### Client
`docker run -it --rm --link lab-clickhouse-server:clickhouse-server clickhouse/clickhouse-client --host clickhouse-server -u user_db --password 1234`
###http port: 8123 
###client port: 9000  

docker run -d -p 8123:8123 -p 9000:9000 --name some-clickhouse-server --ulimit nofile=262144:262144 clickhouse/clickhouse-server
docker run -it --rm --link some-clickhouse-server:clickhouse-server clickhouse/clickhouse-client --host clickhouse-server
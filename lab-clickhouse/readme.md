# Config

## Run images
### Server
`docker run -d --network=host --name lab-clickhouse-server --ulimit nofile=262144:262144 --volume=D:\Projects\Repos\IVT_JavaEE_Labs\lab-clickhouse\resources\database:/var/lib/clickhouse clickhouse/clickhouse-server`
### Client
`docker run -it --rm --link lab-clickhouse-server:clickhouse-server clickhouse/clickhouse-client --host clickhouse-server`
###http port: 8123 
###client port: 9000  


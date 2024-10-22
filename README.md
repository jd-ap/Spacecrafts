# Spacecrafts

[go to statement](./STATEMENT.md)

## Getting started
### Installation
1. Clone this repository
````shell
git clone https://github.com/jd-ap/Spacecrafts.git
````
2. Build service into container
````shell
docker build -f containerfile -t proof/spacecrafts .
docker run -d --rm -p 9000:9000 --name spacecrafts proof/spacecrafts --server.port=9000
docker system prune 
````
### Paths
* [swagger ui](http://localhost:9000/api/v1)
* [h2 console](http://localhost:9000/api/v1/h2-console)
  - **JDBC url**: jdbc:h2:mem:sc_db
  - **User name**: guest
  - **Password**: changeIt


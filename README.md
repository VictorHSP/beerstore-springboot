# beerstore-springboot
Spring Boot com Docker na AWS - Curso ministrado pelo Normandes Júnior

Tecnologias utilizadas: 
 - Spring Boot 2 
 - Spring Data JPA 
 - Actuator
 - Lombok
 - PostgreSQL
 - Bean Validation
 - Mockito
 - Hamcrest
 - AWS ( EC2, S3, VPC)
 - Docker
 - Terraform
 
 # Docker
 
 Script para criação e comunicação entre docker app e docker db: 
 - ```docker network create beer-net```
 - ```docker network connect beer-net beerdb```
 - ```docker build -t gamgrave/beerstore:0.2 .```
 - ```docker run -p 8080:8080 --rm --network beer-net -e APP_OPTIONS='--spring.datasource.url=jdbc:postgresql://beerdb:5432/beerstore'  gamgrave/beerstore:0.2```
 
 

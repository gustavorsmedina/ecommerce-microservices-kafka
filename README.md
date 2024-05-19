# Ecommerce Kafka

- Java
- Event-Driven Architecture
- Kafka
- Docker
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- Resilience4J
- MongoDB
- PostgreSQL

---

## Diagrama

![Diagrama Kafka](/images/kafka-ecommerce-diagram.jpg "Diagrama Kafka")

---

## Passo a passo

#### 1 - Instale o docker em sua máquina <https://www.docker.com>

---

#### 2 - Abra o terminal na pasta *api-order* e execute o comando `docker compose up`

---

#### 3 - Abra o terminal na pasta *api-product* e execute o comando `docker compose up`

Após executar os dois comandos você terá disponível os seguintes serviços:

- PostgreSQL
Porta: 5432

- PgAdmin Web
Porta: 5050
Link de acesso: http://localhost:5050
Email: gustavo@postgres.com
Senha: gustavo

Para se conectar com PgAdmin, você deverá pegar o IP Adress do container que está rodando o PostgreSQL, use o comando `docker ps` no terminal e pegue o ID do container do PostgreSQL e em sequência use o comando `docker inspect <ID>`, lá você encontrará o IP Adress necessário para realizar a conexão..

- MongoDB
Porta: 27017

- Mongo Express
Porta: 8090
Link de acesso: http://localhost:8090
Usuário: mongo
Senha: gustavo

- Kafka
Porta: 9092

- Zookeeper

- Kafdrop
Porta: 19000

---

#### 4 - Crie os bancos de dados necessários para a aplicação

- MongoDB
Crie um banco chamado: db_products

- PostgreSQL
Crie um banco chamado: db_orders

---

#### 5 - Abra service-email/src/main/resources/application.yml

Informe o username (email) que será *responsável* por enviar o email para os usuários que estão fazendo o pedido, e a sua senha de aplicativo.

Gere sua senha de aplicativo do gmail: <https://support.google.com/accounts/answer/185833?hl=pt-BR>

---

#### 6 - Após terminar a configuração, inicie todas as aplicações e elas estarão disponíveis nas seguintes portas:

- api-order > 8081 | http://localhost:8081/v1/orders
- api-product > 8080 | http://localhost:8080/v1/products
- service-email > 8083 | http://localhost:8083
- service-order-consumer > 8085 | http://localhost:8085
- service-payment > 8084 | http://localhost:8084

---

#### 7 - Abra algum aplicativo para realizar suas requisições

- Crie primeiramente um produto:

POST > http://localhost:8080/v1/products

```json
{
	"name": "T-Shirt",
	"price": 20.00,
	"description": "Black t-shirt.",
	"stock": 20
}
```

- Pegue as informações do produto usando:

GET > http://localhost:8080/v1/products

- Crie um novo pedido 

POST > http://localhost:8080/v1/orders

```json
{
	"paymentType": "PIX",
	"userEmail": "user email",
	"products": [
		{
			"code": "product code",
			"name": "T-Shirt",
			"price": 20.00,
			"quantity": 1
		}
	]
}
```
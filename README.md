# Seguros Unimed

Aplicação foi construída utilizando Java JDK 11 e Spring Boot e tem como objetivo disponibilizar os dados de clientes através de APIs para consulta, cadastro, alteração e exclusão de clientes.

Na consulta de clientes você pode fazer filtros utilizando os campos nome, email, gênero, cidade e estado em conjunto ou individualmente.

## Rodando a aplicação

### Pré requisitos:
```text
* Java JDK 11
* Maven
* Docker
```

## Desenvolvimento

Para rodar a aplicação em modo de desenvolvimento basta importar o projeto na sua IDE favorita e executar o método `main` na classe `ApiApplication`

## Build

Para gerar um build com os artefatos basta executar o seguinte comando:

`mvn clean package`

Os artefatos serão gerados no diretório `target`

O JAR para execução da aplicação será o `example-api-1.0.0.jar`

## Docker

Para rodar a aplicação local de maneira mais rápida utilizando o Docker foi criado um arquivo chamado: `docker-compose.yml` na raiz do projeto ele é responsável por fazer o build da imagem do Dockerfile e criar o container.

Para criar o container da aplicação execute comando abaixo ele vai criar o container em background e colocar em execução:
`docker compose up -d`

Para destruir o container criado anteriormente execute o seguinte comando:
`docker compose down`

## Autenticação

Para realizar a segurança dos endpoints foi utilizando o Spring Security com o método de autenticação BASE64.

Para conseguir acessar os endpoints é necessário utilizar codificar as seguintes credenciais abaixo para BASE64:

```text
Usuário: admin
Senha: admin
```

Em sistemas operacionais Linux, MacOS utilize o seguinte comando para gerar o token:
```text
echo -n 'admin:admin' | base64
```

Se preferir basta adicionar o seguinte header nas requisições pois já contém as credenciais codificadas em BASE64:
```text
Authorization: Basic YWRtaW46YWRtaW4=
```

## Testes unitários
Os testes unitários estão presentes no seguinte diretório: `src/test/java`

Para executá-los basta rodar a classe de testes responsável ou executar o seguinte comando:
`mvn test`

## Requests CURL para realizar as chamadas:

- Buscar clientes com filtros:

```text
curl --location 'http://localhost:8080/customers?name=Aranha&gender=M&email=thor%40vingadores&city=Monte&state=MG' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```


- Criar novo cliente:

```text
curl --location 'http://localhost:8080/customers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw '{
    "name": "Mario Marques",
    "email": "mario.martins@gmail.com",
    "gender": "M",
    "ceps": [
        "38500000"
    ]
}'
```


- Editar um cliente já existente:

```text
curl --location --request PUT 'http://localhost:8080/customers/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw '{
    "name": "Jheime Editado",
    "email": "jheime.edit.95@gmail.com",
    "gender": "M",
    "ceps": [
        "38500000", "38440001", "38204056"
    ]
}'
```


- Remover um cliente existente:

```text
curl --location --request DELETE 'http://localhost:8080/customers/1' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```


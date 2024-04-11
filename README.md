<div align="center">

![notifyme](https://github.com/FabianaFSantana/notifyme-api/assets/161942930/a6f07fc1-812b-4fd7-872a-459a5b919545)

# notifyme-api
Api Rest para um sistema de notificações

</div>

## Descrição do Projeto
O Notifyme é um API REST desenvolvido com Spring Boot para servir como backend de um sistema de notificações o qual pode enviar mensagens via email e SMS para o usuário. Ele oferece recursos para a manipulação de usuáros e notiicações, assim como seu envio, proporcionando uma interface para interação com o banco de dados MySQL.

## Configuração do Ambiente

### Requisitos
Certifique-se de ter as seguintes dependências instaladas em seu ambiente de desenvolvimento:

* [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL](https://dev.mysql.com/downloads/installer/)

### Requisitos Adicionais
Será necessário para o envio das notificações e para testá-las criar contas no:
* [Twilio](https://www.twilio.com/en-us)
* [Mailtrap](https://mailtrap.io)

### Instalação
1. Clone o repositório:
```
https://github.com/FabianaFSantana/notifyme-api.git
```
2. No terminal, navegue até o diretório do projeto:
```
cd notifyme-api
```
3. Configure o banco de dados:
Certifique-se de que um servidor MySQL esteja em execução e crie um banco de dados chamado "notifyme".
```
CREATE DATABASE notifyme;
```
4. Configure as propriedades do banco de dados:
Se for o caso, edite o arquivo `src/main/resources/applicationExample.properties` e ajuste as configurações de conexão com o servidor MySQL:
```
spring.datasource.url=jdbc:mysql://localhost:3306/notifyme
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```
Modifique, também, as configurações da conexão com o Mailtrap de acordo com as credenciais presentes na sua conta do Mailtrap:
```
spring.mail.port=PORT
spring.mail.username=USERNAME
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=AUTH
```
Configure as configurações do código de conexão do Twilio com as credenciais da sua conta:
```
spring.mail.port=PORT
spring.mail.username=USERNAME
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=AUTH
```
5. Execute a aplicação:
```
mvn spring-boot:run
```
O servidor estará acessível em `http://localhost:8080` por padrão.


## Estrutura do Projeto
O projeto é estruturado da seguinte forma:
* `com.notifyme.api.constant`: Constant para definir o status da notificação.
* `com.notifyme.api.controller`: Controladores para manipular as requisições HTTP.
* `com.notifyme.api.model`: Modelos de dados para representar Usuário e Notificação.
* `com.notifyme.api.repository`: Repositórios para interação com o banco de dados.
* `com.notifyme.api.service`: Serviços para enviar email pelo Mailtrap, sms pelo Twilio e para criar métodos da entidade Notificação.

## Uso da API
A API possui os seguintes endpoints:

### Usuario:
* `POST /usuario`: Cria um novo usuario.
* `GET /usuario`: Exibe lista com todos os usuários.
* `GET /usuario/{idUsuario}`: Obtém informações de um usuário específico.
* `GET /usuario/exibirListaDeNotificacoes/{idUsuario}`: Exibe lista de notificações do usuário.
* `PUT /usuario/{idUsuario}`: Atualiza as informações de um usuário.
* `DELETE /usuario/{idUsuario}`: Exclui um usuário.
* `DELETE /usuario/{idUsuario}/removerNotificacao/{id}`: Remove uma notificação da lista de notificações do usuário.

### Notificacao:
* `POST /notificacao`: Cria uma nova notificação.
* `POST /notificacao/{idUsuario}/adicionaNotificacaoListaUsuario/{id}`: Adiciona uma notificação na lista de notificações do usuário.
* `GET /notificacao`: Exibe lista com todas as notificações criadas.
* `GET /notificacao/{id}`: Obtém informações de uma notificação específica.
* `GET /notificacao/dataCriacao/{dataCriacao}`: Obtém informações de uma notificação específica através da data de criação.
* `GET /notificacao/dataCriacao/data/{data}`: Obtém uma lista com todas as notificaçõs criadas em uma data específica.
* `GET /notificacao/{idUsuario}/enviarNotificacaoPorEmail/{id}`: Envia a notificação para o usuário por email.
* `GET /notificacao/{idUsuario}/enviarNotificacaoPorSms/{id}`: Envia a notificação por SMS para o usuário.
* `PUT /notificacao/{id}`: Atualiza os dados da notificação.
* `DELETE /notificacao/{id}`: Exclui uma notificação.

## Chamando os Endpoints via Postman
Após iniciar a aplicação, você pode acessar a documentação interativa da API por meio Postman. Lá, você encontrará uma interface fácil de usar para explorar e testar os endpoints da API.







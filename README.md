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





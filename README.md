# Cartão de Crédito
## _Trabalho final da disciplina Spring framework - FIAP_
#### Turma 42SCJ
Prof: FABIO TADASHI MIYASATO

Sistema de gerenciamento de cartões de crédito.

![Build](https://img.shields.io/static/v1?label=Versão&message=1.0.0&color=blue)


# Integrantes
- Arthur Gomes Araújo
- Felipe Marques dos Santos
- Fernando César Martins
- Flávio Lopes de Brito
- Lucas Oliveira Vaz

# Funcionalidades

- Cadastro de alunos;
- Listagem de alunos cadastrados;
- Buscar aluno por ID (matrícula);
- Cadastrar cartão de crédito;
- Buscar cartão de crédito por ID de um cartão;
- Listagem de cartões cadastrados;
- Registrar transação de um cartão de crédito;
- Buscar transação pelo ID do cartão;
- Gerar extrato em PDF;

# Dependências

- Gradle
- Java
- Lombok
- Spring Configuration Processor
- Spring Web
- Spring Data MongoDB
- Validation
- Spring Boot Actuator
- Embedded MongoDB Database

# Changelog

- 1.0.0
  - Cadastrar alunos
  - Listar alunos
  - Buscar um aluno por ID
  - Cadastrar cartão de crédito
  - Buscar cartão de crédito por ID
  - Listar cartoões cadastrados
  - Registrar transação de cartão de crédito
  - Buscar transação por ID de cartão de crédito
  - Gerar extrato em PDF

# Como Executar (Docker-Compose)

Na raiz do projeto, execute o comando:

- Criar a imagem do projeto e executar o containers do mongo e mongo-express
> docker-compose up

- Encerrar o ambiente
> docker-compose down

# Postman

Na pasta "postman" na raiz do projeto, estão as collections e o environment do Postman para importação

# Arquitetura

O projeto consiste de uma aplicação Spring Boot conectando em um banco de dados MongoDB. É utilizado o spring-batch para realizar a carga inicial no banco de dados à partir de um arquivo CSV.
SAT - Spring Boot + Angular

Sistema fullstack com backend em Spring Boot e frontend em Angular.

O projeto possui:

* Registro e login de usuários.
* Lista de produtos disponíveis para compra.
* Compra de produtos com controle de acesso via JWT.
* Visualização de produtos comprados pelo usuário.
* Rotas protegidas verificando token JWT.

Tecnologias utilizadas:

* Backend: Java, Spring Boot, Spring Security, JWT, JPA, H2/MySQL
* Frontend: Angular, TypeScript, HTML, CSS
* Gerenciamento de dependências: Maven (backend) e npm (frontend)

Pré-requisitos:

* Java 17+
* Node.js 16+ e npm
* Maven
* Git

Estrutura do projeto:
meu-projeto/
├─ backend/   (Spring Boot)
├─ frontend/  (Angular)
├─ .gitignore
└─ README.txt

Configuração do Backend (Spring Boot):

1. Entre na pasta do backend:
   cd backend

2. Instale dependências:
   mvn clean install

3. Configure o banco de dados no application.properties ou application.yml (H2 ou MySQL)
   Exemplo para H2:
   spring.datasource.url=jdbc\:h2\:mem\:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.h2.console.enabled=true

4. Rode o backend:
   mvn spring-boot\:run

O backend estará disponível em: [http://localhost:8080](http://localhost:8080)

Configuração do Frontend (Angular):

1. Entre na pasta do frontend:
   cd frontend

2. Instale as dependências:
   npm install

3. Configure a URL do backend no arquivo environment.ts:
   export const environment = {
   production: false,
   apiUrl: '[http://localhost:8080](http://localhost:8080)'
   };

4. Rode o frontend:
   ng serve

O frontend estará disponível em: [http://localhost:4200](http://localhost:4200)

Funcionalidades:

1. Registro e Login

* Usuário se cadastra com email e senha.
* Ao fazer login, o backend retorna um token JWT.

2. Lista de Produtos

* Exibe produtos disponíveis.
* Cada produto possui botão de compra.

3. Compra de Produto

* Requer token JWT válido.
* Adiciona o produto à lista de produtos do usuário.

4. Meus Produtos

* Mostra apenas os produtos comprados pelo usuário.
* Requer token JWT válido.

Testes:

* Backend: JUnit e Spring Boot Test.

Observações:

* Rotas do backend protegidas via JWT. Para Postman, incluir header:
  Authorization: Bearer <token>
* Para deploy completo, pode buildar Angular (ng build) e servir arquivos pelo Spring Boot.


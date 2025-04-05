# 🚀 Desafio - Sistema Full Stack com Spring Boot e Angular

Este projeto é composto por uma API REST desenvolvida com **Spring Boot 2.2.0** e uma interface web construída com **Angular**.  
Ele permite operações básicas de CRUD utilizando banco de dados MySQL, com uma interface amigável para interação com os dados.

---

## 🛠 Tecnologias Utilizadas

### 🔙 Back-End
- ☕ **Java 8+**
- 🔥 **Spring Boot 2.2.0**
- 🌐 **Spring Web**
- 🗃 **JDBC puro**
- 💾 **MySQL**
- ✨ **Lombok**
- 🛡 **Spring Boot Validation**
- 🚀 **Maven**

### 🔜 Front-End
- ⚙️ **Angular**
- 📦 **Node.js / npm**
- 🎨 **HTML5 + SCSS/CSS3**
- 🔄 **Serviços HTTP com Angular HttpClient**

---

## 📂 Estrutura do Projeto

```
/ 
├── desafio-backend/               # Projeto Spring Boot (API REST)
│   ├── .mvn/                      # Configurações do Maven Wrapper
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com.example.desafio/
│   │   │   │       ├── controller/      # Controllers da API
│   │   │   │       ├── cors/            # Configurações de CORS
│   │   │   │       ├── dao/             # Acesso a dados
│   │   │   │       ├── model/           # Entidades do sistema
│   │   │   │       ├── services/        # Lógica de negócios
│   │   │   │       ├── util/            # Utilitários diversos
│   │   │   │       └── DesafioApplication.java  # Classe principal
│   │   │   └── resources/
│   │   │       ├── static/              # Arquivos estáticos (se usados)
│   │   │       ├── templates/           # Templates (caso use)
│   │   │       └── application.properties  # Configurações da aplicação
│   │   └── test/                        # Testes automatizados
│   ├── pom.xml                         # Arquivo de dependências do Maven
│   └── .gitignore
│
├── desafio-web/                  # Projeto Angular (interface web)
│   ├── src/                      # Código-fonte Angular
│   ├── angular.json              # Configuração do Angular CLI
│   ├── package.json              # Dependências e scripts npm
│   └── tsconfig.json             # Configuração TypeScript
│
├── README.md                     # Documentação do projeto
└── .gitignore
```

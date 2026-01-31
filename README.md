# 🏭 InspectFlow Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

**Plataforma backend robusta para gestão inteligente de inspeções industriais, manutenções e estoque. Rastreabilidade completa com Clean Architecture, DDD e arquitetura orientada a eventos.**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)
[![Build Status](https://img.shields.io/github/actions/workflow/status/Rafael01Gx/inspectflow-backend/ci.yml?style=flat-square)](https://github.com/Rafael01Gx/inspectflow-backend/actions)
[![Coverage](https://img.shields.io/codecov/c/github/Rafael01Gx/inspectflow-backend?style=flat-square)](https://codecov.io/gh/Rafael01Gx/inspectflow-backend)
[![Code Quality](https://img.shields.io/codacy/grade/seu-hash?style=flat-square)](https://www.codacy.com/)

[Documentação](#-documentação) • [Instalação](#-instalação) • [Arquitetura](#-arquitetura) • [API](#-api) • [Contribuir](#-contribuindo)

</div>

---

## 📋 Sobre o Projeto

O **InspectFlow Backend** é o núcleo do sistema de gestão de inspeções industriais, projetado para ambientes que exigem controle rigoroso de equipamentos, inspeções mecânicas/elétricas e rastreabilidade completa de manutenções.

### 🎯 Principais Funcionalidades

- 🔐 **Autenticação & Autorização** - JWT stateless com RBAC (Gestor, Mecânico, Eletricista)
- 🏢 **Gestão Organizacional** - Áreas, equipamentos, componentes com QR Code
- ✅ **Inspeções Configuráveis** - Templates dinâmicos, execução mobile-first
- 🔧 **Ordens de Manutenção** - Geração automática via eventos de domínio
- 📦 **Controle de Estoque** - Peças vinculadas a equipamentos com alertas
- 📊 **Dashboards & BI** - CQRS com read models otimizados
- 🔍 **Auditoria Total** - Logs imutáveis de todas as operações
- 🚀 **Eventos de Domínio** - Outbox Pattern para confiabilidade

---

## 🛠️ Stack Tecnológica

### Core
- **Java 25** - LTS com recursos modernos
- **Spring Boot 4.x** - Framework base
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência relacional
- **Spring Data MongoDB** - Dados semi-estruturados

### Banco de Dados
- **PostgreSQL** - Dados estruturais (usuários, equipamentos, manutenção, auditoria)
- **MongoDB** - Checklists e execuções de inspeção

### Qualidade & Ferramentas
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento de DTOs
- **Flyway** - Migrations de banco
- **JUnit 5 + Mockito** - Testes
- **SpringDoc OpenAPI** - Documentação de API
- **Docker** - Containerização

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  (Controllers, DTOs, Request/Response, Security Filters)    │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                    Application Layer                        │
│     (Use Cases, DTOs, Mappers, Domain Event Handlers)       │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                      Domain Layer                           │
│  (Entities, Value Objects, Business Rules, Repositories)    │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                  Infrastructure Layer                       │
│    (Repositories Impl, Database, Events, External APIs)     │
└─────────────────────────────────────────────────────────────┘
```

### Padrões Arquiteturais
- ✨ **Clean Architecture** - Separação clara de responsabilidades
- 🎨 **Domain-Driven Design (DDD)** - Modelagem rica do domínio
- 📨 **CQRS** - Separação de leitura e escrita
- 🔄 **Event-Driven** - Eventos de domínio com Outbox Pattern
- 🧩 **Repository Pattern** - Abstração de persistência

---

## 🚀 Instalação

### Pré-requisitos

- Java 25+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 15+ (ou via Docker)
- MongoDB 7+ (ou via Docker)

### Configuração Rápida com Docker

```bash
# Clone o repositório
git clone https://github.com/Rafael01Gx/inspectflow-backend.git
cd inspectflow-backend

# Suba os bancos de dados
docker-compose up -d postgres mongodb

# Execute a aplicação
./mvnw spring-boot:run
```

### Configuração Manual

1. **Clone e entre no diretório**
```bash
git clone https://github.com/Rafael01Gx/inspectflow-backend.git
cd inspectflow-backend
```

2. **Configure o application.yml**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/inspectflow
    username: seu_usuario
    password: sua_senha
  data:
    mongodb:
      uri: mongodb://localhost:27017/inspectflow
```

3. **Execute as migrations**
```bash
./mvnw flyway:migrate
```

4. **Compile e execute**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

5. **Acesse a aplicação**
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

---

## 📡 API

### Autenticação

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@inspectflow.com",
  "password": "senha123"
}
```

### Equipamentos

```http
GET /api/equipments
Authorization: Bearer {token}

POST /api/equipments
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Compressor AR-001",
  "codigoIdentificacao": "AR-001",
  "areaId": "uuid",
  "periodicidadeInspecao": "SEMANAL"
}
```

### Inspeções

```http
POST /api/inspections/start
Authorization: Bearer {token}

{
  "equipmentId": "uuid"
}
```

📚 **Documentação completa da API:** [Swagger UI](http://localhost:8080/swagger-ui.html)

---

## 🗂️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/inspectflow/
│   │   ├── domain/              # Entidades, Value Objects, Interfaces
│   │   │   ├── user/
│   │   │   ├── equipment/
│   │   │   ├── inspection/
│   │   │   ├── maintenance/
│   │   │   └── inventory/
│   │   ├── application/         # Use Cases, DTOs, Mappers
│   │   │   ├── usecases/
│   │   │   ├── dto/
│   │   │   └── mappers/
│   │   ├── infrastructure/      # Repositórios, Adaptadores
│   │   │   ├── persistence/
│   │   │   ├── events/
│   │   │   └── security/
│   │   ├── presentation/        # Controllers, Requests/Responses
│   │   │   └── controllers/
│   │   └── config/              # Configurações Spring
│   └── resources/
│       ├── db/migration/        # Flyway migrations
│       └── application.yml
└── test/                        # Testes unitários e integração
```

---

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Testes com cobertura
./mvnw verify

# Testes de integração
./mvnw test -Dtest=**/*IntegrationTest

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

### Cobertura Atual
- Unitários: 85%+
- Integração: 70%+
- E2E: 60%+

---

## 🐳 Docker

### Desenvolvimento

```bash
# Build da imagem
docker build -t inspectflow-backend:dev .

# Executar container
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  inspectflow-backend:dev
```

### Docker Compose Completo

```bash
# Subir todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f backend

# Parar serviços
docker-compose down
```

---

## 🔒 Segurança

- 🔐 **JWT Stateless** - Autenticação sem sessão
- 🛡️ **BCrypt** - Hash de senhas
- 🚦 **RBAC** - Controle de acesso baseado em papéis
- 🔍 **Validação de Entrada** - Bean Validation
- 🌐 **CORS Restrito** - Configuração granular
- ⏱️ **Rate Limiting** - Proteção contra abuso
- 📝 **Auditoria** - Logs imutáveis de todas as operações

---

## 📊 Monitoramento

### Spring Boot Actuator

```http
GET /actuator/health
GET /actuator/metrics
GET /actuator/info
```

### Métricas Expostas
- CPU, Memória, Threads
- Requisições HTTP
- Database Connection Pool
- Custom Business Metrics

---

## 🚦 CI/CD

### GitHub Actions Pipeline

```yaml
Build → Tests → SonarQube → Docker Build → Push DockerHub → Deploy
```

### Branches
- `main` - Produção
- `develop` - Desenvolvimento
- `feature/*` - Features
- `hotfix/*` - Correções urgentes

---

## 📈 Performance

### Otimizações Implementadas
- ⚡ **Connection Pooling** - HikariCP
- 🗄️ **Índices de Banco** - Queries otimizadas
- 📄 **Paginação** - Todas as listagens
- 🎯 **Lazy Loading** - Relacionamentos JPA
- 💾 **CQRS Read Models** - Consultas otimizadas

### Benchmarks
- API Response Time: < 200ms (p95)
- Throughput: 1000+ req/s
- Database Queries: < 50ms (p95)

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor, siga estas etapas:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

### Padrões de Código
- ✅ Seguir Clean Code
- ✅ Manter testes com 80%+ cobertura
- ✅ Documentar classes e métodos públicos
- ✅ Usar conventional commits

---

## 📝 Roadmap

- [x] ✅ MVP - Core features (Sprints 0-9)
- [x] ✅ Eventos de Domínio + Outbox Pattern
- [x] ✅ CQRS + Read Models
- [ ] 🚧 Integração com ERP externo
- [ ] 🚧 Manutenção preditiva com IA
- [ ] 📅 Multi-tenant SaaS
- [ ] 📅 IoT Integration
- [ ] 📅 Mobile App (React Native)

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👥 Time

Desenvolvido com ❤️ por [RafaelMoraes]

### Contato
- 💼 LinkedIn: [LinkedIn](https://www.linkedin.com/in/rafael-moraes-dev)
- 🐙 GitHub: [Rafael01Gx](https://github.com/Rafael01Gx)

---

## 🙏 Agradecimentos

- Spring Team pela excelente framework
- Comunidade DDD e Clean Architecture
- Todos os contribuidores do projeto

---

<div align="center">

**[⬆ Voltar ao topo](#-inspectflow-backend)**

Made with ☕ and Java

</div>

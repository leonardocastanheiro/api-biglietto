# API Bigglieto

API REST criada para servir como serviço de comunicação entre o front-end de um sistema web de controle de vouchers para hotéis. O objetivo principal é gerenciar vouchers baseando-se em critérios como tipo de reserva, tipo de pensão, status do voucher, entre outros parâmetros relacionados à operação hoteleira.

---

## Descrição

A API Bigglieto foi desenvolvida utilizando Spring Boot, JPA e MySQL para oferecer uma solução robusta e escalável para o controle de vouchers em hotéis. Ela permite a criação, consulta, atualização e exclusão de vouchers, facilitando a integração com sistemas front-end e garantindo a gestão eficiente dos benefícios oferecidos aos hóspedes.

A segurança da API é garantida por autenticação baseada em Bearer Token, assegurando que apenas clientes autorizados possam acessar os recursos protegidos.

### Funcionalidades principais

- Cadastro e gerenciamento de vouchers associados a diferentes tipos de reservas e pensões.
- Filtros avançados por status, validade, tipo de voucher e outras características específicas do negócio hoteleiro.
- Controle de uso e histórico dos vouchers para auditoria e análises.
- Integração RESTful simples e segura com front-ends modernos.
- Autenticação e autorização baseadas em Bearer Token para segurança reforçada.
- Persistência confiável dos dados com banco MySQL e mapeamento via JPA.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security** (para autenticação via Bearer Token)
- **Spring Data JPA**
- **MySQL**
- **Maven/Gradle** (gerenciador de dependências)
- **Lombok** (para redução de boilerplate)
- **Swagger/OpenAPI** (para documentação da API) — se aplicável

---

## Estrutura do Projeto

A arquitetura do projeto está organizada em camadas claras para facilitar manutenção e escalabilidade:

- `authenticator/` — Componentes responsáveis pela autenticação e segurança
- `infra/` — Configurações de infraestrutura, como banco, segurança, e outros recursos técnicos
- `main/`
  - `controllers/` — Endpoints REST para manipulação dos recursos
  - `domain/` — Entidades e regras de negócio centrais
  - `dtos/` — Objetos de transferência de dados entre camadas
  - `repositories/` — Interfaces JPA para acesso ao banco de dados
  - `services/` — Lógica de negócio e operações da aplicação

---

## Como Rodar a Aplicação

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/bigglieto-api.git
cd bigglieto-api

# Order Management API 
Sistema de gerenciamento de pedidos de uma loja.

![Tecnologias](https://skillicons.dev/icons?i=java,spring,docker,postgresql&theme=light)
> 🚧 Projeto em desenvolvimento

## 📌 Funcionalidades
- Cadastro, listagem, atualização e remoção de itens
- Cadastro, listagem, atualização e remoção de usuários
- Admin com propriedades especiais
- Geração de imagens de com IA
- Sistema de carrinho

## 🎢 Estrutura do Projeto
### Arquitetura
Decidi fazer a arquitetura tradicional (Controller -> Service -> Repositories -> Data Base) pois é simples e tem uma estrutura um tanto sólida para esse projeto.

## ⌛ Estado do Projeto
- [x] CRUD de itens
- [x] CRUD de usuários
- [x] Criptografia de senhas
- [ ] Autenticação com JWT
- [ ] Autorização baseada em roles
- [ ] Sistema de carrinho
- [ ] Testes Unitários
- [ ] Testes de Integração

## 🛠 Tecnologias
- Java 17
- Spring Boot 4.1.0
- PostgreSQL
- Docker
- SonarQube
- Swagger

## 📒 Requisitos Mínimos

## 🧭 Como Rodar o Projeto

### 1. Variáveis de ambiente

O projeto lê configurações sensíveis (credenciais do banco e segredo do JWT) de um arquivo `.env` na raiz do projeto. Esse arquivo **não é versionado**: ele está no `.gitignore` e cada pessoa cria o seu.

1. Copie o modelo:
```bash
   cp .env.example .env
```
   No Windows (PowerShell): `Copy-Item .env.example .env`

2. Preencha os valores no `.env`:

   | Variável | Descrição |
   |---|---|
   | `POSTGRES_USER` | Usuário do PostgreSQL |
   | `POSTGRES_PASSWORD` | Senha do PostgreSQL |
   | `JWT_SECRET` | Segredo usado para assinar os tokens JWT (mínimo de 32 caracteres) |
   | `JWT_EXPIRATION_MS` | Tempo de vida do token em milissegundos (`3600000` = 1 hora) |

3. Gere um valor forte para o `JWT_SECRET`:

   **PowerShell**
```powershell
   $b = New-Object byte[] 48
   [Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($b)
   [Convert]::ToBase64String($b)
```

   **Linux/Mac**
```bash
   openssl rand -base64 48
```

> ⚠️ Nunca faça commit do `.env` nem escreva o segredo em arquivos versionados. Se um segredo vazar, gere outro e substitua o antigo.

### 2. Banco de dados

O `docker-compose.yml` sobe o PostgreSQL usando as mesmas variáveis do `.env`:

```bash
docker compose up -d
```

### 3. Aplicação

Com o `.env` preenchido e o banco no ar, rode a aplicação **a partir da raiz do projeto** (é de lá que o `.env` é lido):

```bash
./mvnw spring-boot:run
```

No Windows (PowerShell): `.\mvnw spring-boot:run`

A documentação dos endpoints fica em `http://localhost:8080/swagger-ui/index.html`.


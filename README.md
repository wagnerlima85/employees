# EMPLOYEES

### REQUISITOS BÁSICOS
`MYSQL 8`,`JAVA 11`,`MAVEN`

### GUIA DE INSTALAÇÃO
**EXECUTAR OS SCRIPTS:** CRIAÇÃO([ARQUIVO](scripts/script-2020-30-11.sql))

**CONFIGURAÇÃO BÁSICA:** ALTERAR CONFIGURAÇÕES DE CONEXÃO COM A BASE DE DADOS([ARQUIVO](src/main/resources/application.yml))

### GUIA DE EXECUÇÕES
**EXECUTAR TEST:** `mvn test` 

**EXECUTAR APLICAÇÃO:** `mvn spring-boot:run`

**CARREGAMENTO INICIAL:** `load`

**ALOCAÇÃO DE EMPLOYEES:** `allocate`

**PROMOÇÃO DE EMPLOYEES:** `promote {valor}`

**BALANCEAR EMPLOYEES:** `balance`

**OBS:** Após execução, a aplicação disponibilizará um terminal *shell* para a execução dos comandos pré-definidos

**AUTHOR** Wagner Lima - wagner.lima@outlook.com

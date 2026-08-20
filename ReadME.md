# BudgetAi - Assistente Financeiro Inteligente por Voz

O BudgetAi e uma aplicacao backend que permite registrar e consultar transacoes financeiras de forma conversacional atraves de audio. Utilizando modelos de Inteligencia Artificial para transcricao, raciocinio e sintese de voz, o usuario pode enviar um audio descrevendo um gasto ou fazendo uma pergunta sobre suas financas e receber a acao executada no banco de dados com a resposta falada em audio.

---

## Tecnologias Utilizadas

* Java 25
* Spring Boot 4.x (Web, Data JPA)
* Spring AI:
  * Speech-to-Text (STT): Whisper (whisper-large-v3 via Groq)
  * Chat e Function Calling: Llama 3 / GPT-OSS via Groq
  * Text-to-Speech (TTS): Canopy Labs Orpheus (canopylabs/orpheus-v1-english via Groq)
* Banco de Dados: H2 / PostgreSQL (via Spring Data JPA)
* Gradle como gerenciador de dependencias e build

---

## Como Executar a Aplicacao

### 1. Pre-requisitos
* JDK 25 instalado
* Chave de API da Groq

### 2. Configurar a Chave de API
Exporte a chave da Groq como variavel de ambiente no terminal:

```bash
# Linux/macOS
export GROQ_API_KEY="gsk_sua_chave_aqui"

# Windows (PowerShell)
$env:GROQ_API_KEY="gsk_sua_chave_aqui"

```

### 3. Rodar o Projeto

Na raiz do repositorio, execute:

```bash
./gradlew bootRun

```

A aplicacao iniciara na porta 8080.

---

## Melhoria Implementada

Alem do fluxo de registrar transacoes via audio, foi implementada a Tool de Resumo e Consulta Inteligente de Gastos (GetSummaryTransactionsUseCase):

* **Decisao Autonoma via Function Calling:** O modelo de IA avalia a intencao da fala do usuario. Se for uma declaracao de despesa, a IA executa a ferramenta de persistencia; se for uma duvida ou consulta de saldo/gastos, ela aciona a ferramenta de agregacao e soma no banco de dados.
* **Resposta Multimodal em Audio:** O endpoint `/transactions/ai` processa o audio de entrada e responde diretamente com um arquivo de audio (`.wav`) sintetizado com a resposta da IA.

---

## Como Testar o Fluxo Principal

### 1. Registrar uma Despesa por Audio

Envie um arquivo de audio (`.mp3` ou `.wav`) contendo uma frase de despesa:

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@./src/main/resources/audio/audio-1.mp3" \
  --output resposta.wav

```

### 2. Consultar Gastos por Audio

Envie um audio perguntando sobre os valores acumulados:

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@./src/main/resources/audio/pergunta.mp3" \
  --output resposta_consulta.wav

```

### 3. Consultar via API REST Tradicional

Para verificar as transacoes salvas diretamente por categoria:

```bash
curl -X GET http://localhost:8080/transactions/TRANSPORTE

```

---

## O Que Foi Aprendido

* **Ecossistema Spring AI:** Integracao de modelos de linguagem, transcricao de audio e sintetizacao de voz em um fluxo unificado no ecossistema Spring.
* **Function Calling / Tools:** Exponibilizacao de casos de uso da aplicacao como ferramentas para que a LLM decida autonomamente quando ler ou gravar dados no banco de dados.
* **Integracao com a Groq:** Configuracao de endpoints compativeis com a OpenAI para execucao de modelos abertos com baixa latencia.
* **Tratamento de Arquivos Multipart:** Manipulacao de streams de audio binario de entrada e saida em endpoints REST com Spring Boot.

```

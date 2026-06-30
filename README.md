# 🖥️ RemoteController

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-API%2024+-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202024.09-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-2.9.0-48B983?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-8.12.3-02303A?style=for-the-badge&logo=gradle&logoColor=white)

Aplicativo Android, desenvolvido em **Kotlin** com **Jetpack Compose**, para cadastro de computadores em rede e envio remoto de comandos de energia (desligar, reiniciar, suspender) através de uma API REST.

---

## 📖 Descrição

**RemoteController** é um cliente mobile que permite gerenciar computadores remotamente a partir do celular. O app foi pensado em duas frentes principais:

- **Cadastro de máquinas**: o usuário registra um computador informando nome, endereço IP, endereço MAC e sistema operacional, que são enviados a um backend via API REST.
- **Envio de comandos**: a partir do ID de um computador cadastrado, o usuário escolhe um tipo de comando (`SHUTDOWN`, `RESTART` ou `SLEEP`) e o dispara remotamente.

Toda a comunicação é feita via HTTP com um servidor backend (não incluído neste repositório), consumido através do Retrofit. O app é voltado para quem precisa administrar máquinas em uma rede local — por exemplo, ligar/desligar computadores remotamente sem acesso físico a eles.

---

## ✅ Funcionalidades

- ✅ Cadastro de computadores (nome, IP, MAC, sistema operacional)
- ✅ Envio de comandos remotos (`SHUTDOWN`, `RESTART`, `SLEEP`) por ID do computador
- ✅ Navegação entre telas via bottom navigation bar (Compose Navigation)
- ✅ Comunicação assíncrona com API REST via Retrofit + Coroutines
- ✅ Feedback de status das requisições (sucesso/erro) exibido na tela
- ✅ Interface construída inteiramente em Jetpack Compose com Material 3

---

## 🛠️ Tecnologias

- **Kotlin** 2.0.21
- **Jetpack Compose** (BOM 2024.09.00)
- **Material 3**
- **Navigation Compose** 2.9.8
- **Retrofit** 2.9.0 + **Gson Converter**
- **Kotlin Coroutines** (kotlinx-coroutines-android 1.7.3)
- **Android Lifecycle / ViewModel** (lifecycle-runtime-ktx)
- **Gradle** 8.12.3 (Kotlin DSL)

---

## 🏗️ Arquitetura

O projeto segue uma organização inspirada em **MVVM (Model-View-ViewModel)**:

- **`model/`** — `MainViewModel`, responsável pelo estado da aplicação e pela orquestração das chamadas de rede.
- **`network/`** — camada de acesso à API (`ApiService`, `RetrofitInstance`) e os DTOs de requisição/resposta.
- **`screens/`** — telas Compose (View), que observam o `StateFlow` exposto pelo ViewModel.
- **`ui/theme/`** — definições de tema, cores e tipografia do Material 3.

O estado é compartilhado entre as telas através de um único `MainViewModel`, instanciado no `MainActivity` e propagado via navegação Compose.

---

## 📁 Estrutura do Projeto

```
app/src/main/java/.../remotecontroller/
├── MainActivity.kt          # Entry point + navegação (NavHost / bottom bar)
├── model/
│   └── MainViewModel.kt     # Estado da aplicação e lógica de chamadas à API
├── network/
│   ├── ApiService.kt        # Interface Retrofit + DTOs (Computer/Command)
│   └── RetrofitInstance.kt  # Configuração do client Retrofit
├── screens/
│   ├── ComputerScreen.kt    # Tela de cadastro de computadores
│   └── CommandScreen.kt     # Tela de envio de comandos
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

---

## 🌐 API Consumida

A comunicação é feita via **Retrofit**, com `GsonConverterFactory` para serialização JSON.

> ⚠️ A URL base atualmente está fixa em `http://192.168.0.112:9000/` (IP local da rede de desenvolvimento). Ajuste em `RetrofitInstance.kt` antes de executar o app em outra rede.

| Método | Endpoint | Descrição | Body |
|--------|----------|-----------|------|
| `POST` | `api/v1/computers` | Cadastra um novo computador | `name`, `ipAddress`, `macAddress`, `operatingSystem` |
| `POST` | `api/v1/commands` | Envia um comando para um computador | `type` (`SHUTDOWN`/`RESTART`/`SLEEP`), `computerId` |

---

## 🔄 Fluxo da Aplicação

```
Usuário abre o app
        ↓
Tela "Computadores" (cadastro)
        ↓
Preenche nome, IP, MAC e SO → POST /api/v1/computers
        ↓
Navega para "Comandos" pela bottom bar
        ↓
Informa o ID do computador e escolhe o tipo de comando
        ↓
POST /api/v1/commands
        ↓
Mensagem de status (sucesso ou erro) exibida na tela
```

---

## 📱 Principais Telas

| Tela | Arquivo | Responsabilidade |
|------|---------|-------------------|
| **Computadores** | `ComputerScreen.kt` | Formulário para cadastrar um novo computador na rede |
| **Comandos** | `CommandScreen.kt` | Formulário para selecionar um comando e enviá-lo a um computador pelo ID |

A navegação entre as duas telas é feita por uma `NavigationBar` fixa na parte inferior, definida em `MainActivity.kt`.

---

## 🚀 Como Executar

### Pré-requisitos

- Android Studio (Hedgehog ou superior)
- JDK 11
- Android SDK 36 (compileSdk/targetSdk)
- minSdk 24 (Android 7.0+)
- Um backend compatível rodando e acessível na rede (endpoints `api/v1/computers` e `api/v1/commands`)

### Passo a passo

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>

# 2. Abra a pasta do projeto no Android Studio
cd remotecontroller

# 3. Ajuste a URL base da API, se necessário
# Edite app/src/main/java/.../network/RetrofitInstance.kt

# 4. Sincronize o Gradle
./gradlew build

# 5. Execute em um emulador ou dispositivo físico
./gradlew installDebug
```

> 💡 Como o app usa `android:usesCleartextTraffic="true"`, ele já está preparado para consumir APIs HTTP (sem TLS) em rede local — ideal para testes com um backend doméstico.

---

## 📦 Principais Dependências

| Biblioteca | Versão | Uso |
|------------|--------|-----|
| `androidx.core:core-ktx` | 1.13.1 | Extensões Kotlin para o Android SDK |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.10.0 | ViewModel e ciclo de vida |
| `androidx.activity:activity-compose` | 1.13.0 | Integração Activity + Compose |
| `androidx.compose:compose-bom` | 2024.09.00 | BOM das bibliotecas Compose |
| `androidx.navigation:navigation-compose` | 2.9.8 | Navegação entre telas Compose |
| `com.squareup.retrofit2:retrofit` | 2.9.0 | Cliente HTTP |
| `com.squareup.retrofit2:converter-gson` | 2.9.0 | Conversão JSON ↔ objetos Kotlin |
| `org.jetbrains.kotlinx:kotlinx-coroutines-android` | 1.7.3 | Programação assíncrona |

---

## 💡 Possíveis Melhorias

- 🔧 Externalizar a URL base da API (`RetrofitInstance.kt`) para um arquivo de configuração/build variant, em vez de IP fixo no código
- ✅ Adicionar testes unitários e instrumentados cobrindo `MainViewModel` e as chamadas de rede
- 📋 Listar computadores já cadastrados (atualmente não há tela de listagem/seleção)
- 🛡️ Tratamento de erros mais granular (timeout, sem conexão, erro de servidor)
- 🔒 Suporte a HTTPS e autenticação na API
- 🌗 Suporte a tema escuro dinâmico
- ♻️ Mover `_message` do `MainViewModel` para `private` com encapsulamento adequado (atualmente exposto como `public`)

---

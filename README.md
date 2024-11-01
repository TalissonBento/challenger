# Desafio

Este aplicativo demonstra boas práticas utilizando recursos do Kotlin e bibliotecas Jetpack mais recentes para Android. Ele se conecta à API da SpaceX para exibir informações sobre sua frota de foguetes.

## 🎯 Objetivo

O objetivo é mostrar boas práticas em Kotlin e Android.

## 📜 Descrição

O aplicativo baixa informações sobre a frota de foguetes da SpaceX e adota uma abordagem **offline-first**, onde os dados são sempre exibidos a partir de uma persistência local e atualizados quando necessário.

### Funcionalidades:
- **Atualização de dados**: A lista pode ser atualizada usando o gesto de arrastar para baixo.
- **Suporte a temas**: Suporte automático para temas claro e escuro.

## 📚 Tecnologias e Bibliotecas Utilizadas

- **Modularização Gradle**: Projeto modularizado por funcionalidades.
- **Arquitetura Limpa com padrão MVI**: Utilizado na camada de apresentação.
- **Jetpack Compose com Material3**: Design moderno para a camada de UI.
- **Kotlin Coroutines & Kotlin Flow**: Para concorrência e abordagem reativa.
- **Kotlin Serialization**: Para conversão e análise de JSON.
- **Retrofit**: Para comunicação de rede com a API.
- **Hilt**: Injeção de dependência.
- **Room**: Banco de dados local para persistência.
- **Coil**: Carregamento eficiente de imagens.
- **Version Catalog**: Para gerenciamento de dependências.
- **Perfis de Base e Startup**: Para melhorar o desempenho de inicialização do app.
- **Timber**: Ferramenta de logging.
- **JUnit5, Turbine e MockK**: Testes unitários.
- **Jetpack Compose Testes, Maestro e Hilt**: Testes de interface de usuário (UI).
- **GitHub Actions**: CI/CD automatizado.
- **Renovate**: Para atualização automática de dependências.
- **KtLint e Detekt**: Verificação e formatação de código.

## 🛠️ Estrutura do Projeto

Este projeto segue uma estrutura modular, com as funcionalidades organizadas em módulos distintos para facilitar a manutenção e evolução do código.

## 🚀 Como Rodar o Projeto

1. Clone este repositório: `git clone git://github.com/TalissonBento/challenger`
2. Abra o projeto no Android Studio.
3. Compile e rode o aplicativo em um emulador ou dispositivo físico.

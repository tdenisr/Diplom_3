# Diplom_3
## Используемые инструменты
* Java 11 
* maven 3.8.1
* JUnit 4.13.2 
* rest-assured 5.3.1 
* allure 2.23.0 
* maven-surefire-plugin 2.22.2 
* javafaker 1.0.2 
* gson 2.10.1
* selenium 3.141.59
##  Запуск тестов и построение отчёта
```
mvn clean test 
mvn allure:serve
```
## Тестируемая система
- web-приложение [Stellar Burgers](https://stellarburgers.nomoreparties.site/)
- [Документация к API](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf)
## Цели
* Для Page Object создан отдельный пакет.
* Для каждой страницы создан отдельный класс с Page Object.
* Тесты разделены по тематике или функциональности. 
* Нужные тестовые данные создаются перед тестом и удаляются после того, как он выполнится.
* Тестовый юзер создаётся с помощью API.
* Сформировать отчёт Allure
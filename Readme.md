# Task Management System

---
### Инструкция по запуску приложения


1. Склонируйте проект из репозитория
2. У вас должен быть установлен Docker  
    * Скачайте [Docker Desktop](https://www.docker.com/products/docker-desktop/)  
    * Следуйте указаниям установки
    * Убедитесь, что установка выполнена успешно. Откройте Docker Desktop
3. Откройте проект TaskManagement в IDE
4. Запустите Terminal и введите следующие команды:
    * cd docker
    * docker compose up
5. Убедитесь, что контейнеры запустились (в Docker Desktop появился мульти-контейнер docker со статусом Running)
6. Запустите приложение из класса TaskManagementApplication
7. Когда приложение запустится, откройте **Swagger** по адресу:
    * >http://localhost:8080/swagger-ui/index.html

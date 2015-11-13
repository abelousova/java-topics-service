# java-topics-service

Quick Start:
 - mvn install
 - cd service-main
 - mvn spring-boot:run -Dru.test.abelousova.topics.base_dir=../base_dir

При этом приложение запустится для рабочей директории base_dir в корне проекта. Чтобы указать другую рабочую директорию,
запустите

 - mvn install
 - cd service-main
 - mvn spring-boot:run -Dru.test.abelousova.topics.base_dir=[путь к директории]

Приложение запускает REST-сервис:
 - GET localhost:3000/topics/ --> получить список топиков для директории
 - GET localhost:3000/topics/[topic_name] --> получить таймстемп последнего запуска для топика topic_name
 - GET localhost:3000/topics/[topic_name]/stats --> получить статистику последнего запуска топика topic_name
 - GET localhost:3000/topics/[topic_name]/partitions --> список партиций и число сообщений по каждой партиции последнего запуска топика topic_name

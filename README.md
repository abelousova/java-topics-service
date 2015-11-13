# java-topics-service

Quickstart:
  mvn install
  cd service-main
  mvn spring-boot:run

При этом приложение запустится для рабочей директории base_dir в корне проекта. Чтобы указать другую рабочую директорию,
запустите

  mvn install
  cd service-main
  mvn spring-boot:run -Dru.test.abelousova.topics.base_dir=[путь к директории]
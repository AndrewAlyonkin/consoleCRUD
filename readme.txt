Консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:
Writer (id, firstName, lastName, List<Post> posts)
Post (id, content, created, updated, List<Label> labels)
Label (id, name)
PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED)

Выполнено в соответствии с патерном MVC;
Для миграции БД используется Liquibase - https://www.liquibase.org/ ;
Сервисный слой приложения покрыт юнит тестами (junit + mockito);
Слой доступа к БД покрыт юнит-тестами(junit);
Проект собирается через Maven;

Стек технологий: Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito.

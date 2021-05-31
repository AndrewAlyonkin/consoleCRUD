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

Для запуска приложения:
1. С помощью СУБД создать необходимую базу данных.
2. В pom.xml указать зависимость от конкретного драйвера необходимой базы данных.
На данный момент установлена зависимость от драйвера для PostgreSQL.
2. В файле src/main/resources/DBCredentials.properties указать параметры доступа к базе данных:

    url=url по которому можно подключиться к БД
    username=имя пользователя
    password=пароль
    driver=драйвер базы данных

        Параметры для Liquibase:
    classpath=название jar-файла - драйвера (в формате *****.jar ), зависимость от которого указана в pom.xml

3. После сборки проекта выполнить команду " mvn liquibase:update " для создания в базе данных необходимых
для работы приложения таблиц с помощью Liquibase.
4. При необходимости Liquibase позволяет откатить изменения, внесенные в БД командой " mvn liquibase:rollback -Dliquibase.rollbackCount=3 "



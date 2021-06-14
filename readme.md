[![Build Status](https://travis-ci.com/AndrewAlyonkin/consoleCRUD.svg?branch=master)](https://travis-ci.com/AndrewAlyonkin/consoleCRUD)

Консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:
- Writer (id, firstName, lastName, List<Post> posts)
- Post (id, content, created, updated, List<Label> labels)
- Label (id, name)
- PostStatus (enum ACTIVE, UNDER_REVIEW, DELETED)

Выполнено в соответствии с патерном MVC;  
Для миграции БД используется Liquibase - https://www.liquibase.org/ и flyway ;  
Сервисный слой приложения покрыт юнит тестами (junit + mockito);  
Проект собирается через Maven;

* Стек технологий: 
  * Java
  * MySQL
  * JDBC
  * Maven
  * Liquibase, FlyWay 
  * JUnit 
  * Mockito 
  * Travis

Для запуска приложения:
Приложение может запускаться в двух разных профилях:

###Профиль 1 - работа с базой данных через JDBC:
>1. С помощью СУБД создать необходимую базу данных.
>2. В pom.xml указать зависимость от конкретного драйвера необходимой базы данных.
>На данный момент установлена зависимость от драйвера для PostgreSQL.
>2. В файле src/main/resources/DBCredentials.properties указать параметры доступа к базе данных:  
    ``url=url по которому можно подключиться к БД
    username=имя пользователя
    password=пароль
    driver=драйвер базы данных  
Параметры для Liquibase:
    classpath=название jar-файла - драйвера (в формате *****.jar ), зависимость от которого указана в pom.xml```

>3. После сборки проекта выполнить команду " mvn liquibase:update " для создания в базе данных необходимых
для работы приложения таблиц и зависимостей с помощью Liquibase.
>4. При необходимости Liquibase позволяет откатить изменения, внесенные в БД командой " mvn liquibase:rollback -Dliquibase.rollbackCount=3 "

###Профиль 2 - работа с базой данных через Hibernate
>1. С помощью СУБД создать необходимую базу данных.
>2. В файле pom.xml для плагина <flyway> указать небходимые конфигурации:

```<configuration>
  <driver> {Драйвер базы данных в формате "org.postgresql.Driver"} </driver>
  <url> {url по которому можно подключиться к БД} </url>
  <user> {имя пользователя БД} </user>
  <password> {пароль БД} </password>
</configuration>
```  


>3. В файле hibernate.cfg.xml указать необходимые параметры для подключения к базе данных
```<property name="hibernate.dialect"> {диалект базы данных} </property>
<property name="hibernate.connection.driver_class"> {Драйвер базы данных в формате "org.postgresql.Driver"} </property>
<property name="hibernate.connection.url"> {url по которому можно подключиться к БД} </property>
<property name="hibernate.connection.username"> {имя пользователя БД} </property>
<property name="hibernate.connection.password"> {пароль БД} </property>
```
>4. После сборки проекта выполнить команду " mvn clean flyway:migrate " для создания в базе данных необходимых
для работы приложения таблиц и зависимостей с помощью Liquibase.
>5. При необходимости Liquibase позволяет откатить изменения, внесенные в БД командой " mvn clean flyway:clean "

 Для выбора провиля при сборке и запуске проекта через мавен указать один из профилей:
  >-Phiber  
  >-Pjdbc

 Для старта программы запустить метод main( ) в классе Console.


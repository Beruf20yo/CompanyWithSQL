# CompanyWithSQL
Для работы с проектом необходимо JDK 17, PostgresSQL и консоль для взаимодействия с БД(или её аналог)  
Данный проект улучшает проект Company путём добавления Postgres SQL и использованием JDBC    
Всё, что ранее хранилось в списках, теперь реализуется с помощью БД  
Для того, чтобы программа полноценно функционировала нужно создать базу данных infoofcompanies  
CREATE DATE infoofcompanies;  
а также нужно создать две таблицы в данной базе данных:  
CREATE TABLE company(  
    id_company serial primary key,  
    name_company varchar(100),  
    income_company numeric default 0  
);  
CREATE TABLE employee(
    id_employee serial primary key,
    id_company int references company(id_company) on delete cascade,
    type_employee varchar(20),
    mounth_salary numeric,
    income_for_company numeric default 0
);
  
  Также необходимо проверить значения по умолчанию, в программе они находятся в SQLWorker.java:  
  User: postgres  
  Password: postgres  
  Если вы ставили иной пароль, то лучше поменять его в программе  
  После данной настройки всё должно полноценно функционировать

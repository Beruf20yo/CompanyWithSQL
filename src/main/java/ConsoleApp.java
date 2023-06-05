import company.Company;
import employee.Employee;
import employee.Manager;
import employee.Operator;
import employee.TopManager;
import sqlwork.DbConnector;
import sqlwork.SQLRequests;
import sqlwork.SQLWork;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner = new Scanner(System.in);
    public static Company company;
    private SQLWork sqlWork;
    protected SQLRequests sqlRequests = new SQLRequests();
    public void StartWork(){
        try {
            Connection connection = DbConnector.connectToDb();
            System.out.println("Успешное подключение к БД");
            sqlWork = new SQLWork(connection);
            company = new Company(sqlWork);
            System.out.println("На данный момент доступны компании: ");
            sqlWork.generateSelectCompanies();
            System.out.println("""  
                Что вы хотите сделать?
                1. Создать компанию
                2. Выбрать компанию
                3. Покинуть программу""");
            String input = scanner.nextLine();
            int chose = Integer.parseInt(input);
            switch (chose) {
                case 1 -> newCompany();
                case 2 -> choseCompany();
                case 3 -> System.out.println("Всего доброго");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Ошибка при подключении к бд");
        }
    }
    public  void choseCompany(){
        System.out.println("Введите номер компании, которую хотите выбрать");
        String input = scanner.nextLine();
        int idCompany = Integer.parseInt(input);
        mainInterface(idCompany);
    }
    public void newCompany() {
        System.out.println("Введите название компании: ");
        String input = scanner.nextLine();
        sqlWork.generateExecute(sqlRequests.insertCompany(input));
        System.out.println("Имя компании установлено!");
        int idCompany = sqlWork.getIdCompany(input);
        mainInterface(idCompany);
    }

    public  void mainInterface(int idCompany) {
        while (true) {
            System.out.println("""
                    Основная программа
                    Что вы хотите сделать?
                    1. Добавить сотрудника
                    2. Удалить сотрудника
                    3. Узнать доход компании
                    4. Узнать информацию о зарплатах компании
                    5. Создать ещё одну компанию/Выбрать другую
                    6. Добавить сотрудников списком""");// Список заранее прописан в программе
            String input = scanner.nextLine();
            if (input.replace(" ", "").equalsIgnoreCase("exit")) {
                System.out.println("Всего доброго");
                System.exit(0);
            }
            int chose = Integer.parseInt(input);
            switch (chose) {
                case 1 -> addEmployee(idCompany);
                case 2 -> deleteEmployee(idCompany);
                case 3 -> System.out.println("Доход вашей компании: " + "\n" + company.getIncome(idCompany));
                case 4 -> getSalaryInfo(idCompany);
                case 5 -> choseCompany();
                case 6 -> {
                    List<Employee> employeeToAdd = List.of(new Manager(idCompany),
                            new TopManager(idCompany), new Operator(idCompany),
                            new Manager(idCompany), new Manager(idCompany));
                    company.hireAll(employeeToAdd, idCompany);
                    System.out.println("Сотрудники добавлены");
                }
            }
        }
    }

    public void addEmployee(int idCompany) {
        System.out.println("""
                Какого сотрудника вы хотите добавить?
                1. Manager
                2. TopManager
                3. Operator""");
        String input = scanner.nextLine();
        int choseId = Integer.parseInt(input);
        int count;
        System.out.println("Сколько сотрудников нужно добавить?");
        input = scanner.nextLine();
        count = Integer.parseInt(input);
        while (count != 0) {
            Employee employee = addEmployee(choseId, idCompany);
            if (employee == null) {
                return;
            } else {
                company.hire(employee,idCompany);
                count--;
            }
        }
        System.out.println("Сотрудник(и) добавлен(ы)");
        mainInterface(idCompany);
    }


    public  Employee addEmployee(int idEmployeeToAdd,int idCompany) {
        return switch (idEmployeeToAdd) {
            case 1 -> new Manager(idCompany);
            case 2 -> new TopManager(idCompany);
            case 3 -> new Operator(idCompany);
            default -> throw new IllegalStateException("Unexpected value: " + idEmployeeToAdd);
        };
    }

    public  void deleteEmployee(int idCompany) {
        System.out.println("""
                Как вы хотите удалить сотрудника?
                1. Удалить нескольких
                2. Удалить по ID
                3. Удалить любого""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        switch (chose) {
            case 1 -> deleteSomeEmployee(idCompany);
            case 2 -> {
                System.out.println("Введите ID сотрудника в списке: ");
                input = scanner.nextLine();
                int id = Integer.parseInt(input);
                company.fire(id,idCompany);
                mainInterface(idCompany);
            }
            case 3 -> company.fire(idCompany);
        }
    }

    public  void deleteSomeEmployee(int idCompany) {
        System.out.println("""
                Сколько сотрудников нужно удалить?
                1. По количеству
                2. По процентам""");
        String input = scanner.nextLine();
        int count = Integer.parseInt(input);
        switch (count) {
            case 1 -> {
                System.out.println("Введите кол-во сотрудников для удаления");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                while ((count != 0 ) || company.getCountOfEmployees(idCompany) != 0) {
                    company.fire(idCompany);
                    count--;
                }
                System.out.println("Сотрудники удалены");
                mainInterface(idCompany);
            }
            case 2 -> {
                System.out.println("Введите процент сотрудников для удаления");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                int i = company.getCountOfEmployees(idCompany) * count / 100;
                while (i != 0 || company.getCountOfEmployees(idCompany) != 0) {
                    company.fire(idCompany);
                    i--;
                }
                System.out.println("Сотрудники удалены");
                mainInterface(idCompany);
            }
        }

    }
    public  void getSalaryInfo(int idCompany) {
        System.out.println("""
                Что вы хотите сделать?
                1. Узнать все уникальные зарплаты
                2. Узнать самые высокие зарплаты
                3. Узнать самые низкие зарплаты""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        switch (chose) {
            case 1 -> {
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany,company.getCountOfEmployees(idCompany), "Top");
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
            case 2 -> {
                switchSalaryStaff("TopSalaryStaff",idCompany);
            }
            case 3 -> {
                switchSalaryStaff("LowSalaryStaff",idCompany);
            }
            default -> throw new IllegalStateException("Unexpected value: " + chose);
        }
    }

    public void switchSalaryStaff(String chose,int idCompany){
        System.out.println("Введите количество зарплат: ");
        String input = scanner.nextLine();
        int count = Integer.parseInt(input);
        switch (chose) {
            case "TopSalaryStaff" -> {
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany, count, "Top" );
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
            case "LowSalaryStaff" -> {
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany, count, "Low");
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
        }
    }

}

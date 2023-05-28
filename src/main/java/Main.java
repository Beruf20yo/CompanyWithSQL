import company.Company;
import employee.Employee;
import employee.Manager;
import employee.Operator;
import employee.TopManager;
import sqlwork.SQLWork;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static SQLWork sqlWork = new SQLWork();
    public static Scanner scanner = new Scanner(System.in);
    public static Company company = new Company();


    public static void main(String[] args) {
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
    }
    public static void choseCompany(){
        System.out.println("Введите номер компании, которую хотите выбрать");
        String input = scanner.nextLine();
        int idCompany = Integer.parseInt(input);
        mainInterface(idCompany);
    }
    public static void newCompany() {
        System.out.println("Введите название компании: ");
        String input = scanner.nextLine();
        sqlWork.generateInsertCompany(input);
        System.out.println("Имя компании установлено!");
        int idCompany = sqlWork.getIdCompany(input);
        mainInterface(idCompany);
    }

    public static void mainInterface(int idCompany) {
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

    public static void addEmployee(int idCompany) {
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

    @SuppressWarnings("unchecked")
    public static <T> T addEmployee(int idEmployeeToAdd,int idCompany) {
        T emp;
        if (idEmployeeToAdd == 1) {
            emp = (T) new Manager(idCompany);
            return emp;
        } else if (idEmployeeToAdd == 2) {
            emp = (T) new TopManager(idCompany);
            return emp;
        } else if (idEmployeeToAdd == 3) {
            emp = (T) new Operator(idCompany);
            return emp;
        } else {
            return null;
        }
    }

    public static void deleteEmployee(int idCompany) {
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

    public static void deleteSomeEmployee(int idCompany) {
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
    public static void getSalaryInfo(int idCompany) {
        System.out.println("""
                Что вы хотите сделать?
                1. Узнать все уникальные зарплаты
                2. Узнать самые высокие зарплаты
                3. Узнать самые низкие зарплаты""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        int count;
        switch (chose) {
            case 1 -> {
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany,company.getCountOfEmployees(idCompany), 0);
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
            case 2 -> {
                System.out.println("Введите количество зарплат: ");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany,count, 0);
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
            case 3 -> {
                System.out.println("Введите количество зарплат: ");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                List<Integer> employeeList = company.getTopOrLowSalaryStaff(idCompany,count, 1);
                for (int employeeSalary : employeeList) {
                    System.out.println(employeeSalary);
                }
            }
        }
    }

}
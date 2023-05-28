package sqlwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLWork {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/infoofcompanies";
    protected ResultSet result;
    public void generateInsertCompany(String name) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "INSERT INTO company (name_company) " +
                    "VALUES ('" + name + "')";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateSelectCompanies() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String allCompanies = "select id_company, name_company from company order by id_company;";
            result = statement.executeQuery(allCompanies);
            while (result.next()) {
                System.out.println(result.getInt("id_company")+" "+result.getString("name_company"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getIdCompany(String nameCompany){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String selectIdCompany = "select id_company from company where name_company ='"+nameCompany +"';";
            result = statement.executeQuery(selectIdCompany);
            result.next();
            return result.getInt("id_company");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void generateInsertEmployee(int idCompany, String typeEmployee, int mounthSalary, int incomeForCompany) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "INSERT INTO employee (id_company, type_employee, mounth_salary, income_for_company) " +
                    "VALUES (" + idCompany + ",'" + typeEmployee + "'," + mounthSalary + "," + incomeForCompany + ")";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateUpdateCompanyIncome(int id_company, int incomeForCompany) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "update company set income_company = income_company +"+incomeForCompany+" where id_company ="+id_company+";";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> generateTopOrLowSalaryStaff(int limitCountOfSalary, int idCompany, int setTopOrLow) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String SalaryStaff;
            List<Integer> liftOfSalary = new ArrayList<>();
            if(setTopOrLow == 1){
                SalaryStaff = "(select distinct mounth_salary from employee where id_company =" + idCompany + ") " +
                        "order by mounth_salary limit " + limitCountOfSalary + ";";
            } else {
                SalaryStaff = "(select distinct mounth_salary from employee where id_company =" + idCompany + ") " +
                        "order by mounth_salary desc limit " + limitCountOfSalary + ";";
            }
            result =  statement.executeQuery(SalaryStaff);
            while (result.next()){
                liftOfSalary.add(result.getInt("mounth_salary"));
            }
            return liftOfSalary;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateDeleteEmployee(int idEmployee){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "delete from employee where id_employee = "+ idEmployee +";";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getIdCompanyIncomeOfEmployee(int idEmployee){
        int[] results = new int[2];
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "select id_company,income_for_company from employee where id_employee = "+ idEmployee +";";
            result = statement.executeQuery(newCompany);
            result.next();
            results[0] = result.getInt("id_company");
            results[1] = result.getInt("income_for_company");
            return results;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public int generateSelectCountOfEmployee(int idCompany){
        int countOfEmployee;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "SELECT COUNT(*) as employees_count FROM employee where id_company ="+ idCompany +";";
            result = statement.executeQuery(newCompany);
            result.next();
            countOfEmployee = result.getInt("employees_count");
            return countOfEmployee;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void generateSimpleDelete(int idCompany){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "delete from employee where id_company = "+ idCompany +";";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int generateSelectIncome(int idCompany){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "SELECT income_company FROM company where id_company ="+ idCompany +";";
            result = statement.executeQuery(newCompany);
            result.next();
            return result.getInt("income_company");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void generateUpdateTopManagerSalary(int idCompany, int ratePerMounth){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String newCompany = "update employee set mounth_salary = ("+ratePerMounth+" * 5 ) / 2 where id_company ="+ idCompany +" and type_employee = 'TopManager';";
            statement.execute(newCompany);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package sqlwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLWork {
    private final Connection connection;
    protected ResultSet result;
    public SQLWork(Connection connection){
        this.connection = connection;
    }
    public void generateExecute(String operation){
        try(Statement statement = connection.createStatement()){
            statement.execute(operation);
        }catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateSelectCompanies() {
        try (Statement statement = connection.createStatement()) {
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
        try (Statement statement = connection.createStatement()) {
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

    public List<Integer> generateTopOrLowSalaryStaff(int limitCountOfSalary, int idCompany, String setTopOrLow) {
        try (Statement statement = connection.createStatement()) {
            String SalaryStaff = "";
            List<Integer> liftOfSalary = new ArrayList<>();
            switch (setTopOrLow){
                case "Low" -> SalaryStaff = "(select distinct mounth_salary from employee where id_company =" + idCompany + ") " +
                        "order by employee.mounth_salary limit " + limitCountOfSalary + ";";
                case "Top" -> SalaryStaff = "(select distinct mounth_salary from employee where id_company =" + idCompany + ") " +
                        "order by employee.mounth_salary desc limit " + limitCountOfSalary + ";";
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


    public int[] getIdCompanyIncomeOfEmployee(int idEmployee){
        int[] results = new int[2];
        try (Statement statement = connection.createStatement()) {
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
        try (Statement statement = connection.createStatement()) {
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

    public int generateSelectIncome(int idCompany){
        try (Statement statement = connection.createStatement()) {
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

}


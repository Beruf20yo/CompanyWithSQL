package sqlwork;

public class SQLRequests {
    public String insertCompany(String name){
        return "INSERT INTO company (name_company) " +
                "VALUES ('" + name + "')";
    }
    public String deleteEmployee(int idEmployee){
        return "delete from employee where id_employee = "+ idEmployee +";";
    }
    public String simpleDelete(int idCompany){
        return "delete from employee where id_company = "+ idCompany +";";
    }
    public String updateTopManagerSalary(int idCompany, int ratePerMounth){
        return "update employee set mounth_salary = ("+ratePerMounth+" * 5 ) / 2 where id_company ="+ idCompany +
                " and type_employee = 'TopManager';";
    }
    public String UpdateCompanyIncome(int id_company, int incomeForCompany){
        return "update company set income_company = income_company + "+incomeForCompany+" where id_company ="+id_company+";";
    }
    public String InsertEmployee(int idCompany, String typeEmployee, int mounthSalary, int incomeForCompany){
        return "INSERT INTO employee (id_company, type_employee, mounth_salary, income_for_company) " +
                "VALUES (" + idCompany + ",'" + typeEmployee + "'," + mounthSalary + "," + incomeForCompany + ")";
    }
}

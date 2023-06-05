package company;

import employee.Employee;
import sqlwork.SQLRequests;
import sqlwork.SQLWork;
import java.util.ArrayList;
import java.util.List;


public class Company {

   protected SQLWork sqlWork;
   protected SQLRequests sqlRequests = new SQLRequests();

   public Company(SQLWork sqlWork){
       this.sqlWork = sqlWork;
   }

    public void hire(Employee employee, int idCompany) {
       sqlWork.generateExecute(sqlRequests.InsertEmployee(idCompany,employee.getTypeEmployee(),
               employee.getMonthSalary(),employee.getIncomeForCompany()));
       sqlWork.generateExecute(sqlRequests.UpdateCompanyIncome(idCompany,employee.getIncomeForCompany()));
       if(getIncome(idCompany)>10000000){
            sqlWork.generateExecute(sqlRequests.updateTopManagerSalary(idCompany,employee.getRatePerMounth()));
       }
    }

    public void hireAll(List<Employee> lisdToAdd, int idCompany) {
        for(Employee employee: lisdToAdd){
            sqlWork.generateExecute(sqlRequests.InsertEmployee(idCompany,employee.getTypeEmployee(),
                    employee.getMonthSalary(),employee.getIncomeForCompany()));
            sqlWork.generateExecute(sqlRequests.UpdateCompanyIncome(idCompany,employee.getIncomeForCompany()));
        }
    }

    public void fire(int id, int idCompany) {
        int[] idAndIncome = sqlWork.getIdCompanyIncomeOfEmployee(id);
        if(idAndIncome[0] != idCompany){
            System.out.println("Сотрудника с таким id нет в вашей компании");
        } else {
            sqlWork.generateExecute(sqlRequests.UpdateCompanyIncome(idAndIncome[0],-idAndIncome[1]));
            sqlWork.generateExecute(sqlRequests.deleteEmployee(id));
        }
    }

    public int getCountOfEmployees(int idComapany){
        return sqlWork.generateSelectCountOfEmployee(idComapany);
    }
    public void fire(int idCompany) {
       sqlWork.generateExecute(sqlRequests.simpleDelete(idCompany));

    }

    public List<Integer> getTopOrLowSalaryStaff(int idCompany, int count, String chose) {
        ArrayList<Integer> listOfSalary = new ArrayList<>();
       switch (chose){
            case "Top" -> listOfSalary.addAll(sqlWork.generateTopOrLowSalaryStaff(count,idCompany,"Top"));
            case "Low" -> listOfSalary.addAll(sqlWork.generateTopOrLowSalaryStaff(count,idCompany,"Low"));
            default -> throw new IllegalStateException("Unexpected value: " + chose);
        }
        return listOfSalary;
    }

    public int getIncome(int idCompany) {
       return sqlWork.generateSelectIncome(idCompany);

    }
}
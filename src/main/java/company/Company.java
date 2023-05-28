package company;

import employee.Employee;
import sqlwork.SQLWork;
import java.util.ArrayList;
import java.util.List;


public class Company {

   protected SQLWork sqlWork = new SQLWork();

    public void hire(Employee employee, int idCompany) {
        sqlWork.generateInsertEmployee(idCompany,employee.getTypeEmployee(),employee.getMonthSalary(),employee.incomeForCompany());
        sqlWork.generateUpdateCompanyIncome(idCompany,employee.incomeForCompany());
        if(getIncome(idCompany)>10000000){
            sqlWork.generateUpdateTopManagerSalary(idCompany,employee.getRatePerMounth());
        }
    }

    public void hireAll(List<Employee> lisdToAdd, int idCompany) {
        for(Employee employee: lisdToAdd){
            sqlWork.generateInsertEmployee(idCompany,employee.getTypeEmployee(),employee.getMonthSalary(),employee.incomeForCompany());
            sqlWork.generateUpdateCompanyIncome(idCompany,employee.incomeForCompany());
        }
    }

    public void fire(int id, int idCompany) {
        int[] idAndIncome = sqlWork.getIdCompanyIncomeOfEmployee(id);
        if(idAndIncome[0] != idCompany){
            System.out.println("Сотрудника с таким id нет в вашей компании");
        } else {
            sqlWork.generateUpdateCompanyIncome(idAndIncome[0],-idAndIncome[1]);
            sqlWork.generateDeleteEmployee(id);
        }
    }

    public int getCountOfEmployees(int idComapany){
        return sqlWork.generateSelectCountOfEmployee(idComapany);
    }
    public void fire(int idCompany) {
        sqlWork.generateSimpleDelete(idCompany);
    }

    public List<Integer> getTopOrLowSalaryStaff(int idCompany, int count, int setChose) {
        ArrayList<Integer> listOfSalary = new ArrayList<>();
        if(setChose == 0){
            listOfSalary.addAll(sqlWork.generateTopOrLowSalaryStaff(count,idCompany,0));
        }else {
            listOfSalary.addAll(sqlWork.generateTopOrLowSalaryStaff(count,idCompany,1));
        }
        return listOfSalary;
    }

    public int getIncome(int idCompany) {
       return sqlWork.generateSelectIncome(idCompany);

    }
}
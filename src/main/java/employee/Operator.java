package employee;

public class Operator extends Employee {
    public Operator(int idCompany){
        ratePerMounth = 40000;
        typeEmployee = "Operator";
        monthSalary = ratePerMounth;
        incomeForCompany = 0;
        this.idCompany = idCompany;
    }

}
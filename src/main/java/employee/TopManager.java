package employee;
public class TopManager extends Employee {
    protected  int ratePerMounth = 80000;
    public TopManager(int idCompany) {
        typeEmployee = "TopManager";
        incomeForCompany = 0;
        this.idCompany = idCompany;
        this.monthSalary = ratePerMounth;
    }
}
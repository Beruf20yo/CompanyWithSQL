package employee;
public class TopManager implements Employee {

    protected  int ratePerMounth = 80000;
    protected String typeEmployee = "TopManager";
    protected int monthSalary;
    protected int idCompany;

    public TopManager(int idCompany) {
        this.idCompany = idCompany;
        this.monthSalary = ratePerMounth;
    }
    public int getRatePerMounth() {
        return ratePerMounth;
    }

    @Override
    public int getMonthSalary() {
        return monthSalary;
    }
    public int incomeForCompany() {
        return 0;
    }

    public String getTypeEmployee() {
        return typeEmployee;
    }
}
package employee;

public class Operator implements Employee {
    protected int monthSalary = 40000;
    protected  int ratePerMounth = 40000;
    protected String typeEmployee = "Operator";
    protected int idCompany;

    public Operator(int idCompany){
        this.idCompany = idCompany;
    }
    public int getRatePerMounth() {
        return ratePerMounth;
    }
    public int getMonthSalary() {
        return monthSalary;
    }

    @Override
    public int incomeForCompany() {
        return 0;
    }

    public String getTypeEmployee() {
        return typeEmployee;
    }


}
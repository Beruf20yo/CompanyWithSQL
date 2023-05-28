package employee;


public class Manager implements Employee {
    protected  int ratePerMounth = 60000;
    protected int monthSalary;
    protected int incomeForCompany;
    protected String typeEmployee = "Manager";
    protected int idCompany;

    public Manager( int idCompany) {
        this.idCompany = idCompany;
        this.incomeForCompany = setCompanyIncome();
    }
    public int getRatePerMounth() {
        return ratePerMounth;
    }

    @Override
    public int getMonthSalary() {
        return monthSalary;
    }

    public int setCompanyIncome() {
        int startRange = 115000;
        int endRange = 140000;
        int incomePart = startRange + (int) (Math.random() * endRange);
        this.monthSalary = (incomePart * 5 / 100) + ratePerMounth;
        return incomePart;
    }
    public int incomeForCompany() {
        return incomeForCompany;
    }

    public String getTypeEmployee() {
        return typeEmployee;
    }

}
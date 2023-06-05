package employee;


public class Manager extends Employee {
    protected  int ratePerMounth = 60000;
    public Manager( int idCompany) {
        typeEmployee = "Manager";
        this.idCompany = idCompany;
        this.incomeForCompany = setCompanyIncome();
    }
    public int setCompanyIncome() {
        int startRange = 115000;
        int endRange = 140000;
        int incomePart = startRange + (int) (Math.random() * endRange);
        this.monthSalary = (incomePart * 5 / 100) + ratePerMounth;
        return incomePart;
    }
}
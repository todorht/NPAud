package aud3;

public abstract class Account {
    private String accountOwner;
    private int number;
    private double currentAmount;

    public Account(String accountOwner, int number, double currentAmount) {
        this.accountOwner = accountOwner;
        this.number = number;
        this.currentAmount = currentAmount;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

}

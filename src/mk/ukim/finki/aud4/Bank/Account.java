package mk.ukim.finki.aud4.Bank;

import java.util.Objects;

public abstract class Account {
    private String accountOwner;
    private int number;
    private double currentAmount;

    public Account(String accountOwner, int number, double currentAmount) {
        this.accountOwner = accountOwner;
        this.number = number;
        this.currentAmount = currentAmount;
    }

    public double getCurrentAmount(){
        return currentAmount;
    }

    public void addAmount(double amount){
        this.currentAmount+=amount;
    }

    public void withdraw(double amount){
        this.currentAmount-=amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return number == account.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

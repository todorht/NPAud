package mk.ukim.finki.aud4.Bank;

import java.util.Arrays;

public class Bank {
    private Account[] accounts;
    private int totalAccounts;
    private int maxAccount;

    public Bank( int maxAccount) {
        this.maxAccount = maxAccount;
        accounts = new Account[maxAccount];
        totalAccounts = 0;
    }

    public void addAccount(Account account){
        for(Account account1: accounts){
            if(account1.equals(account)) return;
        }
        if(totalAccounts == maxAccount){
            accounts = Arrays.copyOf(accounts, maxAccount*2);
            this.maxAccount = maxAccount *2;
        }
        accounts[totalAccounts++] = account;

    }

    public double totalAssets(){
        double sum = 0;
        for(Account account: accounts){ sum+=account.getCurrentAmount(); }
        return sum;
    }

    public void addInterestToAll(){
        for(Account account: accounts){
            if(account instanceof InterestBearingAccount){
                InterestBearingAccount interestBearingAccount = (InterestBearingAccount) account;
                interestBearingAccount.addInterest();
            }
        }
    }
}

package mk.ukim.finki.aud4.Bank;

import mk.ukim.finki.aud4.Bank.InterestCheckingAccount;

public class PlatinumCheckingAccount extends InterestCheckingAccount {

    public PlatinumCheckingAccount(String accountOwner, int number, double currentAmount) {
        super(accountOwner, number, currentAmount);
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount()*(INTEREST_RATE*2));
    }
}

package mk.ukim.finki.labs.codeVoved;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Random;

class Account {
    private String username;
    private  long id;
    private String balance;

    public Account(String username, String balance) {
        this.username = username;
        this.balance = balance;
        Random random = new Random();
        this.id= random.nextLong();
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance( String balance){
        this.balance = balance;
    }

    @Override
    public String toString() {

        return "Name: " + username + '\n'+"Balance: " + balance;
    }
}

abstract class Transaction {
    private long fromId;
    private long toId;
    private String description;
    private String amount;

    public Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }

    public long getFromId() { return fromId; }

    public long getToId() { return toId; }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public double provisionCal(){
        if(this.description.equals("FlatAmount")){
            FlatAmountProvisionTransaction transaction = (FlatAmountProvisionTransaction) this;
            return Double.parseDouble(transaction.getFlatProvision().substring(0,transaction.getFlatProvision().length()-1));
        }else{
            FlatPercentProvisionTransaction transaction = (FlatPercentProvisionTransaction) this;
            return ((int)Double.parseDouble(transaction.getAmount().substring(0,transaction.getAmount().length()-1))*transaction.getCentsPerDolar())/100.00;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId &&
                toId == that.toId &&
                Objects.equals(description, that.description) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction {


    private String flatProvision;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision) {
        super(fromId,toId,"FlatAmount",amount);
        this.flatProvision=flatProvision;
    }

    public String getFlatProvision() {
        return flatProvision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatAmountProvisionTransaction)) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatProvision, that.flatProvision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatProvision);
    }
}

class FlatPercentProvisionTransaction extends Transaction {

    private int centsPerDolar;

    FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDolar) {
        super(fromId,toId,"FlatPercent", amount);
        this.centsPerDolar=centsPerDolar;
    }

    public int getCentsPerDolar() {
        return centsPerDolar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatPercentProvisionTransaction)) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return centsPerDolar == that.centsPerDolar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centsPerDolar);
    }
}

class  Bank {
    private final Account[] accounts;
    private String name;
    private double totalTransaction;
    private double totalProvision;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = accounts.clone(); //deepcopy, vo testiranjeto na equals - koga pravi promena na tretito element, promenata se pravi i vo klasata bank(bez .clone())
    }

    public Account[] getAccounts() {
        return this.accounts;
    }

    public double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(double totalTransaction) {
        this.totalTransaction += totalTransaction;
    }

    public double getTotalProvision() {
        return totalProvision;
    }

    public void setTotalProvision(double totalProvision) {
        this.totalProvision += totalProvision;
    }

    private double StrToDouble(String string){
        return Double.parseDouble(string.substring(0,string.length()-1));
    }
    static String doubleToString(double d){
        return String.format("%.2f$", d);
    }

    public boolean makeTransaction(Transaction t){
        Account from = null;
        Account to = null;
        for(Account a: accounts){
            if(t.getToId()==a.getId())  to = a;
            if(t.getFromId()==a.getId()) from = a;
        }
        if(from==null && to==null) return false;
            if (!(StrToDouble(t.getAmount()) > StrToDouble(from.getBalance()))) {
                from.setBalance(doubleToString(StrToDouble(from.getBalance()) - (StrToDouble(t.getAmount()) + t.provisionCal())));
            } else return false;
            to.setBalance(doubleToString(StrToDouble(to.getBalance()) + StrToDouble(t.getAmount()) ));
            setTotalTransaction(StrToDouble(t.getAmount()));
            setTotalProvision(t.provisionCal());
            return true;
    }

    public String totalTransfers(){
        return doubleToString(getTotalTransaction());
    }

    public String totalProvision(){
        return doubleToString(getTotalProvision());
    }

    @Override
    public String toString() {
        StringBuilder accountsString = new StringBuilder();
        for(Account a : accounts) accountsString.append(a.toString()).append("\n");
        return "Name: " + name + "\n\n" + accountsString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(totalTransaction, bank.totalTransaction) &&
                Objects.equals(totalProvision, bank.totalProvision) &&
                Arrays.equals(accounts, bank.accounts) &&
                Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, totalTransaction, totalProvision);
        result = 31 * result + Arrays.hashCode(accounts);
        return result;
    }
}

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1)&&!a1.equals(a2)&&!a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.1");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.2");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.3");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.4");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + t.getAmount());
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + bank.totalProvision());
                    System.out.println("Total transfers: " + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
        }
        return null;
    }


}

package aud1;

public class CombinationLock {

    private int combination;
    private boolean isOpen;

    public CombinationLock(int combination) {
        this.combination = combination;
    }

    public boolean open(int combo){
        this.isOpen = (this.combination == combo);
        return isOpen;
    }

    public boolean changeCombo(int oldCombo, int newCombo){
        if (oldCombo != this.combination) return false;
        this.combination = newCombo;
        return true;
    }

    public static void main(String[] args) {
        CombinationLock lock = new CombinationLock(111);
        System.out.println(lock.open(000));
        System.out.println(lock.open(111));
        System.out.println(lock.changeCombo(000, 111));
        System.out.println(lock.changeCombo(111,000));
        System.out.println(lock.open(111));
        System.out.println(lock.open(000));
    }
}

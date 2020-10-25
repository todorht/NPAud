package aud2;

import java.util.Arrays;

public class MultipleDeck {

    private Deck[] decks;

    public MultipleDeck (int num){
        this.decks = new Deck[num];
        for (int i=0; i<num; i++)
            decks[i] = new Deck();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Deck deck: decks){
            sb.append(deck);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MultipleDeck multipleDeck = new MultipleDeck(3);
        System.out.println(multipleDeck);
    }
}

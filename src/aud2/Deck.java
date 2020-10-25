package aud2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {

    private PlayingCard[] cards;
    private boolean[] picked;
    private int pickedTotal;


    public Deck () {
        cards = new PlayingCard[52];
        picked = new boolean[52];
        pickedTotal = 0;

        for (int i=0; i<PlayingCard.TYPE.values().length; i++){
            for (int j=0; j<13; j++){
                cards[j + 13 * i] = new PlayingCard(PlayingCard.TYPE.values()[i], j+1);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;
        Deck other = (Deck) o;
        return Arrays.equals(this.cards, other.cards);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + Arrays.hashCode(cards);
        return result;
    }

    public PlayingCard[] shuffle(){
        List<PlayingCard> playingCards = Arrays.asList(cards);
        Collections.shuffle(playingCards);
        return cards;
    }

    public PlayingCard dealCard(){
        if(pickedTotal == 52) return null;

        int card = (int) (52 * Math.random());
        if(!picked[card]){
            pickedTotal++;
            picked[card] = true;
            return cards[card];
        }
        return dealCard();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(PlayingCard card: cards) {
            sb.append(card);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);

        deck.shuffle();
        System.out.println(deck);

        PlayingCard card;
        while ((card = deck.dealCard()) != null){
            System.out.println(card);
        }
    }
}

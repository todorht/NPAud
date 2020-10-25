package mk.ukim.finki.aud2;

import java.util.*;

class MultipleDeck{

    private Deck[] decks;

    public MultipleDeck(int numOfDecks){
        this.decks = new Deck[numOfDecks];
        for (int i = 0;i<numOfDecks;i++){
            decks[i] = new Deck();
        }
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       for(Deck deck : decks){
            sb.append(deck);
            sb.append('\n');
       }
        return sb.toString();
    }
}

class Deck{

    private PlayingCard[] cards;
    private boolean[] picked;
    private int pickedTotal;

    public Deck(){
        picked = new boolean[52];
        cards = new PlayingCard[52];
        pickedTotal = 0;

        for(int i =0; i<PlayingCard.TYPE.values().length;i++){
            for(int j=0; j<13;j++){
                cards[j+13*i]=new PlayingCard(PlayingCard.TYPE.values()[i], j+1);
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
        if(this == o) return true;  //dali e istiot objekt
        if(o == null)return false;
        if(this.getClass()!=o.getClass())return false;
        Deck other = (Deck) o;
        return Arrays.equals(cards,other.cards);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(PlayingCard c : cards){
            sb.append(c);
            sb.append("\n");
        }
        return sb.toString();
    }

    public PlayingCard[] shuffle(){
        List<PlayingCard> playingCards = Arrays.asList(cards);
        Collections.shuffle(playingCards);
        return cards;
    }

    public PlayingCard dealCard(){
        if(pickedTotal==52) return null;
        int card = (int)(52*Math.random());
        if(!picked[card]){
            picked[card] = true;
            pickedTotal++;
            return cards[card];
        }else return dealCard();
    }

    public boolean hasCard(){
        return cards.length>0;
    }

}

public class PlayingCard {

    public enum TYPE{
        HEARTS,
        DIAMONDS,
        SPADES,
        CLUBS
    }

    private TYPE cardType;
    private int rank;

    public PlayingCard(TYPE cardType, int rank) {
        this.cardType = cardType;
        this.rank = rank;
    }

    public TYPE getCardType() { return cardType; }

    public void setCardType(TYPE cardType) { this.cardType = cardType; }

    public int getRank() { return rank; }

    public void setRank(int rank) { this.rank = rank; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayingCard)) return false;
        PlayingCard that = (PlayingCard) o;
        return rank == that.rank &&
                cardType == that.cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, rank);
    }

    @Override
    public String toString() {
        return String.format("%d %s", rank,cardType.toString());
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);

        deck.shuffle();
        System.out.println(deck);
        System.out.println();


        PlayingCard card;
        while ((card = deck.dealCard())!=null){
            System.out.println(card);
        }

        MultipleDeck multipleDeck = new MultipleDeck(3);
        System.out.println(multipleDeck);
    }
}

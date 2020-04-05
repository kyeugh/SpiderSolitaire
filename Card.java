public class Card {
    enum Suit {
        SPADES,
        CLUBS,
        DIAMONDS,
        HEARTS
    }

    private int rank;
    private Suit suit;

    public Card(Suit s, int r) {
        suit = s;
        rank = r;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getImage() {
        StringBuilder imgPath = new StringBuilder("assets/");
        imgPath.append(getRank());
        imgPath.append(getSuit().name().charAt(0));
        imgPath.append(".png");
        return imgPath.toString();
    } 
}
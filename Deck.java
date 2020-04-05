import java.util.Stack;
import java.util.Collections;

public class Deck {
    private Stack<Card> deck = new Stack<Card>();
    private int numSuits;

    public Deck() {
        this(1);
    }

    public Deck(int n) {
        if (n != 1 && n != 2 && n != 4)
            n = 1;
        else {
            numSuits = n;
            populateDeck();
            shuffle();
        }
    }

    public Card top() {
        Card topCard;
        try { // peek() throws exception when empty
            topCard = deck.peek();
        }
        catch (Exception e) {
            topCard = null;
        }
        return topCard;
    }

    public Card drawCard() {
        if (deck.empty()) {
            System.out.println("ERROR: no cards left");
            return null;
        }
        else
            return deck.pop();
    }

    private void populateDeck() {
        for (int suit = 0; suit < numSuits; suit++) {
            for (int card = 0; card < 104 / numSuits; card++) {
                Card.Suit s = Card.Suit.values()[suit]; // 0 = Spades, 1 = Clubs, etc.
                int r = (card % 13) + 1;  // produces 1-13
                deck.push(new Card(s, r));
            }
        }
    }

    private void shuffle() {
        Collections.shuffle(deck);
    }
} // end of Deck class
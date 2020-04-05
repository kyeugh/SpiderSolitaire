import java.util.Stack;

public class Deck {
    private Stack<Card> deck = new Stack<Card>();
    private int cardsLeft;

    public Deck(int numSuites)
    {
        populateDeck(numSuites);
        cardsLeft = 104;
        shuffle();
    }

    public Card top()
    {
        return deck.peek();
    }

    public Card drawCard()
    {
        if (cardsLeft == 0)
        {
            System.out.print("ERROR: no cards left\n");
            return null;
        }
        else
        {
            cardsLeft--;
            return deck.pop();
        }
    }

    private void populateDeck(int numSuites)
    {
        if(numSuites == 1)
        {
            for(int i = 1; i < 14; i++)
            {
                for (int x = 0; x < 8; x++)
                {
                    deck.push(new Card(Card.Suit.SPADES, i));
                }
            }
        }
        else if(numSuites == 2)
        {
            for(int i = 0; i < 2; i++)
            {
                for (int x = 1; x < 14; x++)
                {
                    for (int y = 0; y < 4; y++)
                    {
                        if(i == 0)
                        {
                            deck.push(new Card(Card.Suit.SPADES, x));
                        }
                        else
                        {
                            deck.push(new Card(Card.Suit.HEARTS, x));
                        }
                    }
                }
            }
        }
        else
        {
            for(int i = 0; i < 4; i++)
            {
                for(int x = 1; x < 14; x++)
                {
                    switch(i)
                    {
                        case 0: deck.push(new Card(Card.Suit.SPADES, x));
                                deck.push(new Card(Card.Suit.SPADES, x));
                                break;
                        case 1: deck.push(new Card(Card.Suit.HEARTS, x));
                                deck.push(new Card(Card.Suit.HEARTS, x));
                                break;
                        case 2: deck.push(new Card(Card.Suit.CLUBS, x));
                                deck.push(new Card(Card.Suit.CLUBS, x));
                                break;
                        case 3: deck.push(new Card(Card.Suit.DIAMONDS, x));
                                deck.push(new Card(Card.Suit.DIAMONDS, x));
                                break;
                    }
                }
            }
        } // end of if-else
    }

    private void shuffle()
    {
        // shuffle deck evenly
    }
} // end of Deck class
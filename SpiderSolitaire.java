import javax.swing.*;
import java.awt.*;
import java.util.Vector;

class SpiderSolitaire {
    Deck deck;
    JFrame frame;
    boolean cardsSelected;
    Vector<Card> cards;

    public SpiderSolitaire() {
        deck = new Deck(4, this);
        cardsSelected = false; // for identifying that a group of cards has been clicked
        cards = null; // for holding a batch of selected cards
        frame = new JFrame("Spider Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = .8;
        gbc.weighty = 1;
        /* game code goes here */
        Pile piles[] = new Pile[10];
        for (int i = 0; i < 10; i++) {
            if(i < 4)
                piles[i] = new Pile(deck, 6);
            else
                piles[i] = new Pile(deck, 5);
            frame.add(piles[i], gbc);
        }

        // test code: should be removed
        /*
        Card testCard = new Card(Card.Suit.Spades, 1, this);
        testCard.setChild(new Card(Card.Suit.Hearts, 12, this));
        piles[1].addCard(testCard);
        piles[1].take(testCard);
        */

        /* end game code */
        /* menu bar */
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('g');
        JMenuItem restartMenu = new JMenuItem("Restart");
        restartMenu.setMnemonic('r');
        JMenuItem pauseMenu = new JMenuItem("Pause");
        pauseMenu.setMnemonic('p');
        gameMenu.add(restartMenu);
        gameMenu.add(pauseMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        JMenuItem howtoMenu = new JMenuItem("How to play");
        helpMenu.setMnemonic('t');
        helpMenu.add(howtoMenu);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(1250, 900);
        frame.setVisible(true);
    }

    boolean hasSelectedCards()
    {
        return cardsSelected;
    }

    void selectCards(Vector<Card> selectCards)
    {
        cardsSelected = true;
        cards = new Vector<Card>();
        for(int i = 0; i < selectCards.size(); i++)
        {
            cards.addElement(selectCards.get(i));
        }
    }

    Vector<Card> getCards()
    {
        return cards;
    }

    void deselectCards()
    {
        cardsSelected = false;
        cards = null;
    }

    public static void main(String[] args) {
        new SpiderSolitaire();
    }
}

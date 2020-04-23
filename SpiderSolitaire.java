import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class SpiderSolitaire {
    Deck deck;
    Pile piles[];
    JFrame frame;
    boolean cardsSelected = false;
    Vector<Card> cards = null;
    int numSuits;

    public SpiderSolitaire(int n) {
        numSuits = n;
        frame = new JFrame("Spider Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().setBackground(new Color(25, 160, 15));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = .8;
        gbc.weighty = 1;
        /* game code goes here */
        deck = new Deck(numSuits, this);
        piles = new Pile[10];
        for (int i = 0; i < 10; i++) {
            if(i < 4)
                piles[i] = new Pile(deck, 6, this);
            else
                piles[i] = new Pile(deck, 5, this);
            frame.add(piles[i], gbc);
        }
        /* menu bar */
        JMenuBar menuBar = new JMenuBar();

        JMenuItem dealMenu = new JMenuItem("Deal!") {
            public Dimension getMaximumSize() {
                Dimension d1 = super.getPreferredSize();
                Dimension d2 = super.getMaximumSize();
                d2.width = d1.width;
                return d2;
            }
        };
        dealMenu.setMnemonic('d');
        dealMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean allSpacesFilled = true;
                for (Pile p : piles)
                    if (p.empty())
                        allSpacesFilled = false;
                if (allSpacesFilled) {
                    Card c;
                    for (Pile pile : piles) {
                        c = deck.drawCard();
                        if (c != null) {
                            c.flip();
                            pile.addCard(c);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "The deck is empty!");
                            break;
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "You may only deal when all empty spaces are occupied.");
                }
            }
        });

        JMenu restartMenu = new JMenu("Restart");
        restartMenu.setMnemonic('r');
        JMenuItem newGame1 = new JMenuItem("1 suit");
        newGame1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SpiderSolitaire(1);
            }
        });
        JMenuItem newGame2 = new JMenuItem("2 suits");
        newGame2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SpiderSolitaire(2);
            }
        });
        JMenuItem newGame4 = new JMenuItem("4 suits");
        newGame4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SpiderSolitaire(4);
            }
        });
        restartMenu.add(newGame1);
        restartMenu.add(newGame2);
        restartMenu.add(newGame4);

        menuBar.add(dealMenu);
        menuBar.add(restartMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(1250, 900);
        frame.setVisible(true);
    }

    public boolean hasSelectedCards()
    {
        return cardsSelected;
    }

    public void selectCards(Vector<Card> selectCards)
    {
        /* adds the passed in vector to the hand */
        cardsSelected = true;
        cards = new Vector<Card>();
        for(int i = 0; i < selectCards.size(); i++)
        {
            cards.addElement(selectCards.get(i));
        }
    }

    public Vector<Card> getCards()
    {
        /* returns the hand */
        return cards;
    }

    public void deselectCards()
    {
        cardsSelected = false;
        cards = null;
    }

    public void checkWin() {
        /* checks to see if the game is won, and handles it if so */
        if (deck.isEmpty()) {
            for (Pile pile : piles)
                if (!pile.empty())
                    return;
            int playAgain = JOptionPane.showConfirmDialog(null, "Congratulations, you won!\nPlay again?", "You won!", JOptionPane.YES_NO_OPTION);
            if (playAgain == JOptionPane.YES_OPTION) {
                frame.dispose();
                new SpiderSolitaire(numSuits);
            }
            else
                System.exit(0);
        }
    }

    public static void main(String[] args) {
        String[] options = new String[] {"1", "2", "4"};
        int numDecks = JOptionPane.showOptionDialog(null, "How many suits would you like to play with?", "SpiderSolitaire Setup",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
        numDecks++;
        if (numDecks == 3)
            numDecks++;
        new SpiderSolitaire(numDecks);
    }
}

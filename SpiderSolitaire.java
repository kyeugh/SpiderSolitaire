import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class SpiderSolitaire {
    Deck deck;
    JFrame frame;
    boolean cardsSelected;
    Vector<Card> cards;

    public SpiderSolitaire(int numDecks) {
        cardsSelected = false; // for identifying that a group of cards has been clicked
        cards = null; // for holding a batch of selected cards
        frame = new JFrame("Spider Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(25, 160, 15));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = .8;
        gbc.weighty = 1;
        /* game code goes here */
        deck = new Deck(numDecks, this);
        Pile piles[] = new Pile[10];
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
        });

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('g');
        JMenu restartMenu = new JMenu("Restart");
        restartMenu.setMnemonic('r');
        restartMenu.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {
                restartMenu.doClick();
            }
        });
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
        JMenuItem pauseMenu = new JMenuItem("Pause");
        pauseMenu.setMnemonic('p');
        gameMenu.add(restartMenu);
        gameMenu.add(pauseMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        JMenuItem howtoMenu = new JMenuItem("How to play");
        helpMenu.setMnemonic('t');
        helpMenu.add(howtoMenu);

        menuBar.add(dealMenu);
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

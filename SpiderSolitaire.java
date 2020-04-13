import javax.swing.*;
import java.awt.*;

class SpiderSolitaire {
    Deck deck;
    JFrame frame;

    public SpiderSolitaire() {
        deck = new Deck(4);
        frame = new JFrame("Spider Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        /* game code goes here */
        Pile piles[] = new Pile[5];
        piles[0] = new Pile(deck, 6);
        piles[1] = new Pile(deck, 6);
        piles[2] = new Pile(deck, 6);
        piles[3] = new Pile(deck, 5);
        piles[4] = new Pile(deck, 5);
        for (Pile pile : piles)
            frame.add(pile, gbc);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        /* end game code */
        frame.setSize(1100, 800);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new SpiderSolitaire();
    }
}
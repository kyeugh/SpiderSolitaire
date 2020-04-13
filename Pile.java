import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pile extends JPanel {
    private Vector<Card> cards;

    public Pile(Card c) {
        cards = new Vector<Card>();
        while (c != null) {
            cards.add(c);
            c = c.getChild();
        }
        setPreferredSize(new Dimension(115, (50 * cards.size()) + 125));
    }

    public Pile(Deck d, int num) {
        cards = new Vector<Card>();
        Card c;
        for (int i = 0; i < num; i++) {
            c = d.drawCard();
            if (i > 0)
                cards.get(i - 1).setChild(c);
            if (i == num - 1)
                c.show();
            cards.add(c);
        }
        setPreferredSize(new Dimension(115, (50 * cards.size()) + 125));
    }

    protected void paintComponent(Graphics g) {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).paintComponent(g);
        }
    }

}
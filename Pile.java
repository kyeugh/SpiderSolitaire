import java.util.Vector;
import javax.swing.*;
import java.awt.*;

public class Pile extends JPanel {
    private Vector<Card> cards;
    private JLayeredPane layeredPane;
    private static int offset = 50;

    public Pile(Card c) {
        cards = new Vector<Card>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        addCard(c);
        recalcSize();
        add(layeredPane);
    } // end of pile constructor

    public Pile(Deck d, int num) {
        cards = new Vector<Card>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        int offset = 50;
        for (int depth = 0; depth < num; depth++) {
            Card c = d.drawCard();
            if (depth > 0)
                cards.get(depth - 1).setChild(c);
            if (depth == num - 1)
                c.flip();
            c.setBounds(0, offset * depth, 115, 145);
            cards.add(c);
            c.setPile(this);
            layeredPane.add(c, Integer.valueOf(depth));
        }
        recalcSize();
        add(layeredPane);
    } // end of pile constructor

    Card top() {
        return cards.lastElement();
    } // end of top

    void addCard(Card c) {
        while (c != null) {
            c.setPile(this);
            if (cards.size() > 0)
                top().setChild(c);
            c.setBounds(0, offset * cards.size(), 115, 145);
            cards.add(c);
            layeredPane.add(c, Integer.valueOf(cards.size()));
            c = c.getChild();
        }
        recalcSize();
        repaint();
    } // end of addCard

    Pile take(Card c) {
        boolean found = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == c) {
                if (i > 0)
                    cards.get(i - 1).setChild(null);
                found = true;
            }
            if (found)
                cards.remove(i);
        }
        if (found)
            return new Pile(c);
        else
            return null;
    } // end of take

    public void recalcSize() {
        layeredPane.setPreferredSize(new Dimension(115, (offset * cards.size()) + (145 - offset)));
        revalidate();
    }

    void select() {
        for (Card card : cards)
            card.select();
    } // end of select

} // end of pile class

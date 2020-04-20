import java.util.Vector;
import javax.swing.*;
import java.awt.*;

public class Pile extends JPanel {
    private Vector<Card> cards;
    private JLayeredPane layeredPane;
    private static int offset = 35;

    public Pile(Card c) {
        cards = new Vector<Card>();
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        addCard(c);
        recalcSize();
        add(layeredPane);
    } // end of pile constructor

    public Pile(Deck d, int num) {
        cards = new Vector<Card>();
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
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

    public Card top() {
        return cards.firstElement();
    }

    public Card bottom() {
        return cards.lastElement();
    } // end of top

    void addCard(Card c) {
        while (c != null) {
            c.setPile(this);
            if (cards.size() > 0)
                bottom().setChild(c);
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
        Card newBottom = null;
        int cIndex = -1;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == c) {
                if (i > 0) {
                    newBottom = cards.get(i - 1);
                    newBottom.setChild(null);
                    if (!newBottom.faceUp())
                        newBottom.flip();
                    cIndex = i;
                }
                found = true;
            }
        }
        if (cIndex >= 0) // removing inside loop causes off-by-one errors
            cards.subList(cIndex, cards.size()).clear(); // removeRange() is protected; this is the best approximation
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

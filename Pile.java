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
        layeredPane.setPreferredSize(new Dimension(135, offset * cards.size() + (175 - offset)));
        add(layeredPane);
    }

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
            c.setBounds(0, offset * depth, 135, 175);
            cards.add(c);
            layeredPane.add(c, Integer.valueOf(depth));
        }
        layeredPane.setPreferredSize(new Dimension(135, offset * num + (175 - offset)));
        add(layeredPane);
    }

    Card top() {
        return cards.lastElement();
    }

    void addCard(Card c) {
        while (c != null) {
            if (cards.size() > 0)
                top().setChild(c);
            cards.add(c);
            c.setBounds(0, offset * cards.size(), 135, 175);
            layeredPane.add(c, Integer.valueOf(cards.size()));
            c = c.getChild();
        }
        layeredPane.setPreferredSize(new Dimension(135, offset * cards.size() + (175 - offset)));
        repaint();
    }

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
    }
    
    void select() {
        for (Card card : cards)
            card.select();
    }

}
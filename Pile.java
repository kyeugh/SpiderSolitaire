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
    
    public boolean empty() {
        return cards.size() == 0;
    }

    public Card top() {
        return cards.firstElement();
    }

    public Card bottom() {
        return cards.lastElement();
    } // end of top

    public void resolve() {
        Card c = null;
        for (Card card : cards) {
            if (card.faceUp() && card.getRank() == 13) {
                c = card;
                break;
            }
        }
        if (c != null && c.isLegalStack()) {
            Card lastCard = c;
            while (lastCard.getChild() != null)
                lastCard = lastCard.getChild();
            if (lastCard.getRank() == 1) {
                take(c);
            }
        }
    }

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
        resolve();
        recalcSize();
    } // end of addCard

    void take(Card c) {
        Card newBottom = null;
        int cIndex = cards.indexOf(c);
        int num = cards.size() - cIndex;
        if (cIndex >= 0) {
            if (cIndex > 0) {
                newBottom = cards.get(cIndex - 1);
                newBottom.setChild(null);
                if (!newBottom.faceUp())
                    newBottom.flip();
            }
            for (int i = 0; i < num; i++) {
                layeredPane.remove(layeredPane.lowestLayer());
            }
            cards.subList(cIndex, cards.size()).clear();
        }
        recalcSize();
    } // end of take

    public void recalcSize() {
        layeredPane.setPreferredSize(new Dimension(115, (offset * cards.size()) + (145 - offset)));
        revalidate();
        repaint();
    }

    void select() {
        for (Card card : cards)
            card.select();
    } // end of select

} // end of pile class

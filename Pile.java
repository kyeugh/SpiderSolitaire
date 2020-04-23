import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Pile extends JPanel {
    private Vector<Card> cards;
    private JLayeredPane layeredPane;
    private static int offset = 35;
    private SpiderSolitaire spiderSolitaire;

    public Pile(Card c, SpiderSolitaire solitaire) {
        cards = new Vector<Card>();
        spiderSolitaire = solitaire;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        addCard(c); // adds all children as well
        recalcSize();
        add(layeredPane);
    }

    public Pile(Deck d, int num, SpiderSolitaire solitaire) {
        cards = new Vector<Card>();
        spiderSolitaire = solitaire;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        for (int depth = 0; depth < num; depth++) {
            Card c = d.drawCard();
            if (depth > 0) {
                cards.get(depth - 1).setChild(c);
            }
            if (depth == num - 1)
                c.flip();
            c.setBounds(0, offset * depth, 115, 145);
            cards.add(c);
            c.setPile(this);
            layeredPane.add(c, Integer.valueOf(depth));
        }

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // we're only clicking the pile when there are no cards remaining
                if (empty() && spiderSolitaire.hasSelectedCards()) {
                    Card cardToAdd = spiderSolitaire.getCards().get(0);
                    cardToAdd.take();
                    addCard(cardToAdd); // don't need to verify; any card can be added to an empty space
                    while (cardToAdd != null) {
                        cardToAdd.deselect();
                        cardToAdd = cardToAdd.getChild();
                    }
                    spiderSolitaire.deselectCards();
                }
            }
        });

        recalcSize();
        add(layeredPane);
    }

    public boolean empty() {
        return cards.isEmpty();
    }

    public Card top() {
        return cards.firstElement();
    }

    public Card bottom() {
        return cards.lastElement();
    }

    public void resolve() {
        /* 
            checks the pile for complete stacks of king through ace,
            all with matching suits, and removes the cards from
            play if found.
        */
        Card c = null;
        for (Card card : cards) {
            if (card.getRank() == 13 && card.faceUp()) {
                c = card;   // check from the lowest king down
            }
        }
        if (c != null && c.isLegalStack()) { // ensures ranks are in decreasing order and suits match
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
            layeredPane.add(c, Integer.valueOf(cards.size() + 1));
            c = c.getChild();
        }
        resolve();
        recalcSize();
    } // end of addCard

    void take(Card c) {
        Card newBottom = null;
        int cIndex = cards.indexOf(c);
        int num = cards.size() - cIndex;    // number of cards in stack
        if (cIndex >= 0) {
            if (cIndex > 0) {
                newBottom = cards.get(cIndex - 1);
                newBottom.setChild(null);
                if (!newBottom.faceUp())
                    newBottom.flip();
            }
            if (num == cards.size())
                layeredPane.removeAll();
            else
                while (c != null) {
                    layeredPane.remove(c);  // removing by layer index doesn't work
                    c = c.getChild();
                }
            cards.subList(cIndex, cards.size()).clear();
        }
        recalcSize();
    } // end of take

    public void recalcSize() {
        layeredPane.setPreferredSize(new Dimension(115, (offset * (cards.size() + 1)) + (145 - offset)));
        revalidate();
        repaint();
    }

    void select() {
        for (Card card : cards)
            card.select();
    } // end of select

} // end of pile class

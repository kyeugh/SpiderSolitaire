import java.util.Vector;
import javax.swing.*;
import java.awt.*;

public class Pile extends JPanel {
    private Vector<Card> cards;
    private JLayeredPane layeredPane;

    public Pile(Card c) {
        cards = new Vector<Card>();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        int offset = 50;
        int depth = 0;
        while (c != null) {
            cards.add(c);
            c.setBounds(0, offset * depth, 115, 175);
            layeredPane.add(c, Integer.valueOf(depth++));
            c = c.getChild();
        }
        layeredPane.setPreferredSize(new Dimension(115, offset * depth + (175 - offset)));
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
            c.setBounds(0, offset * depth, 115, 175);
            cards.add(c);
            layeredPane.add(c, Integer.valueOf(depth));
        }
        layeredPane.setPreferredSize(new Dimension(115, offset * num + (175 - offset)));
        add(layeredPane);
    }

}
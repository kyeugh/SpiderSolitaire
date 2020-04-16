import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class Card extends JPanel {
    enum Suit {
        Spades,
        Clubs,
        Diamonds,
        Hearts
    }

    private int rank;
    private Suit suit;
    private boolean isFaceUp,
                    isSelected;
    private Image frontImage,
                  backImage;
    Card child;

    public Card(Suit s, int r) {
        suit = s;
        rank = r;
        isFaceUp = false;
        isSelected = false;
        child = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Card c = Card.this;
                boolean alreadySelected = c.selected();
                while (c != null) {
                    if (alreadySelected)
                        c.deselect();
                    else
                        c.select();
                    c = c.getChild();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                repaint();
           }
        });

        try {
            Image cardImage = ImageIO.read(new File(getImagePath()));
            frontImage = cardImage.getScaledInstance(115, 175, Image.SCALE_SMOOTH);
            cardImage = ImageIO.read(new File("assets/green_back.png"));
            backImage = cardImage.getScaledInstance(115, 175, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new Dimension(135, 175));
    }

    public void flip() {
        isFaceUp = !isFaceUp;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setChild(Card c) {
        child = c;
    }

    public Card getChild() {
        return child;
    }

    public boolean hasChild() {
        return child != null;
    }

    public void select() {
        isSelected = true;
        repaint();
    }

    public void deselect() {
        isSelected = false;
        repaint();
    }

    public boolean selected() {
        return isSelected;
    }

    private String getImagePath() {
        StringBuilder imgPath = new StringBuilder("assets/");
        imgPath.append(getRank());
        imgPath.append(getSuit().name().charAt(0));
        imgPath.append(".png");
        return imgPath.toString();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        switch (getRank()) {
            case 1:
                result.append("Ace");
                break;
            case 11:
                result.append("Jack");
                break;
            case 12:
                result.append("Queen");
                break;
            case 13:
                result.append("King");
                break;
            default:
                result.append(getRank());
                break;
        }
        result.append(" of ");
        result.append(getSuit());
        if (isFaceUp)
            result.append(" (face-up)");
        return result.toString();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = isSelected ? 20 : 0;
        g.drawImage(isFaceUp ? frontImage : backImage, x, 0, this);
    }
}

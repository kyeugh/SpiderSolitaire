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
    private int x, y;
    private Suit suit;
    private boolean isFaceUp;
    private Image frontImage, backImage;
    Card child;

    public Card(Suit s, int r) {
        suit = s;
        rank = r;
        isFaceUp = false;
        child = null;
        x = 0;
        y = 0;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                flip();
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
        setPreferredSize(new Dimension(115, 175));
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

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setChild(Card c) {
        c.setX(this.x);
        c.setY(this.y + 30);
        child = c;
    }

    public Card getChild() {
        return child;
    }

    public boolean hasChild() {
        return child != null;
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
        g.drawImage(isFaceUp ? frontImage : backImage, 0, 0, this);
    }
}
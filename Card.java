import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

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
    Card parent;
    private SpiderSolitaire spiderSolitaire;
    private Pile pile = null;

    public Card(Suit s, int r, SpiderSolitaire solitaire) {
        suit = s;
        rank = r;
        isFaceUp = false;
        isSelected = false;
        child = null;
        spiderSolitaire = solitaire;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFaceUp)
                {
                    Card c = Card.this;
                    Vector<Card> cards = new Vector<Card>();
                    boolean alreadySelected = c.selected();
                    if(spiderSolitaire.hasSelectedCards())
                    {
                        if(spiderSolitaire.getCards().get(0) == c)
                        {
                            while(c != null)
                            {
                                c.deselect();
                                c = c.getChild();
                            }
                            spiderSolitaire.deselectCards();
                        }
                        else if(spiderSolitaire.getCards().get(0).getSuit() == c.getSuit() && spiderSolitaire.getCards().get(0).getRank() == (c.getRank() - 1)) // I know, a doozy, I'll fix it
                        {
                            if(c.hasChild() == false)
                            {
                                Card cardToAdd = spiderSolitaire.getCards().get(0);
                                Pile cPile = cardToAdd.getPile();
                                Card.this.pile.addCard(cPile.take(cardToAdd).top());
                                while (cardToAdd != null) {
                                    cardToAdd.deselect();
                                    cardToAdd = cardToAdd.getChild();
                                }
                                spiderSolitaire.deselectCards();
                            }
                        }
                        else
                        {
                            for(int i = 0; i < spiderSolitaire.getCards().size(); i++)
                            {
                                spiderSolitaire.getCards().get(i).deselect();
                            }
                            spiderSolitaire.deselectCards();
                        } // end of nested if-else
                    }
                    else{
                        while (c != null)
                        {
                            if (alreadySelected)
                            {
                                c.deselect();
                            }
                            else
                            {
                                c.select();
                                cards.addElement(c);
                            }

                            c = c.getChild();
                        } // end of while loop
                        spiderSolitaire.selectCards(cards);
                    } // end of hasSelectedCards if-else statement
                    pile.recalcSize();
                    pile.repaint();
                }
            }
        });

        try {
            Image cardImage = ImageIO.read(new File(getImagePath()));
            frontImage = cardImage.getScaledInstance(95, 145, Image.SCALE_SMOOTH);
            cardImage = ImageIO.read(new File("assets/green_back.png"));
            backImage = cardImage.getScaledInstance(95, 145, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setPreferredSize(new Dimension(115, 145));
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

    Pile getPile()
    {
        return pile;
    }

    void setPile(Pile newPile)
    {
        pile = newPile;
    }
}

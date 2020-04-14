import javax.swing.*;
import java.awt.*;

class SpiderSolitaire {
    Deck deck;
    JFrame frame;

    public SpiderSolitaire() {
        deck = new Deck(4);
        frame = new JFrame("Spider Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        /* game code goes here */
        for (int i = 0; i < 10; i++)
            frame.add(new Pile(deck, 5));
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        /* end game code */
        /* menu bar */
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('g');
        JMenuItem restartMenu = new JMenuItem("Restart");
        restartMenu.setMnemonic('r');
        JMenuItem pauseMenu = new JMenuItem("Pause");
        pauseMenu.setMnemonic('p');
        gameMenu.add(restartMenu);
        gameMenu.add(pauseMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        JMenuItem howtoMenu = new JMenuItem("How to play");
        helpMenu.setMnemonic('t');
        helpMenu.add(howtoMenu);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(1250, 900);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new SpiderSolitaire();
    }
}
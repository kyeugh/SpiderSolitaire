class SpiderSolitaire {
    public static void main(String[] args) {
        Deck testDeck = new Deck(4);
        while (testDeck.top() != null)
            System.out.println(testDeck.drawCard());
    }
}
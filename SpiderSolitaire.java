class SpiderSolitaire {
    public static void main(String[] args) {
        Card test = new Card(Card.Suit.SPADES, 1);
        System.out.println(test.getSuit());
        System.out.println(test.getRank());
        System.out.println(test.getImage());
    }
}
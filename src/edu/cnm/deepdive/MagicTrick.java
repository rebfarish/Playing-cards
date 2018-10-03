package edu.cnm.deepdive;

import edu.cnm.deepdive.Suit.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MagicTrick {


  public static void main(String[] args) {
    Random rng = new Random();
    Stack<Card> deck = new Stack<>();
    Stack<Card> redPile = new Stack<>();
    Stack<Card> blackPile = new Stack<>();
    for (Suit suit: Suit.values()){
      for (Rank rank: Rank.values())
        deck.push(new Card(rank, suit));
    }
    Collections.shuffle(deck);
    while (!deck.isEmpty()) {
      Card selector = deck.pop();
      Card card = deck.pop();
      if (selector.getSuit().getColor() == Color.RED) {
        redPile.push(card);
      }else {
        blackPile.push(card);
      }
    }
    //NB Shuffle is not strictly necessary
    assert 2* (redPile.size() + blackPile.size()) ==Suit.values().length * Rank.values().length;
    Collections.shuffle(redPile, rng);
    Collections.shuffle(blackPile, rng);
    int n= rng.nextInt(Math.min(redPile.size(), blackPile.size()) +1);
    Stack<Card> redExtract = new Stack<>();
    Stack<Card> blackExtract = new Stack<>();
    for (int i = 0; i< n; i++) {
      redExtract.push(redPile.pop());
      blackExtract.push(blackPile.pop());
    }
    redPile.addAll(blackExtract);
    blackPile.addAll(redExtract);
    long redCount = redPile.parallelStream()
        .filter((c) -> c.getSuit().getColor()== Color.RED)
        .count();

    long blackCount = blackPile.parallelStream()
        .filter((c) -> c.getSuit().getColor()== Color.BLACK)
        .count();
    assert redCount == blackCount;
    System.out.println(redPile);
    System.out.println(blackPile);
  }


}


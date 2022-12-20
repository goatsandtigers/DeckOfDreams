package com.goatsandtigers.deckofdreams.player;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.cards.starting.StartingNegativeCard;
import com.goatsandtigers.deckofdreams.cards.starting.StartingNeutralCard;
import com.goatsandtigers.deckofdreams.cards.starting.StartingPositiveCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private List<Card> deck;
    private String name;

    public Player(String name) {
        this.name = name;
        deck = buildStartingDeck();
    }

    private static List<Card> buildStartingDeck() {
        List<Card> startingCards = new ArrayList<>();
        startingCards.add(new StartingNegativeCard());
        startingCards.add(new StartingNegativeCard());
        startingCards.add(new StartingPositiveCard());
        startingCards.add(new StartingPositiveCard());
        startingCards.add(new StartingNeutralCard());
        startingCards.add(new StartingNeutralCard());
        Collections.shuffle(startingCards);
        return startingCards;
    }

    public String getName() {
        return name;
    }

    public Card drawCard() throws TurnEndsException {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        } else {
            throw new TurnEndsException("Out of cards.");
        }
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

}

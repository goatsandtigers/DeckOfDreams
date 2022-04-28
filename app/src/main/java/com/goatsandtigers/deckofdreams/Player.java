package com.goatsandtigers.deckofdreams;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.PlayerOutOfCardsException;
import com.goatsandtigers.deckofdreams.card.negative.NegativeCard;
import com.goatsandtigers.deckofdreams.card.starter.StartingCardNegative;
import com.goatsandtigers.deckofdreams.card.starter.StartingCardNeutral;
import com.goatsandtigers.deckofdreams.card.starter.StartingCardPositive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {

    private final String name;

    private List<Card> deck;
    private List<Card> dream;
    private List<Card> discardPile;
    private int numMoments;

    public Player(String name) {
        this.name = name;
        deck = buildStarterDeck();
        dream = Collections.emptyList();
        discardPile = new ArrayList<>();
        newDream();
    }

    private List<Card> buildStarterDeck() {
        List<Card> deck = new ArrayList<>(Arrays.asList(
                new StartingCardPositive(),
                new StartingCardPositive(),
                new StartingCardNeutral(),
                new StartingCardNeutral(),
                new StartingCardNegative(),
                new StartingCardNegative()));
        Collections.shuffle(deck);
        return deck;
    }

    public String getName() {
        return name;
    }

    public void newDream() {
        discardPile.addAll(dream);
        dream = new ArrayList<>();
        try {
            drawCard();
        } catch (NoMomentsRemainingException e) {
            // Could only happen if the rules set the number of moments per turn to less than one
            e.printStackTrace();
        } catch (PlayerOutOfCardsException e) {
            // TODO handle player using purify to trash their last card
            e.printStackTrace();
        }
        numMoments = 5;
    }

    public void drawCard() throws NoMomentsRemainingException, PlayerOutOfCardsException {
        if (numMoments < 1) {
            throw new NoMomentsRemainingException();
        }
        numMoments--;
        if (deck.isEmpty()) {
            throw new PlayerOutOfCardsException();
        }
        Card nextCard = deck.remove(0);
        numMoments += nextCard.getOnDrawMomentIncrease();
        dream.add(nextCard);
    }

    public void addCardToDream(Card card) {
        dream.add(card);
    }

    public List<Card> getDream() {
        return Collections.unmodifiableList(dream);
    }

    public int getNumMoments() {
        return numMoments;
    }

    public int getNumNegativeActionsAllowed() {
        long numNegativeCardsInDream = dream.stream()
                .filter(card -> card instanceof NegativeCard)
                .count();
        return 1 - (int) numNegativeCardsInDream;
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discardPile);
    }

    public void addDiscardPileToDeck() {
        Collections.shuffle(discardPile);
        deck.addAll(discardPile);
        discardPile.clear();
    }

    public void removeCardFromDream(Card card) {
        dream.remove(card);
    }

    public void deductMoments(int moments) {
        numMoments -= moments;
    }
}

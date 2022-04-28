package com.goatsandtigers.deckofdreams.shop;

import com.goatsandtigers.deckofdreams.Rules;
import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.card.negative.NegativeCard;
import com.goatsandtigers.deckofdreams.card.neutral.NeutralCard;
import com.goatsandtigers.deckofdreams.card.positive.PositiveCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shop {

    private final Rules rules;

    private List<NegativeCard> negativeCards;
    private List<NeutralCard> neutralCards;
    private List<PositiveCard> positiveCards;

    public Shop(Rules rules) {
        this.rules = rules;
        reset();
    }

    private void reset() {
        int numCardsInShopRow = rules.getNumCardsInShopRow();
        negativeCards = new ArrayList<>(numCardsInShopRow);
        neutralCards = new ArrayList<>(numCardsInShopRow);
        positiveCards = new ArrayList<>(numCardsInShopRow);
        for (int i = 0; i < numCardsInShopRow; i++) {
            NegativeCard randomNegativeCard = NegativeCard.random();
            negativeCards.add(randomNegativeCard);
            NeutralCard randomNeutralCard = NeutralCard.random();
            neutralCards.add(randomNeutralCard);
            PositiveCard randomPositiveCard = PositiveCard.random();
            positiveCards.add(randomPositiveCard);
        }
    }

    public List<NegativeCard> getNegativeCards() {
        return Collections.unmodifiableList(negativeCards);
    }

    public List<NeutralCard> getNeutralCards() {
        return Collections.unmodifiableList(neutralCards);
    }

    public List<PositiveCard> getPositiveCards() {
        return Collections.unmodifiableList(positiveCards);
    }

    public void removeCard(Card card) {
        if (card instanceof NegativeCard) {
            negativeCards.remove(card);
        } else if (card instanceof NeutralCard) {
            neutralCards.remove(card);
        } else if (card instanceof PositiveCard) {
            positiveCards.remove(card);
        }
    }

}

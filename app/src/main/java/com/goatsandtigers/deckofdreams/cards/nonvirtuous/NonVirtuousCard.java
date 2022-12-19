package com.goatsandtigers.deckofdreams.cards.nonvirtuous;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NonVirtuousCard extends Card {

    public int getColor() {
        return Color.RED;
    }

    public static List<Card> listCards() {
        return new ArrayList<>(Arrays.asList(new NonVirtuousKillingCard(),
                new NonVirtuousStealingCard()));
    }

    @Override
    public int getCost() {
        return 0;
    }
}

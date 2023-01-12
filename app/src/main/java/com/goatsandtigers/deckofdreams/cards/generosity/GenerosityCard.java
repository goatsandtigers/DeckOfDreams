package com.goatsandtigers.deckofdreams.cards.generosity;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GenerosityCard extends Card {

    public int getColor() {
        return Color.CYAN;
    }

    public static List<Card> listCards() {
        return new ArrayList<>(Arrays.asList(new Generosity1Card(),
                new Generosity2Card(),
                new Generosity3Card(),
                new Generosity4Card(),
                new Generosity5Card(),
                new Generosity6Card(),
                new GenerosityGiveFearlessnessCard()));
    }
}

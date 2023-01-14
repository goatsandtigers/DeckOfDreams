package com.goatsandtigers.deckofdreams.cards.opponentforce;

import android.graphics.Color;

import com.goatsandtigers.deckofdreams.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class OpponentForceCard extends Card {

    public int getColor() {
        return Color.GREEN;
    }

    public static List<Card> listCards() {
        return new ArrayList<Card>(Arrays.asList(new OpponentForceLifeSavingCard()));
    }

}

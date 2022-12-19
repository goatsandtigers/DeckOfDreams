package com.goatsandtigers.deckofdreams.ui.card;

import android.content.Context;
import android.widget.LinearLayout;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.player.TurnEndsException;

import java.util.List;

public class DreamView extends LinearLayout {

    //private List<Card> drawnCards;
    private Player player;
    private int moments = 1;

    public DreamView(Context context, Player player) {
        super(context);
        this.player = player;
        drawCard();
    }

    public void drawCard() {
        try {
            Card card = player.drawCard();
            CardView cardView = new CardView(getContext(), card);
            addView(cardView);
        } catch (TurnEndsException e) {
            e.printStackTrace();
        }
    }
}

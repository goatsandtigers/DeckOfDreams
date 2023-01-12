package com.goatsandtigers.deckofdreams.ui.card;

import android.content.Context;
import android.widget.GridLayout;

import com.goatsandtigers.deckofdreams.GameController;
import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Player;
import com.goatsandtigers.deckofdreams.player.TurnEndsException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DreamView extends GridLayout {

    private List<CardView> drawnCardViews = new ArrayList<>();
    private Player player;
    private int moments = 1;

    public DreamView(Context context, Player player, GameController gameController) {
        super(context);
        this.player = player;
        int screenWidth = gameController.getDisplayMetrics().widthPixels;
        setColumnCount(screenWidth / CardView.WIDTH);
        drawCard();
    }

    public Card drawCard() {
        try {
            Card card = player.drawCard();
            CardView cardView = new CardView(getContext(), card);
            drawnCardViews.add(cardView);
            addView(cardView);
            return card;
        } catch (TurnEndsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addCard(Card card) {
        CardView cardView = new CardView(getContext(), card);
        drawnCardViews.add(cardView);
        addView(cardView);
    }

    public void removeLastCard() {
        CardView lastCardView = drawnCardViews.remove(drawnCardViews.size() - 1);
        removeView(lastCardView);
    }

    public void clear() {
        drawnCardViews.clear();
        removeAllViews();
    }

    public List<Card> getDrawnCards() {
        return drawnCardViews.stream()
                .map(CardView::getCard)
                .collect(Collectors.toList());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

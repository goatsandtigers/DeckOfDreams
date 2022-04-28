package com.goatsandtigers.deckofdreams;

import com.goatsandtigers.deckofdreams.card.Card;

public interface IGameListener {

    void nextDream();

    void refreshDream();

    Player getCurrentPlayer();

    void removeCardFromShop(Card card);
}

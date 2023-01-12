package com.goatsandtigers.deckofdreams;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Turn;

public interface GameController {

    boolean canAffordCard(Card card);

    void purchaseCard(Card card);

    Turn getCurrentTurn();

    void showMsg(String msg);

    boolean isTurnOver();
}

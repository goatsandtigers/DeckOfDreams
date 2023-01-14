package com.goatsandtigers.deckofdreams;

import android.util.DisplayMetrics;

import com.goatsandtigers.deckofdreams.cards.Card;
import com.goatsandtigers.deckofdreams.player.Turn;

import java.util.List;

public interface GameController {

    boolean canAffordCard(Card card);

    void purchaseCard(Card card);

    Turn getCurrentTurn();

    void showMsg(String msg);

    boolean isTurnOver();

    Card getPurchaseNotAllowedCardInDream();

    List<Class<?>> cardTypesToTrash();

    DisplayMetrics getDisplayMetrics();
}

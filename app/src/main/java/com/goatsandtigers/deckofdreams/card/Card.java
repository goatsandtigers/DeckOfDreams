package com.goatsandtigers.deckofdreams.card;

import android.content.Context;

import com.goatsandtigers.deckofdreams.IGameListener;
import com.goatsandtigers.deckofdreams.Player;
import com.goatsandtigers.deckofdreams.shop.Shop;

import java.util.Objects;

public abstract class Card {

    private final String name;
    private final int cost;
    private final String onPurchaseText;
    private final String onDrawText;
    private final int onDrawMomentIncrease;
    private final boolean addToDream;

    public Card(String name, int cost, String onPurchaseText, String onDrawText, int onDrawMomentIncrease, boolean addToDream) {
        this.name = name;
        this.cost = cost;
        this.onPurchaseText = onPurchaseText;
        this.onDrawText = onDrawText;
        this.onDrawMomentIncrease = onDrawMomentIncrease;
        this.addToDream = addToDream;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getOnPurchaseText() {
        return onPurchaseText;
    }

    public String getOnDrawText() {
        return onDrawText;
    }

    public int getOnDrawMomentIncrease() {
        return onDrawMomentIncrease;
    }

    public boolean isAddToDream() { return addToDream; }

    public void performOnPurchaseAction(Context context, Shop shop, IGameListener gameListener) {}

}

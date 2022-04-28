package com.goatsandtigers.deckofdreams;

import com.goatsandtigers.deckofdreams.card.Card;
import com.goatsandtigers.deckofdreams.shop.Shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Rules rules;
    private final Shop shop;
    private final List<Player> players;

    private int currentPlayer = -1;

    public Game() {
        rules = new Rules();
        shop = new Shop(rules);
        players = new ArrayList<>();
        for (int i = 0; i < rules.getNumPlayers(); i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        startNextPlayersTurn();
    }

    public Shop getShop() {
        return shop;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public void startNextPlayersTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
        players.get(currentPlayer).newDream();
    }

}

package com.goatsandtigers.deckofdreams.player;

public class Turn {

    private final Player player;

    private int merit;
    private int numNonVirtuousCardsTurnedOver;

    public Turn(Player player) {
        this.player = player;
        merit = 1;
        numNonVirtuousCardsTurnedOver = 0;
    }
    
    public Player getPlayer() {
        return player;
    }

    public int getMerit() {
        return merit;
    }

    public int getNumNonVirtuousCardsTurnedOver() {
        return numNonVirtuousCardsTurnedOver;
    }

    public void spendMerit(int cost) {
        merit -= cost;
    }
}

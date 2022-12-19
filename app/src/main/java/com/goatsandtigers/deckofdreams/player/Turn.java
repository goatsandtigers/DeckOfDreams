package com.goatsandtigers.deckofdreams.player;

public class Turn {

    private int merit;
    private int numNonVirtuousCardsTurnedOver;

    public Turn() {
        merit = 1;
        numNonVirtuousCardsTurnedOver = 0;
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

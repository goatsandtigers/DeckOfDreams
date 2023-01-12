package com.goatsandtigers.deckofdreams.player;

public class Turn {

    private final Player player;

    private int merit;
    private int numNonVirtuousCardsTurnedOver;
    private boolean over;

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

    public void gainMerit(int gainedMerit) {
        merit += gainedMerit;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

}

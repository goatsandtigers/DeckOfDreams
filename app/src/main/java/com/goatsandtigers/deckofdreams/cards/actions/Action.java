package com.goatsandtigers.deckofdreams.cards.actions;

public class Action {

    private ActionOrdinal actionOrder;
    private Runnable runnable;

    public Action(ActionOrdinal actionOrder, Runnable runnable) {
        this.actionOrder = actionOrder;
        this.runnable = runnable;
    }

    public ActionOrdinal getActionOrder() {
        return actionOrder;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}

package com.goatsandtigers.deckofdreams.ui;

import com.goatsandtigers.deckofdreams.cards.Card;

import java.util.List;

public class CardStringUtils {

    public static String joinCardTypeNames(List<Class<?>> cardTypes) {
        String result = "";
        for (int i = 0; i < cardTypes.size(); i++) {
            if (i > 0 && i == cardTypes.size() -1) {
                result += " and ";
            } else if (i > 0) {
                result += ", ";
            }
            try {
                Class<?> clazz = cardTypes.get(i);
                Card cardToTrash = (Card) clazz.newInstance();
                result += cardToTrash.getName();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

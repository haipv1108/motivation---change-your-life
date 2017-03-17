package com.brine.motivation_changeyourlife.model;

/**
 * Created by phamhai on 15/03/2017.
 */

public class MotivationAction {
    public static final int CHECKED = 1;
    public static final int UNCHECKED = 0;

    private int id;
    private int cardId;
    private int isUnderstand;
    private int isCanDoIt;
    private int isDoneIt;
    private String action1;
    private String action2;
    private String action3;

    public MotivationAction(){

    }

    public MotivationAction(int _id, int _cardId, int _isUnderstand, int _isCanDoIt,
                            int _isDoneIt, String _action1, String _action2, String _action3){
        this.id = _id;
        this.cardId = _cardId;
        this.isUnderstand = _isUnderstand;
        this.isCanDoIt = _isCanDoIt;
        this.isDoneIt = _isCanDoIt;
    }

    public int getId() {
        return id;
    }

    public MotivationAction setId(int id){
        this.id = id;
        return this;
    }

    public int getCardId() {
        return cardId;
    }

    public MotivationAction setCardId(int cardId){
        this.cardId = cardId;
        return this;
    }

    public int isUnderstand() {
        return isUnderstand;
    }

    public MotivationAction setUnderstand(int understand) {
        isUnderstand = understand;
        return this;
    }

    public int isCanDoIt() {
        return isCanDoIt;
    }

    public MotivationAction setCanDoIt(int canDoIt) {
        isCanDoIt = canDoIt;
        return this;
    }

    public int isDoneIt() {
        return isDoneIt;
    }

    public MotivationAction setDoneIt(int doneIt) {
        isDoneIt = doneIt;
        return this;
    }

    public String getAction1() {
        return action1;
    }

    public MotivationAction setAction1(String action1) {
        this.action1 = action1;
        return this;
    }

    public String getAction2() {
        return action2;
    }

    public MotivationAction setAction2(String action2) {
        this.action2 = action2;
        return this;
    }

    public String getAction3() {
        return action3;
    }

    public MotivationAction setAction3(String action3) {
        this.action3 = action3;
        return this;
    }
}

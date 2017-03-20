package com.brine.motivation_changeyourlife.model;

/**
 * Created by phamhai on 15/03/2017.
 */

public class MotivationCard {
    public static final int STATUS_WAIT_YOU = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_DONE = 2;
    public static final int CHECKED = 1;
    public static final int UNCHECKED = 0;

    private int id;
    private String title;
    private String image;
    private String description;
    private int status;

    private boolean isUnderstand;
    private boolean isCanDoIt;
    private boolean isDoneIt;

    public MotivationCard(){

    }

    public MotivationCard(int _id, String _title, String _image, String _description, int _status){
        this.id = _id;
        this.title = _title;
        this.image = _image;
        this.description = _description;
        this.status = _status;
        this.isUnderstand = false;
        this.isCanDoIt = false;
        this.isDoneIt = false;
    }

    public int getId() {
        return id;
    }

    public MotivationCard setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MotivationCard setTitle(String title){
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public MotivationCard setImage(String image){
        this.image = image;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MotivationCard setDescription(String description){
        this.description = description;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MotivationCard setStatus(int status){
        this.status = status;
        return this;
    }

    public boolean isUnderstand() {
        return isUnderstand;
    }

    public int getUnderstand(){
        if(isUnderstand){
            return CHECKED;
        }
        return UNCHECKED;
    }

    public MotivationCard setUnderstand(int understand) {
        if(understand == UNCHECKED){
            isUnderstand = false;
        }else{
            isUnderstand = true;
        }
        return this;
    }

    public boolean isCanDoIt() {
        return isCanDoIt;
    }

    public int getCanDoIt(){
        if(isCanDoIt){
            return CHECKED;
        }
        return UNCHECKED;
    }

    public MotivationCard setCanDoIt(int canDoIt) {
        if(canDoIt == UNCHECKED){
            isCanDoIt = false;
        }else{
            isCanDoIt = true;
        }
        return this;
    }

    public boolean isDoneIt() {
        return isDoneIt;
    }

    public int getDoneIt(){
        if(isDoneIt){
            return CHECKED;
        }
        return UNCHECKED;
    }

    public MotivationCard setDoneIt(int doneIt) {
        if(doneIt == UNCHECKED){
            isDoneIt = false;
        }else{
            isDoneIt = true;
        }
        return this;
    }
}

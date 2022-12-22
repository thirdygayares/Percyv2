package com.firetera.percyv2.Model;

public class EventThemeModel {
    String eventName;
    String numOfPeople;
    String pricePerPeople;
    int eventImage;

    public EventThemeModel(String eventName, String numOfPeople, String pricePerPeople, int eventImage) {
        this.eventName = eventName;
        this.numOfPeople = numOfPeople;
        this.pricePerPeople = pricePerPeople;
        this.eventImage = eventImage;
    }



    public String getEventName() {
        return eventName;
    }

    public String getNumOfPeople() {
        return numOfPeople;
    }

    public String getPricePerPeople() {
        return pricePerPeople;
    }

    public int getEventImage() {
        return eventImage;
    }
}

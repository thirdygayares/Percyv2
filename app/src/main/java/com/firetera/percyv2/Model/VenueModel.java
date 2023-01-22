package com.firetera.percyv2.Model;

public class VenueModel {
    String venueName;
    String venueAddress;
    int venueImage;

    public VenueModel(String venueName, String venueAddress, int venueImage) {
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueImage = venueImage;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public int getVenueImage() {
        return venueImage;
    }
}

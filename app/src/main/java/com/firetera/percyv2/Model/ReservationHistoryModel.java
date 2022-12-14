package com.firetera.percyv2.Model;

public class ReservationHistoryModel {

    String reservationID;
    String name;
    String mobilenum;
    String event;
    String reservationDate;
    String numofPeople;
    String companyName;
    String venue;
    Boolean status;



    public ReservationHistoryModel(String reservationID, String name, String mobilenum, String event, String reservationDate, String numofPeople, Boolean status, String companyName, String venue) {
        this.reservationID = reservationID;
        this.name = name;
        this.mobilenum = mobilenum;
        this.event = event;
        this.reservationDate = reservationDate;
        this.numofPeople = numofPeople;
        this.status = status;
        this.companyName = companyName;
        this.venue = venue;
    }



    public String getReservationID() {
        return reservationID;
    }

    public String getName() {
        return name;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public String getEvent() {
        return event;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getNumofPeople() {
        return numofPeople;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getVenue() {
        return venue;
    }
}

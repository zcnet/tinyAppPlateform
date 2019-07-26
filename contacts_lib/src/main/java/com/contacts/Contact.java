package com.contacts;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Contact implements Comparable<Contact>{
    private int contactID;
    private java.lang.String contactNameLast;
    private java.lang.String contactNameFirst;
    private int matchedDegree;

    public List<TelNumber> getTelNums() {
        return telNums;
    }

    public void setTelNums(List<TelNumber> telNums) {
        this.telNums = telNums;
    }

    //电话列表
    private List<TelNumber> telNums = new ArrayList<>();

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactNameLast() {
        return contactNameLast;
    }

    public void setContactNameLast(String contactNameLast) {
        this.contactNameLast = contactNameLast;
    }

    public String getContactNameFirst() {
        return contactNameFirst;
    }

    public void setContactNameFirst(String contactNameFirst) {
        this.contactNameFirst = contactNameFirst;
    }


    public int getMatchedDegree() {
        return matchedDegree;
    }

    public void setMatchedDegree(int matchedDegree) {
        this.matchedDegree = matchedDegree;
    }

    @Override
    public int compareTo(@NonNull Contact another) {
        if (getMatchedDegree() > another.getMatchedDegree()) {
            return -1;
        } else if (getMatchedDegree() < another.getMatchedDegree()){
            return 1;
        } else {
            return 0;
        }
    }
}

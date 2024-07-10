package com.revature.entity;

import java.util.Objects;

public class CheckingAccount {

    private String owner;
    private int solaris;
    private int spice;

    public  CheckingAccount(){}

    public CheckingAccount(String owner, int solaris, int spice) {
        this.owner = owner;
        this.solaris = solaris;
        this.spice = spice;
    }

    public int getSolaris() {
        return solaris;
    }

    public void setSolaris(int solaris) {
        this.solaris = solaris;
    }

    public int getSpice() {
        return spice;
    }

    public void setSpice(int spice) {
        this.spice = spice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckingAccount that = (CheckingAccount) o;
        return getSolaris() == that.getSolaris() && getSpice() == that.getSpice() && Objects.equals(getOwner(), that.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwner(), getSolaris(), getSpice());
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "owner=" + owner +
                ", solaris=" + solaris +
                ", spice=" + spice +
                "}\n";
    }
}

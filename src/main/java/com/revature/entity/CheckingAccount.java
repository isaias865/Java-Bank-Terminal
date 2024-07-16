package com.revature.entity;

import java.util.Objects;

public class CheckingAccount {

    private String owner;
    private String name;
    private double solaris;
    private double spice;

    public  CheckingAccount(){}

    public CheckingAccount(String owner, String name, double solaris, double spice) {
        this.owner = owner;
        this.name = name;
        this.solaris = solaris;
        this.spice = spice;
    }

    public CheckingAccount(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSolaris() {
        return solaris;
    }

    public void setSolaris(double solaris) {
        this.solaris = solaris;
    }

    public double getSpice() {
        return spice;
    }

    public void setSpice(double spice) {
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
        return getSolaris() == that.getSolaris() && getSpice() == that.getSpice() && Objects.equals(getOwner(), that.getOwner()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwner(), getName(), getSolaris(), getSpice());
    }

    @Override
    public String toString() {
        return "Checking Account: " + name + "\n" +
                "Owner: " + owner + "\n" +
                "Solaris: " + solaris + "\n" +
                "Spice: " + spice + "\n";

    }
}

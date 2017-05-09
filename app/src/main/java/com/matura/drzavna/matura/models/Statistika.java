/*
 * Copyright (c) 2017. MaturaApp
 */

package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Statistika extends RealmObject {

    @PrimaryKey
    String pitanje;

    boolean isTocno;
    String tvojOdg;
    String tocno;
    String predmet;
    String godina;
    String rok;
    String razina;

    public Statistika(){}

    public Statistika(boolean isTocno, String tvojOdg, String pitanje, String tocno, String predmet, String godina, String rok, String razina) {
        this.isTocno = isTocno;
        this.tvojOdg = tvojOdg;
        this.pitanje = pitanje;
        this.tocno = tocno;
        this.predmet = predmet;
        this.godina = godina;
        this.rok = rok;
        this.razina = razina;
    }


    public boolean isTocno() {
        return isTocno;
    }

    public void setTocno(boolean tocno) {
        isTocno = tocno;
    }

    public String getTvojOdg() {
        return tvojOdg;
    }

    public void setTvojOdg(String tvojOdg) {
        this.tvojOdg = tvojOdg;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getTocno() {
        return tocno;
    }

    public void setTocno(String tocno) {
        this.tocno = tocno;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getRazina() {
        return razina;
    }

    public void setRazina(String razina) {
        this.razina = razina;
    }
}

package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NjemackiPitanje extends RealmObject {


    @PrimaryKey
    int pitanje_id;
    int vrsta_pitanja;

    String pitanje;
    String odgovorA;
    String odgovorB;
    String odgovorC;
    String odgovorD;
    String odgovorE;
    String odgovorF;

    String tocan;
    String godina;
    String rok;
    String razina;


    public int getPitanje_id() {
        return pitanje_id;
    }

    public void setPitanje_id(int pitanje_id) {
        this.pitanje_id = pitanje_id;
    }

    public int getVrsta_pitanja() {
        return vrsta_pitanja;
    }

    public void setVrsta_pitanja(int vrsta_pitanja) {
        this.vrsta_pitanja = vrsta_pitanja;
    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovorA() {
        return odgovorA;
    }

    public void setOdgovorA(String odgovorA) {
        this.odgovorA = odgovorA;
    }

    public String getOdgovorB() {
        return odgovorB;
    }

    public void setOdgovorB(String odgovorB) {
        this.odgovorB = odgovorB;
    }

    public String getOdgovorC() {
        return odgovorC;
    }

    public void setOdgovorC(String odgovorC) {
        this.odgovorC = odgovorC;
    }

    public String getOdgovorD() {
        return odgovorD;
    }

    public void setOdgovorD(String odgovorD) {
        this.odgovorD = odgovorD;
    }

    public String getOdgovorE() {
        return odgovorE;
    }

    public void setOdgovorE(String odgovorE) {
        this.odgovorE = odgovorE;
    }

    public String getOdgovorF() {
        return odgovorF;
    }

    public void setOdgovorF(String odgovorF) {
        this.odgovorF = odgovorF;
    }

    public String getTocan() {
        return tocan;
    }

    public void setTocan(String tocan) {
        this.tocan = tocan;
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

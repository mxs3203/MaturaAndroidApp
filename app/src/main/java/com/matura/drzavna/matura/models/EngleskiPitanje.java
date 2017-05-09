package com.matura.drzavna.matura.models;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 4/15/17.
 */

public class EngleskiPitanje  extends RealmObject {

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

    String ponudenoA;
    String ponudenoB;
    String ponudenoC;
    String ponudenoD;
    String ponudenoE;
    String ponudenoF;
    String ponudenoG;
    String ponudenoH;
    String ponudenoI;

    String tocanA;
    String tocanB;
    String tocanC;
    String tocanD;
    String tocanE;
    String tocanF;
    String tocanG;
    String tocanH;
    String tocanI;

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

    public String getPonudenoA() {
        return ponudenoA;
    }

    public void setPonudenoA(String ponudenoA) {
        this.ponudenoA = ponudenoA;
    }

    public String getPonudenoB() {
        return ponudenoB;
    }

    public void setPonudenoB(String ponudenoB) {
        this.ponudenoB = ponudenoB;
    }

    public String getPonudenoC() {
        return ponudenoC;
    }

    public void setPonudenoC(String ponudenoC) {
        this.ponudenoC = ponudenoC;
    }

    public String getPonudenoD() {
        return ponudenoD;
    }

    public void setPonudenoD(String ponudenoD) {
        this.ponudenoD = ponudenoD;
    }

    public String getPonudenoE() {
        return ponudenoE;
    }

    public void setPonudenoE(String ponudenoE) {
        this.ponudenoE = ponudenoE;
    }

    public String getPonudenoF() {
        return ponudenoF;
    }

    public void setPonudenoF(String ponudenoF) {
        this.ponudenoF = ponudenoF;
    }

    public String getPonudenoG() {
        return ponudenoG;
    }

    public void setPonudenoG(String ponudenoG) {
        this.ponudenoG = ponudenoG;
    }

    public String getPonudenoH() {
        return ponudenoH;
    }

    public void setPonudenoH(String ponudenoH) {
        this.ponudenoH = ponudenoH;
    }

    public String getPonudenoI() {
        return ponudenoI;
    }

    public void setPonudenoI(String ponudenoI) {
        this.ponudenoI = ponudenoI;
    }

    public String getTocanA() {
        return tocanA;
    }

    public void setTocanA(String tocanA) {
        this.tocanA = tocanA;
    }

    public String getTocanB() {
        return tocanB;
    }

    public void setTocanB(String tocanB) {
        this.tocanB = tocanB;
    }

    public String getTocanC() {
        return tocanC;
    }

    public void setTocanC(String tocanC) {
        this.tocanC = tocanC;
    }

    public String getTocanD() {
        return tocanD;
    }

    public void setTocanD(String tocanD) {
        this.tocanD = tocanD;
    }

    public String getTocanE() {
        return tocanE;
    }

    public void setTocanE(String tocanE) {
        this.tocanE = tocanE;
    }

    public String getTocanF() {
        return tocanF;
    }

    public void setTocanF(String tocanF) {
        this.tocanF = tocanF;
    }

    public String getTocanG() {
        return tocanG;
    }

    public void setTocanG(String tocanG) {
        this.tocanG = tocanG;
    }

    public String getTocanH() {
        return tocanH;
    }

    public void setTocanH(String tocanH) {
        this.tocanH = tocanH;
    }

    public String getTocanI() {
        return tocanI;
    }

    public void setTocanI(String tocanI) {
        this.tocanI = tocanI;
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

package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/8/17.
 */

public class KnjizevnostPovezivanje extends RealmObject {

    @PrimaryKey
    int PitanjeID;

    String Pitanje;
    String red1;
    String red2;
    String red3;
    String red4;
    String red5;

    String stupacA;
    String stupacB;
    String stupacC;
    String stupacD;
    String stupacE;
    String stupacF;

    String Tocan1;
    String Tocan2;
    String Tocan3;
    String Tocan4;
    String Tocan5;

    String godina;
    String rok;
    String razina;

    public int getPitanjeID() {
        return PitanjeID;
    }

    public void setPitanjeID(int pitanjeID) {
        PitanjeID = pitanjeID;
    }

    public String getPitanje() {
        return Pitanje;
    }

    public void setPitanje(String pitanje) {
        Pitanje = pitanje;
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
    }

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
    }

    public String getRed4() {
        return red4;
    }

    public void setRed4(String red4) {
        this.red4 = red4;
    }

    public String getRed5() {
        return red5;
    }

    public void setRed5(String red5) {
        this.red5 = red5;
    }

    public String getStupacA() {
        return stupacA;
    }

    public void setStupacA(String stupacA) {
        this.stupacA = stupacA;
    }

    public String getStupacB() {
        return stupacB;
    }

    public void setStupacB(String stupacB) {
        this.stupacB = stupacB;
    }

    public String getStupacC() {
        return stupacC;
    }

    public void setStupacC(String stupacC) {
        this.stupacC = stupacC;
    }

    public String getStupacD() {
        return stupacD;
    }

    public void setStupacD(String stupacD) {
        this.stupacD = stupacD;
    }

    public String getStupacE() {
        return stupacE;
    }

    public void setStupacE(String stupacE) {
        this.stupacE = stupacE;
    }

    public String getStupacF() {
        return stupacF;
    }

    public void setStupacF(String stupacF) {
        this.stupacF = stupacF;
    }

    public String getTocan1() {
        return Tocan1;
    }

    public void setTocan1(String tocan1) {
        Tocan1 = tocan1;
    }

    public String getTocan2() {
        return Tocan2;
    }

    public void setTocan2(String tocan2) {
        Tocan2 = tocan2;
    }

    public String getTocan3() {
        return Tocan3;
    }

    public void setTocan3(String tocan3) {
        Tocan3 = tocan3;
    }

    public String getTocan4() {
        return Tocan4;
    }

    public void setTocan4(String tocan4) {
        Tocan4 = tocan4;
    }

    public String getTocan5() {
        return Tocan5;
    }

    public void setTocan5(String tocan5) {
        Tocan5 = tocan5;
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

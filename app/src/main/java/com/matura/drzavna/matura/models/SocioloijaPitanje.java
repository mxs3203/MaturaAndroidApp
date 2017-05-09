package com.matura.drzavna.matura.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mateosokac on 3/15/17.
 */

public class SocioloijaPitanje  extends RealmObject {

    @PrimaryKey
    int pitanje_id;

    String pitanje;
    String odgovorA;
    String odgovorB;
    String odgovorC;
    String odgovorD;
    boolean vise_odgovora;
    String tocan;
    String tocan2;
    String tocan3;
    String slika;
    String godina;
    String rok;
    String razina;

    public int getPitanje_id() {
        return pitanje_id;
    }

    public void setPitanje_id(int pitanje_id) {
        this.pitanje_id = pitanje_id;
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

    public boolean isVise_odgovora() {
        return vise_odgovora;
    }

    public void setVise_odgovora(boolean vise_odgovora) {
        this.vise_odgovora = vise_odgovora;
    }

    public String getTocan() {
        return tocan;
    }

    public void setTocan(String tocan) {
        this.tocan = tocan;
    }

    public String getTocan2() {
        return tocan2;
    }

    public void setTocan2(String tocan2) {
        this.tocan2 = tocan2;
    }

    public String getTocan3() {
        return tocan3;
    }

    public void setTocan3(String tocan3) {
        this.tocan3 = tocan3;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
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

package com.matura.drzavna.matura.main_menu_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matura.drzavna.matura.DrzavnaMaturaMainMenu;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.ispit_controllers.IspitActivity;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.GeografijaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;
import com.matura.drzavna.matura.models.InformatikaPitanje;
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.models.KemijaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.NjemackiPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.support.DatabaseHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RjesavanjeIspitaRok extends Activity {

    TextView title, pocetna;
    ImageView back;
    Context c;
    Realm realm;
    String razina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_rjesavanje_ispita_rok);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);


        c = this;

        Realm.init(this);
        realm = DatabaseHelper.resetRealm();

        Intent i = getIntent();
        final String table_name = i.getStringExtra("table_name");
        final String godina = i.getStringExtra("godina");
        if(i.hasExtra("razina"))
             razina = i.getStringExtra("razina");

        setupTitleBar(DatabaseHelper.mapTableNameToPresentationValue(table_name));

        final ArrayList<String> rokovi = getAllRokovi(table_name);

        ArrayAdapter<Ispit> adapter = new ArrayAdapter(this, R.layout.jedan_ispit, R.id.textViewIspit, rokovi);
        ListView list = (ListView)findViewById(R.id.rok_ispita_listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(c, RjesavanjeIspitaRazina.class);
                in.putExtra("table_name", table_name);
                in.putExtra("godina", godina);
                in.putExtra("rok", DatabaseHelper.mapRokValueToTableValue(rokovi.get(i)));
                startActivity(in);
                finish();

            }
        });

    }

    private void setupTitleBar(String ispit)
    {
        back = (ImageView) findViewById(R.id.imageViewNavBarIcon1);
        back.setImageResource(R.drawable.nav_bar_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pocetna = (TextView)findViewById(R.id.textViewBarBack);
        pocetna.setText("Opcije");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(c, DrzavnaMaturaMainMenu.class);
                startActivity(i);
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText(ispit);
    }

    private ArrayList<String> getAllRokovi(String name)
    {
        ArrayList<String> godine = new ArrayList<String>();
        if(name.equalsIgnoreCase("povijest_pitanja")) {
            RealmResults<PovijestPitanja> povijest = realm.where(PovijestPitanja.class).findAll();
            for(PovijestPitanja p : povijest){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null)
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("pig_pitanja")) {
            RealmResults<Pig_Pitanja> pig = realm.where(Pig_Pitanja.class).findAll();
            for(Pig_Pitanja p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null)
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("knjizevnost_pitanja")) {
            RealmResults<KnjizevnostPitanja> pig = realm.where(KnjizevnostPitanja.class).findAll();
            for(KnjizevnostPitanja p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("biologija_pitanja")) {
            RealmResults<BiologijaPitanje> pig = realm.where(BiologijaPitanje.class).findAll();
            for(BiologijaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null)
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("sociologija_pitanja")) {
            RealmResults<SocioloijaPitanje> pig = realm.where(SocioloijaPitanje.class).findAll();
            for(SocioloijaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("matematika_pitanja")) {
            RealmResults<MatematikaPitanje> pig = realm.where(MatematikaPitanje.class).findAll();
            for(MatematikaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("fizika_pitanja")) {
            RealmResults<FizikaPitanje> pig = realm.where(FizikaPitanje.class).findAll();
            for(FizikaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("kemija_pitanja")) {
            RealmResults<KemijaPitanje> pig = realm.where(KemijaPitanje.class).findAll();
            for(KemijaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("engleski_pitanja")) {
            RealmResults<EngleskiPitanje> pig = realm.where(EngleskiPitanje.class).findAll();
            for(EngleskiPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("psihologija_pitanja")) {
            RealmResults<EngleskiPitanje> pig = realm.where(EngleskiPitanje.class).findAll();
            for(EngleskiPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("likovni_pitanja")) {
            RealmResults<EngleskiPitanje> pig = realm.where(EngleskiPitanje.class).findAll();
            for(EngleskiPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("njemacki_pitanja")) {
            RealmResults<NjemackiPitanje> pig = realm.where(NjemackiPitanje.class).findAll();
            for(NjemackiPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("informatika_pitanja")) {
            RealmResults<InformatikaPitanje> pig = realm.where(InformatikaPitanje.class).findAll();
            for(InformatikaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }
        else if(name.equalsIgnoreCase("geografija_pitanja")) {
            RealmResults<GeografijaPitanje> pig = realm.where(GeografijaPitanje.class).findAll();
            for(GeografijaPitanje p : pig){
                if(!godine.contains(DatabaseHelper.mapRokToPresentationvalue(p.getRok())) && p.getRok() != null && !p.getRok().equalsIgnoreCase(""))
                    godine.add(DatabaseHelper.mapRokToPresentationvalue(p.getRok()));
            }
        }

        return godine;

    }
}

package com.matura.drzavna.matura.ispit_controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matura.drzavna.matura.DrzavnaMaturaMainMenu;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.StatistikaActivity;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.GramatikaPitanje;
import com.matura.drzavna.matura.models.KemijaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.LikovniPitanje;
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.PsihologijaPitanje;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.models.Statistika;
import com.matura.drzavna.matura.support.DatabaseHelper;
import com.matura.drzavna.matura.support.ListAdapterBiologija;
import com.matura.drzavna.matura.support.ListAdapterEngleski;
import com.matura.drzavna.matura.support.ListAdapterFizika;
import com.matura.drzavna.matura.support.ListAdapterGramatikaPitanja;
import com.matura.drzavna.matura.support.ListAdapterKemija;
import com.matura.drzavna.matura.support.ListAdapterKnjizevnostPitanja;
import com.matura.drzavna.matura.support.ListAdapterKnjizevnostTekst;
import com.matura.drzavna.matura.support.ListAdapterLikovni;
import com.matura.drzavna.matura.support.ListAdapterMatematika;
import com.matura.drzavna.matura.support.ListAdapterPIG;
import com.matura.drzavna.matura.support.ListAdapterPovijest;
import com.matura.drzavna.matura.support.ListAdapterPsihologija;
import com.matura.drzavna.matura.support.ListAdapterSociologija;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class IspitActivity extends Activity {

    Realm realm;
    int start;
    int end;
    String table_name,godina, rok;
    TextView odKudDokud;
    ListView listView;
    TextView title, pocetna, formuletext;
    ImageView back,formule;
    Context c;
    Button nastavi;
    String razina;
    int ispitSize = 0;
    int pitanjeBroj = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_ispit);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

        odKudDokud = (TextView)findViewById(R.id.textViewPitanjaodKuddoKud);
        listView = (ListView) findViewById(R.id.listPovijest);
        listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        listView.setItemsCanFocus(true);

        c = this;


        Intent i = getIntent();
        validateIntentExtra(i);
        setupTitleBar();


        Realm.init(this);
        realm = DatabaseHelper.resetRealm();

        RealmResults<Statistika> s = realm.where(Statistika.class).findAll();
        System.out.println("Statistika pitanje "+s.size());




        if(table_name.equalsIgnoreCase("povijest_pitanja")) {
            RealmResults<PovijestPitanja> ispiti = realm.where(PovijestPitanja.class).beginGroup().equalTo("godina", godina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            if(end < ispiti.size()) {
                List<PovijestPitanja> prvih_pet = ispiti.subList(start, end);
                ListAdapterPovijest customAdapter = new ListAdapterPovijest(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{
                Intent is = new Intent(c, StatistikaActivity.class);

                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }else if(table_name.equalsIgnoreCase("biologija_pitanja")) {
            RealmResults<BiologijaPitanje> ispiti = realm.where(BiologijaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("rok", rok).endGroup().findAll();
            ispitSize = ispiti.size();
            if(end < ispiti.size()) {
                List<BiologijaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterBiologija customAdapter = new ListAdapterBiologija(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{
                Intent is = new Intent(c, StatistikaActivity.class);

                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }
        else if(table_name.equalsIgnoreCase("sociologija_pitanja")) {
            RealmResults<SocioloijaPitanje> ispiti = realm.where(SocioloijaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("rok", rok).endGroup().findAll();
            ispitSize = ispiti.size();
            if(end < ispiti.size()) {
                List<SocioloijaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterSociologija customAdapter = new ListAdapterSociologija(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{
                Intent is = new Intent(c, StatistikaActivity.class);
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }
        else if(table_name.equalsIgnoreCase("pig_pitanja")) {

            RealmResults<Pig_Pitanja> ispiti = realm.where(Pig_Pitanja.class).beginGroup().equalTo("godina", godina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if(end < ispiti.size()) {
                List<Pig_Pitanja> prvih_pet = ispiti.subList(start, end);
                ListAdapterPIG customAdapter = new ListAdapterPIG(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{
                Intent is = new Intent(c, StatistikaActivity.class);

                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }
        else if(table_name.equalsIgnoreCase("matematika_pitanja")) {

            RealmResults<MatematikaPitanje> ispiti = realm.where(MatematikaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("rok",rok).equalTo("razina",razina).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if(end < ispiti.size()) {
                List<MatematikaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterMatematika customAdapter = new ListAdapterMatematika(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{
                Intent is = new Intent(c, StatistikaActivity.class);

                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }
        else if(table_name.equalsIgnoreCase("fizika_pitanja")) {

            RealmResults<FizikaPitanje> ispiti = realm.where(FizikaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if(end < ispiti.size()) {
                List<FizikaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterFizika customAdapter = new ListAdapterFizika(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina, this);
                listView .setAdapter(customAdapter);
            }else{

                Intent is = new Intent(c, StatistikaActivity.class);
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
                finish();
            }
        }
        else if(table_name.equalsIgnoreCase("knjizevnost_pitanja")) {

            System.out.println("knjizevnost pitanja");
            RealmResults<KnjizevnostPitanja> ispiti = realm.where(KnjizevnostPitanja.class).beginGroup().equalTo("godina", godina).equalTo("razina",razina).equalTo("rok", rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if(end < ispiti.size()) {
                List<KnjizevnostPitanja> prvih_pet = ispiti.subList(start, end);
                ListAdapterKnjizevnostPitanja customAdapter = new ListAdapterKnjizevnostPitanja(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            }else{
                Intent in = new Intent(c, IspitActivity.class);
                in.putExtra("table_name", "knjizevnost_tekst");
                pitanjeBroj = end;
                start = 0;
                end = 1;

                in.putExtra("godina", godina);
                in.putExtra("start", start);
                in.putExtra("end", end);
                in.putExtra("rok", rok);
                in.putExtra("razina", razina);
                startActivity(in);
                finish();

            }
        }
        else if(table_name.equalsIgnoreCase("knjizevnost_tekst")) {
            odKudDokud.setText(DatabaseHelper.mapTableNameToPresentationValue(table_name));
            System.out.println("knjizevnost tekst");
            RealmResults<KnjizevnostTekst> ispiti = realm.where(KnjizevnostTekst.class).beginGroup().equalTo("godina", godina).equalTo("razina", razina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<KnjizevnostTekst> prvih_pet = ispiti.subList(start, end);
                ListAdapterKnjizevnostTekst customAdapter = new ListAdapterKnjizevnostTekst(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent in = new Intent(c, IspitActivity.class);
                in.putExtra("table_name", "gramatika_pitanja");
                pitanjeBroj = end;
                start = 0;
                end = 1;
                in.putExtra("godina", godina);
                in.putExtra("start", start);
                in.putExtra("end", end);
                in.putExtra("rok", rok);
                in.putExtra("razina", razina);
                in.putExtra("broj", pitanjeBroj);
                finish();
                startActivity(in);
            }
            realm.close();
        }
        else if(table_name.equalsIgnoreCase("gramatika_pitanja")) {
            odKudDokud.setText(DatabaseHelper.mapTableNameToPresentationValue(table_name));
            System.out.println("knjizevnost gramatika");
            RealmResults<GramatikaPitanje> ispiti = realm.where(GramatikaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("razina", razina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<GramatikaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterGramatikaPitanja customAdapter = new ListAdapterGramatikaPitanja(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent is = new Intent(c, StatistikaActivity.class);
                finish();
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
            }
            realm.close();
        }
        else if(table_name.equalsIgnoreCase("kemija_pitanja")) {
            RealmResults<KemijaPitanje> ispiti = realm.where(KemijaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<KemijaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterKemija customAdapter = new ListAdapterKemija(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent is = new Intent(c, StatistikaActivity.class);
                finish();
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
            }
            realm.close();
        }
        else if(table_name.equalsIgnoreCase("engleski_pitanja")) {
            RealmResults<EngleskiPitanje> ispiti = realm.where(EngleskiPitanje.class).beginGroup().equalTo("godina", godina).equalTo("razina", razina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("ENGLESKI : Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<EngleskiPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterEngleski customAdapter = new ListAdapterEngleski(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent is = new Intent(c, StatistikaActivity.class);
                finish();
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
            }
        }
        else if(table_name.equalsIgnoreCase("psihologija_pitanja")) {
            RealmResults<PsihologijaPitanje> ispiti = realm.where(PsihologijaPitanje.class).beginGroup().equalTo("godina", godina).equalTo("razina", razina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("ENGLESKI : Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<PsihologijaPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterPsihologija customAdapter = new ListAdapterPsihologija(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent is = new Intent(c, StatistikaActivity.class);
                finish();
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
            }
        }
        else if(table_name.equalsIgnoreCase("likovni_pitanja")) {
            RealmResults<LikovniPitanje> ispiti = realm.where(LikovniPitanje.class).beginGroup().equalTo("godina", godina).equalTo("razina", razina).equalTo("rok",rok).endGroup().findAll();
            ispitSize = ispiti.size();
            System.out.println("ENGLESKI : Razina :" + razina + "Godina: " + godina + "Size iz baze:" + ispiti.size());
            if (end < ispiti.size()) {
                List<LikovniPitanje> prvih_pet = ispiti.subList(start, end);
                ListAdapterLikovni customAdapter = new ListAdapterLikovni(this, R.layout.activity_ispit, prvih_pet, start, end, table_name, godina,this);
                listView.setAdapter(customAdapter);
            } else {
                Intent is = new Intent(c, StatistikaActivity.class);
                finish();
                is.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                is.putExtra("godina", godina);
                is.putExtra("razina", razina);
                is.putExtra("size", ispiti.size());
                startActivity(is);
            }
        }

    }
    private void validateIntentExtra(Intent i)
    {
        table_name = i.getStringExtra("table_name");
        godina = i.getStringExtra("godina");
        if(i.hasExtra("razina")) {
            razina = i.getStringExtra("razina");
        }
        if(i.hasExtra("rok")) {
            rok = i.getStringExtra("rok");
        }
        if(i.hasExtra("start") && i.hasExtra("end")) {
            if(table_name.equalsIgnoreCase("matematika_pitanja") || table_name.equalsIgnoreCase("fizika_pitanja")
                    || table_name.equalsIgnoreCase("kemija_pitanja")) {
                start = i.getIntExtra("start", 0) + 1;
                end = i.getIntExtra("end", 1) + 1;
                odKudDokud.setText("Pitanje  " + end);
            }
            else if(table_name.equalsIgnoreCase("gramatika_pitanja") || table_name.equalsIgnoreCase("knjizevnost_tekst")
                    || table_name.equalsIgnoreCase("knjizevnost_pitanja"))
            {
                start = i.getIntExtra("start", 0) + 1;
                end = i.getIntExtra("end", 1) + 1;
                odKudDokud.setText(DatabaseHelper.mapTableNameToPresentationValue(table_name));
            }
            else
            {
                start = i.getIntExtra("start", 0) + 1;
                end = i.getIntExtra("end", 1) + 1;
                odKudDokud.setText("Pitanje  " + end);
            }
            System.out.println("OD NASTAVI\nod kud: "+ start);
            System.out.println("do kud: "+ end);
        }
        else
        {
            if(table_name.equalsIgnoreCase("matematika_pitanja") || table_name.equalsIgnoreCase("fizika_pitanja")
                    || table_name.equalsIgnoreCase("kemija_pitanja")) {
                start = 0;
                end = 1;
                odKudDokud.setText("Pitanje " + end);
            }
            else
            {
                start = 0;
                end = 1;
                odKudDokud.setText("Pitanje" + end);
            }

        }
        System.out.println("--------------------------------------");
        System.out.println("table: " + table_name);
        System.out.println("razina: " + razina);
        System.out.println("rok: " + rok);
        System.out.println("godina: " + godina);
        System.out.println("--------------------------------------");
    }

    private void setupTitleBar()
    {
        /*if(table_name.equalsIgnoreCase("matematika_pitanja") || table_name.equalsIgnoreCase("kemija_pitanja")
             || table_name.equalsIgnoreCase("fizika_pitanja")) {
            formule = (ImageView) findViewById(R.id.imageViewIconNavBar2);
            formuletext = (TextView) findViewById(R.id.textViewBarOptions);
            formuletext.setText("Formule");
            formule.setImageResource(R.drawable.formule);
            formuletext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        */
        back = (ImageView) findViewById(R.id.imageViewNavBarIcon1);
        back.setImageResource(R.drawable.nav_bar_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, DrzavnaMaturaMainMenu.class);
                finish();
                startActivity(i);
            }
        });
        pocetna = (TextView)findViewById(R.id.textViewBarBack);
        pocetna.setText("Završi");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, StatistikaActivity.class);
                i.putExtra("predmet", DatabaseHelper.mapTableNameToPresentationValue(table_name));
                i.putExtra("godina", godina);
                i.putExtra("razina", razina);
                i.putExtra("size", ispitSize);
                startActivity(i);
                finish();
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText(DatabaseHelper.mapTableNameToPresentationValue(table_name));
    }
}

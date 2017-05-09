/*
 * Copyright (c) 2017. MaturaApp
 */

package com.matura.drzavna.matura;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matura.drzavna.matura.models.Statistika;
import com.matura.drzavna.matura.support.DatabaseHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class StatistikaActivity extends Activity {

    int tocna, netocna;
    float postotak;
    Realm realm;
    String godina, predmet, razina;
    ListView listView;
    int size;

    TextView title, pocetna, formuletext;
    ImageView back,formule;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_statistika);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

        c = this;

        Intent i = getIntent();

        if(i.hasExtra("razina") && i.hasExtra("godina") && i.hasExtra("predmet")  && i.hasExtra("size")) {
            razina = i.getStringExtra("razina");
            predmet = i.getStringExtra("predmet");
            godina = i.getStringExtra("godina");
            size = i.getIntExtra("size", 40);
        }


        TextView postotakView = (TextView)findViewById(R.id.textViewPostotak);
        TextView ime = (TextView)findViewById(R.id.textViewPredmetIme);
        TextView ukupnoPitanja = (TextView)findViewById(R.id.textViewUkupnoPitanja);
        TextView tocno = (TextView)findViewById(R.id.textViewTocno);
        TextView netocno = (TextView)findViewById(R.id.textViewNeTocno);
        TextView odgovorena = (TextView)findViewById(R.id.textViewOdgovorenaPitanja);
        Button zavrsi = (Button) findViewById(R.id.buttonZavrsi);
        zavrsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, DrzavnaMaturaMainMenu.class);
                finish();
                startActivity(i);
            }
        });

        ime.setText(predmet);
        ukupnoPitanja.setText(size + "");



        Realm.init(this);
        realm = DatabaseHelper.resetRealm();


//        setupTitleBar(predmet);


        tocna = 0;
        netocna = 0;
        postotak = (float) 0.0;
        RealmResults<Statistika> stat = realm.where(Statistika.class).equalTo("predmet", predmet).findAll();
        for(Statistika s : stat)
        {
            if(s.isTocno())
                tocna++;
            else
                netocna++;
        }
        postotak = 100.0f * tocna / stat.size();
        postotakView.setText("~ "+ postotak + " %" );
        tocno.setText(tocna + "");
        netocno.setText(netocna+"");
        odgovorena.setText(stat.size() + " / " + size );




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DrzavnaMaturaMainMenu.class);
        finish();
        startActivity(i);
    }

    private void setupTitleBar(String table_name)
    {
        formule = (ImageView) findViewById(R.id.imageViewIconNavBar2);
        formuletext = (TextView)findViewById(R.id.textViewBarOptions);
        formuletext.setText("Formule");
        formule.setImageResource(R.drawable.formule);
        formule.setVisibility(View.GONE);
        formuletext.setVisibility(View.GONE);

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
        pocetna.setText("Početna");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, DrzavnaMaturaMainMenu.class);
                finish();
                startActivity(i);
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText(table_name);
    }
}

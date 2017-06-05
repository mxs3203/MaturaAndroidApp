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
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.support.DatabaseHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class RjesavanjeIspitaRazina extends Activity {

    TextView title, pocetna;
    ImageView back;
    Context c;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_rjesavanje_ispita_razina);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

        c = this;

        Intent i = getIntent();
        final String table_name = i.getStringExtra("table_name");
        final String godina = i.getStringExtra("godina");
        final String rok = i.getStringExtra("rok");
        if(table_name.equalsIgnoreCase("pig_pitanja") || table_name.equalsIgnoreCase("povijest_pitanja") ||
                table_name.equalsIgnoreCase("biologija_pitanja") || table_name.equalsIgnoreCase("sociologija_pitanja")
                || table_name.equalsIgnoreCase("fizika_pitanja")  || table_name.equalsIgnoreCase("kemija_pitanja")
                || table_name.equalsIgnoreCase("psihologija_pitanja")  || table_name.equalsIgnoreCase("likovni_pitanja")
                || table_name.equalsIgnoreCase("informatika_pitanja") || table_name.equalsIgnoreCase("njemacki_pitanja")
                || table_name.equalsIgnoreCase("geografija_pitanja")){

            Intent in = new Intent(c, RjesavanjeIspitaInfoFragments.class);
            in.putExtra("table_name", table_name);
            in.putExtra("godina", godina);
            in.putExtra("rok", rok);
            startActivity(in);
            finish();
        }

        System.out.println("koja bazica godine ispita: "+table_name + " iz godine " + godina);
        setupTitleBar(DatabaseHelper.mapTableNameToPresentationValue(table_name));
        final String[] razine = new String[2];
        razine[0] = "Viša razina (A)";
        razine[1] = "Niža razina (B)";
        ArrayAdapter<Ispit> adapter = new ArrayAdapter(this, R.layout.jedan_ispit, R.id.textViewIspit, razine);
        ListView list = (ListView)findViewById(R.id.ispit_razine);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(c, RjesavanjeIspitaInfoFragments.class);
                in.putExtra("table_name", table_name);
                in.putExtra("godina", godina);
                in.putExtra("rok", rok);
                in.putExtra("razina", DatabaseHelper.mapRazinaToColumnName(razine[i], table_name));
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
}

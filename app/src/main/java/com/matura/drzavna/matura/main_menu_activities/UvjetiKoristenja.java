package com.matura.drzavna.matura.main_menu_activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.matura.drzavna.matura.R;

public class UvjetiKoristenja extends Activity {

    TextView pocetna,title;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_uvjeti_koristenja);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);

        back = (ImageView) findViewById(R.id.imageViewNavBarIcon1);
        back.setImageResource(R.drawable.nav_bar_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pocetna = (TextView)findViewById(R.id.textViewBarBack);
        pocetna.setText("Početna");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText("Opći uvjeti korištenja");
    }
}

package com.matura.drzavna.matura.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.ispit_controllers.IspitActivity;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by mateosokac on 3/20/17.
 */

public class ListAdapterPovijest extends ArrayAdapter<PovijestPitanja> implements View.OnClickListener{
    List<PovijestPitanja> items;
    Button nastavi, predaj;
    int start,end;
    String table_name,godina;
    Activity a;
    TextView mojOdg;
    Realm realm;


    public ListAdapterPovijest(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterPovijest(Context context, int resource, List<PovijestPitanja> items, int start, int end, String table_name, String godina, Activity a) {
        super(context, resource);
        this.items = items;
        this.start = start;
        this.a = a;
        this.end = end;
        this.table_name = table_name;
        this.godina = godina;



        Realm.init(context);
        realm = DatabaseHelper.resetRealm();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        int vrsta_pitanja = 0;
        final PovijestPitanja p = items.get(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());

            if(p.getOdgovorA() == null || p.getOdgovorC() == null || p.getOdgovorB() == null) {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                vrsta_pitanja = 2;
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
                predaj = (Button)v.findViewById(R.id.buttonOcijeni);
                final TextView tocanNapomena = (TextView)v.findViewById(R.id.textViewNapomenaTocan);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                ikonica.setClickable(false);
                predaj.setVisibility(View.VISIBLE);
                predaj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mojOdg.getText().toString().equalsIgnoreCase("") ) {
                            if (p.getTocan().contains("/")) {
                                String[] parts = p.getTocan().split("/");
                                for (String s : parts) {
                                    String sss = s.trim();
                                    if (sss.equalsIgnoreCase(mojOdg.getText().toString().trim())) {
                                        ikonica.setChecked(true);
                                        mojOdg.setBackgroundColor(Color.parseColor("#4d91e3"));
                                    } else {
                                        tocanNapomena.setText("Točan odgovor je: " + p.getTocan() + "\nNapomena: Odgovor nije dodan u statistku.");
                                        mojOdg.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                    }
                                }
                            } else {

                                if (mojOdg.getText().toString().equalsIgnoreCase(p.getTocan())) {
                                    ikonica.setChecked(true);
                                    mojOdg.setBackgroundColor(Color.parseColor("#4d91e3"));
                                } else {
                                    tocanNapomena.setText("Točan odgovor je: " + p.getTocan() + "\nNapomena: Odgovor nije dodan u statistku.");
                                    mojOdg.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                }
                            }
                        }

                    }
                });
            }
            else if(p.getOdgovorA() != null && p.getOdgovorC() != null && p.getOdgovorB() != null)
            {
                v = vi.inflate(R.layout.pitanje, null);
                vrsta_pitanja = 3;
            }
        }


        if (p != null) {

            nastavi = (Button)v.findViewById(R.id.nastavi_pitanja);
            nastavi.setVisibility(View.VISIBLE);
            nastavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(), IspitActivity.class);
                    in.putExtra("table_name", table_name);
                    in.putExtra("godina", godina);
                    in.putExtra("start", start);
                    in.putExtra("rok", p.getRok());
                    in.putExtra("end", end);
                    in.putExtra("razina", p.getRazina());
                    a.finish();
                    getContext().startActivity(in);

                }
            });

            System.out.println("items size"+items.size());
            System.out.println(items.get(items.size()-1).getPitanje());


            SmartImageView slika = (SmartImageView)v.findViewById(R.id.my_image);
            if(p.getSlika() != null) {
                slika.setImageUrl("http://drzavnamatura.000webhostapp.com/images/povijest/" + p.getSlika() + ".png");
            }

            TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);

            pitanje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
//                            + p.getPitanje_id() + ", " +p.getGodina() +","+ p.getRok() + ","+p.getRazina(), Toast.LENGTH_LONG).show();
                }
            });



            TextView broj_pitanja = (TextView) v.findViewById(R.id.textViewBrojPitanja);
            broj_pitanja.setText(start + position + 1+  "");




            if(vrsta_pitanja == 3) {
                pitanje.setText(p.getPitanje());
                setupABCD(p, v, position);
            }
            else if(vrsta_pitanja == 2){

                pitanje.setText(p.getPitanje().replaceAll("_____", "..."));
            }


            if (p.getText_pitanje() != null) {
                TextView pitanjetext = (TextView) v.findViewById(R.id.textViewPitanjeText);
                pitanjetext.setText("\"" + p.getText_pitanje() + "\"");
            }
        }



        return v;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public PovijestPitanja getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(PovijestPitanja item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {
        view.setBackgroundResource(R.drawable.odgovor_border);
    }


    public void setupABCD(final PovijestPitanja p, View v, final int position)
    {
        final ArrayList<TextView> odgovori = new ArrayList<>();

        if (p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null && p.getOdgovorD() != null) {
            final TextView odgovorA = (TextView) v.findViewById(R.id.textViewOdgovorA);
            odgovori.add(odgovorA);
            final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
            odgovorA.setText(p.getOdgovorA());
            odgovorA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorA.setAlpha((float) 0.5);
                    odgovorA.setClickable(false);
                    ocijeniZadatak(odgovori, p.getTocan());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorA.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                }
            });
            final TextView odgovorB = (TextView) v.findViewById(R.id.textViewOdgovorB);
            odgovori.add(odgovorB);
            final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
            odgovorB.setText(p.getOdgovorB());
            odgovorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorB.setAlpha((float) 0.5);
                    odgovorB.setClickable(false);
                    ocijeniZadatak(odgovori, p.getTocan());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorB.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorB.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                }
            });

            final TextView odgovorC = (TextView) v.findViewById(R.id.textViewOdgovorC);
            TextView slovoC = (TextView) v.findViewById(R.id.odgCSlovo);
            odgovori.add(odgovorC);
            odgovorC.setText(p.getOdgovorC());
            odgovorC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorC.setAlpha((float) 0.5);
                    odgovorC.setClickable(false);
                    ocijeniZadatak(odgovori, p.getTocan());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorC.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorC.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                }
            });

            final TextView odgovorD = (TextView) v.findViewById(R.id.textViewOdgovorD);
            TextView slovoD = (TextView) v.findViewById(R.id.odgDSlovo);
            odgovori.add(odgovorD);
            odgovorD.setText(p.getOdgovorD());
            odgovorD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorD.setAlpha((float) 0.5);
                    odgovorD.setClickable(false);
                    ocijeniZadatak(odgovori, p.getTocan());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorD.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorD.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                }
            });
        }
    }

    public static void ocijeniZadatak(ArrayList<TextView> odgovori, String tocan)
    {
        for(TextView v : odgovori)
        {
            v.setClickable(false);
            if(v.getText().toString().equals(tocan)) {
                v.setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#141F4B"));
            }
            else
                v.setBackgroundColor(Color.parseColor("#AAAAAA"));
        }
    }
}

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
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.GeografijaPitanje;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ListAdapterGeografija extends ArrayAdapter<GeografijaPitanje> implements View.OnClickListener {
    List<GeografijaPitanje> items;
    Button nastavi, predaj,predaj1,predaj2,predaj3,predaj4;
    int start,end;
    TextView mojOdg1;
    TextView mojOdg2;
    TextView mojOdg3;
    TextView mojOdg;
    TextView mojOdg4;
    CheckBox ikonica1;
    CheckBox ikonica2;
    CheckBox ikonica3;
    CheckBox ikonica4;
    Realm realm;
    String table_name,godina;
    Activity a;
    GeografijaPitanje pp;
    TextView tocanNapomena1,tocanNapomena2,tocanNapomena3,tocanNapomena4;


    public ListAdapterGeografija(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterGeografija(Context context, int resource, List<GeografijaPitanje> items, int start, int end, String table_name, String godina, Activity a) {
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
        final GeografijaPitanje p = items.get(position);
        pp = p;
        int vrsta_pitanja = 0;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            System.out.println(p.getVise_pitanja());
            if(p.getVise_pitanja() == 1) {
                v = vi.inflate(R.layout.pitanje_nadopuna_vise_pitanja, null);
                vrsta_pitanja = 2;
                System.out.println("Nadopuna vise");

            }
            else
            {
                if(p.getOdgovorA() != null && p.getOdgovorC() != null && p.getOdgovorB() != null) {
                    v = vi.inflate(R.layout.pitanje, null);
                    vrsta_pitanja = 1;
                    System.out.println("Normalno pitanje");
                }
                else
                {
                    v = vi.inflate(R.layout.pitanje_nadopuna, null);
                    vrsta_pitanja = 3;
                    System.out.println("Nadopuna");
                }
            }
        }



        if (p != null) {

            TextView broj_pitanja = (TextView) v.findViewById(R.id.textViewBrojPitanja);
            broj_pitanja.setText(start + position + 1 + "");

            SmartImageView slika = (SmartImageView) v.findViewById(R.id.my_image);
            if (p.getSlika() != null) {
                slika.setImageUrl("http://drzavnamatura.000webhostapp.com/images/geografija/" + p.getSlika() + ".png",2);
            }

            nastavi = (Button)v.findViewById(R.id.nastavi_pitanja);
            nastavi.setVisibility(View.VISIBLE);
            nastavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(), IspitActivity.class);
                    in.putExtra("table_name", table_name);
                    in.putExtra("godina", godina);
                    in.putExtra("rok", p.getRok());
                    in.putExtra("start", start);
                    in.putExtra("end", end);
                    in.putExtra("razina", p.getRazina());
                    a.finish();
                    getContext().startActivity(in);

                }
            });

            if (vrsta_pitanja == 1) {//abcd
                TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);
                pitanje.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
                                + p.getPitanje_id() + ", " +p.getGodina() +","+ p.getRok() + ","+p.getRazina(), Toast.LENGTH_LONG).show();
                    }
                });
                pitanje.setText(p.getPitanje());
                setupABCD(p, position, v);
            }
            else if(vrsta_pitanja == 3)
            {
                TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);
                pitanje.setText(p.getPitanje());
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
            }else if (vrsta_pitanja == 2) {//vise pitanja

                TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);
                pitanje.setVisibility(View.GONE);
                TextView pitanje1 = (TextView) v.findViewById(R.id.textViewPitanje1);
                TextView pitanje2= (TextView) v.findViewById(R.id.textViewPitanje2);
                TextView pitanje3 = (TextView) v.findViewById(R.id.textViewPitanje3);
                TextView pitanje4 = (TextView) v.findViewById(R.id.textViewPitanje4);



                ikonica1 = (CheckBox) v.findViewById(R.id.checkBoxTocno1);
                ikonica1.setClickable(false);
                ikonica2 = (CheckBox) v.findViewById(R.id.checkBoxTocno2);
                ikonica2.setClickable(false);
                ikonica3 = (CheckBox) v.findViewById(R.id.checkBoxTocno3);
                ikonica3.setClickable(false);
                ikonica4 = (CheckBox) v.findViewById(R.id.checkBoxTocno4);
                ikonica4.setClickable(false);

                mojOdg1 = (EditText)v.findViewById(R.id.mojOdg1);
                mojOdg2 = (EditText)v.findViewById(R.id.mojOdg2);
                mojOdg3 = (EditText)v.findViewById(R.id.mojOdg3);
                mojOdg4 = (EditText)v.findViewById(R.id.mojOdg4);

                predaj1 = (Button)v.findViewById(R.id.buttonOcijeni1);
                predaj1.setVisibility(View.VISIBLE);
                predaj1.setOnClickListener(this);

                predaj2 = (Button)v.findViewById(R.id.buttonOcijeni2);
                predaj2.setVisibility(View.VISIBLE);
                predaj2.setOnClickListener(this);

                predaj3 = (Button)v.findViewById(R.id.buttonOcijeni3);
                predaj3.setVisibility(View.VISIBLE);
                predaj3.setOnClickListener(this);

                predaj4 = (Button)v.findViewById(R.id.buttonOcijeni4);
                predaj4.setVisibility(View.VISIBLE);
                predaj4.setOnClickListener(this);

                tocanNapomena1 = (TextView)v.findViewById(R.id.textViewNapomenaTocan1);
                tocanNapomena2 = (TextView)v.findViewById(R.id.textViewNapomenaToca2);
                tocanNapomena3 = (TextView)v.findViewById(R.id.textViewNapomenaToca3);
                tocanNapomena4 = (TextView)v.findViewById(R.id.textViewNapomenaTocan4);




                if(p.getPitanje() != null) {
                    pitanje1.setText(p.getPitanje() + "");
                }else
                {
                    predaj1.setVisibility(View.GONE);
                    mojOdg1.setVisibility(View.GONE);
                    ikonica1.setVisibility(View.GONE);
                }
                if(p.getPitanje2() != null) {
                    pitanje2.setText(p.getPitanje2() + "");
                }else
                {
                    predaj2.setVisibility(View.GONE);
                    mojOdg2.setVisibility(View.GONE);
                    ikonica2.setVisibility(View.GONE);
                }
                if(p.getPitanje3() != null) {
                    pitanje3.setText(p.getPitanje3() + "");
                }
                else{
                    predaj3.setVisibility(View.GONE);
                    ikonica3.setVisibility(View.GONE);
                    mojOdg3.setVisibility(View.GONE);
                }
                if(p.getPitanje4() != null) {
                    pitanje4.setText(p.getPitanje4() + "");
                }
                else {
                    pitanje4.setVisibility(View.GONE);
                    predaj4.setVisibility(View.GONE);
                    mojOdg4.setVisibility(View.GONE);
                    ikonica4.setVisibility(View.GONE);
                }

            }

        }

        return v;
    }

    private void setupABCD(final GeografijaPitanje p, final int position, View v)
    {
        final ArrayList<TextView> odgovori = new ArrayList<>();

        if (p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null && p.getOdgovorD() != null) {
            final TextView odgovorA = (TextView) v.findViewById(R.id.textViewOdgovorA);
            final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
            odgovori.add(odgovorA);
            odgovorA.setText(p.getOdgovorA());
            odgovorA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorA.setAlpha((float) 0.5);
                    ocijeniZadatak(odgovori, p.getTocan());
                    odgovorA.setClickable(false);
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorA.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);

                        }
                    });
                    realm.close();
                }
            });
            final TextView odgovorB = (TextView) v.findViewById(R.id.textViewOdgovorB);
            final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
            odgovorB.setText(p.getOdgovorB());
            odgovori.add(odgovorB);
            odgovorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorB.setAlpha((float) 0.5);
                    ocijeniZadatak(odgovori, p.getTocan());
                    odgovorB.setClickable(false);
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorB.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                    realm.close();
                }
            });

            final TextView odgovorC = (TextView) v.findViewById(R.id.textViewOdgovorC);
            TextView slovoC = (TextView) v.findViewById(R.id.odgCSlovo);
            odgovorC.setText(p.getOdgovorC());
            odgovori.add(odgovorC);
            odgovorC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorC.setAlpha((float) 0.5);
                    ocijeniZadatak(odgovori, p.getTocan());
                    odgovorC.setClickable(false);
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorC.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                    realm.close();
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
                    ocijeniZadatak(odgovori, p.getTocan());
                    odgovorD.setClickable(false);
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorD.getText().toString(), p.getPitanje(), p.getTocan(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                    realm.close();
                }
            });
        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public GeografijaPitanje getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(GeografijaPitanje item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonOcijeni1){
            if(!mojOdg1.getText().toString().equalsIgnoreCase("") ) {
                if (pp.getOdgovorA().contains("ILI")) {
                    String[] parts = pp.getOdgovorA().split("ILI");
                    for (String s : parts) {
                        String sss = s.trim();
                        if (sss.equalsIgnoreCase(mojOdg1.getText().toString().trim())) {
                            ikonica1.setChecked(true);
                            mojOdg1.setBackgroundColor(Color.parseColor("#4d91e3"));
                        } else {
                            tocanNapomena1.setText("Točan odgovor je: " + pp.getOdgovorA() + "\nNapomena: Odgovor nije dodan u statistku.");
                            mojOdg1.setBackgroundColor(Color.parseColor("#AAAAAA"));
                        }
                    }
                } else {

                    if (mojOdg1.getText().toString().equalsIgnoreCase(pp.getOdgovorA())) {
                        ikonica1.setChecked(true);
                        mojOdg1.setBackgroundColor(Color.parseColor("#4d91e3"));
                    } else {
                        tocanNapomena1.setText("Točan odgovor je: " + pp.getOdgovorA() + "\nNapomena: Odgovor nije dodan u statistku.");
                        mojOdg1.setBackgroundColor(Color.parseColor("#AAAAAA"));
                    }
                }
            }
        }
        else if(view.getId() == R.id.buttonOcijeni2)
        {
            if(!mojOdg2.getText().toString().equalsIgnoreCase("") ) {
                if (pp.getOdgovorB().contains("ILI")) {
                    String[] parts = pp.getOdgovorB().split("ILI");
                    for (String s : parts) {
                        if (s.equalsIgnoreCase(mojOdg2.getText().toString())) {
                            ikonica2.setChecked(true);
                            mojOdg2.setBackgroundColor(Color.parseColor("#4d91e3"));
                        } else {
                            tocanNapomena2.setText("Točan odgovor je: " + pp.getOdgovorB() + "\nNapomena: Odgovor nije dodan u statistku.");
                            mojOdg2.setBackgroundColor(Color.parseColor("#AAAAAA"));
                        }
                    }
                } else {

                    if (mojOdg2.getText().toString().equalsIgnoreCase(pp.getOdgovorB())) {
                        ikonica2.setChecked(true);
                        mojOdg2.setBackgroundColor(Color.parseColor("#4d91e3"));
                    } else {
                        tocanNapomena2.setText("Točan odgovor je: " + pp.getOdgovorB() + "\nNapomena: Odgovor nije dodan u statistku.");
                        mojOdg2.setBackgroundColor(Color.parseColor("#AAAAAA"));
                    }
                }
            }
        }
        else if(view.getId() == R.id.buttonOcijeni3)
        {
            try {
                if (!mojOdg3.getText().toString().equalsIgnoreCase("")) {
                    if (pp.getOdgovorC().contains("ILI")) {
                        String[] parts = pp.getOdgovorC().split("ILI");
                        for (String s : parts) {
                            if (s.equalsIgnoreCase(mojOdg3.getText().toString())) {
                                ikonica3.setChecked(true);
                                mojOdg3.setBackgroundColor(Color.parseColor("#4d91e3"));
                            } else {
                                tocanNapomena3.setText("Točan odgovor je: " + pp.getOdgovorC() + "\nNapomena: Odgovor nije dodan u statistku.");
                                mojOdg3.setBackgroundColor(Color.parseColor("#AAAAAA"));
                            }
                        }
                    } else {

                        if (mojOdg3.getText().toString().equalsIgnoreCase(pp.getOdgovorC())) {
                            ikonica3.setChecked(true);
                            mojOdg3.setBackgroundColor(Color.parseColor("#4d91e3"));
                        } else {
                            tocanNapomena3.setText("Točan odgovor je: " + pp.getOdgovorC() + "\nNapomena: Odgovor nije dodan u statistku.");
                            mojOdg3.setBackgroundColor(Color.parseColor("#AAAAAA"));
                        }
                    }
                }
            }catch (Exception e)
            {e.printStackTrace();}
        }
        else if(view.getId() == R.id.buttonOcijeni4)
        {
            try {
                if (!mojOdg4.getText().toString().equalsIgnoreCase("")) {
                    if (pp.getOdgovorD().contains("ILI")) {
                        String[] parts = pp.getOdgovorD().split("ILI");
                        for (String s : parts) {
                            if (s.equalsIgnoreCase(mojOdg4.getText().toString())) {
                                ikonica4.setChecked(true);
                                mojOdg4.setBackgroundColor(Color.parseColor("#4d91e3"));
                            } else {
                                tocanNapomena4.setText("Točan odgovor je: " + pp.getOdgovorD() + "\nNapomena: Odgovor nije dodan u statistku.");
                                mojOdg4.setBackgroundColor(Color.parseColor("#AAAAAA"));
                            }
                        }
                    } else {

                        if (mojOdg4.getText().toString().equalsIgnoreCase(pp.getOdgovorD())) {
                            ikonica4.setChecked(true);
                            mojOdg4.setBackgroundColor(Color.parseColor("#4d91e3"));
                        } else {
                            tocanNapomena4.setText("Točan odgovor je: " + pp.getOdgovorD() + "\nNapomena: Odgovor nije dodan u statistku.");
                            mojOdg4.setBackgroundColor(Color.parseColor("#AAAAAA"));
                        }
                    }
                }
            } catch (Exception e)
            {e.printStackTrace();}
        }

    }

    public static void ocijeniZadatak(ArrayList<TextView> odgovori, String tocan) {
        try {
            for (TextView v : odgovori) {
                v.setClickable(false);
                if (v.getText().equals(tocan)) {
                    v.setBackgroundColor(Color.parseColor("#141F4B"));
                    v.setTextColor(Color.WHITE);
                }
                else
                    v.setBackgroundColor(Color.parseColor("#AAAAAA"));
            }
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}

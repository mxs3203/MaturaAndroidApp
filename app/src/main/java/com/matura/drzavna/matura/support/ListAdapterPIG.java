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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.ispit_controllers.IspitActivity;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.SocioloijaPitanje;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by mateosokac on 3/21/17.
 */

public class ListAdapterPIG extends ArrayAdapter<Pig_Pitanja> implements View.OnClickListener {

    List<Pig_Pitanja> items;
    Button nastavi, predaj;
    int start,end;
    Activity a;
    TextView mojOdg;
    String table_name,godina;

    Realm realm;

    public ListAdapterPIG(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterPIG(Context context, int resource, List<Pig_Pitanja> items, int start, int end, String table_name, String godina, Activity a) {
        super(context, resource);
        this.items = items;
        this.start = start;
        this.end = end;
        this.a=a;
        this.table_name = table_name;
        this.godina = godina;



        Realm.init(context);
        realm = DatabaseHelper.resetRealm();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = convertView;
        int vrsta_pitanja =0;

        final Pig_Pitanja p = items.get(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if(p.isTocno_netocno() != null) {
                v = vi.inflate(R.layout.pitanje_tocno_netocno, null);
                vrsta_pitanja = 1;
            }
            else if(p.getOdgovorA() == null || p.getOdgovorC() == null || p.getOdgovorB() == null) {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                vrsta_pitanja = 2;
                final TextView tocanNapomena = (TextView)v.findViewById(R.id.textViewNapomenaTocan);
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
                predaj = (Button)v.findViewById(R.id.buttonOcijeni);
                predaj.setVisibility(View.VISIBLE);
                predaj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mojOdg.getText().toString().equalsIgnoreCase("") ) {
                            if (p.getTocno().contains(",")) {
                                String[] parts = p.getTocno().split(",");
                                for (String s : parts) {
                                    String sss = s.trim();
                                    if (sss.equalsIgnoreCase(mojOdg.getText().toString().trim())) {
                                        mojOdg.setBackgroundColor(Color.parseColor("#141F4B"));
                                    } else {
                                        tocanNapomena.setText("Točan odgovor je: " + p.getTocno() + "\nNapomena: Odgovor nije dodan u statistku.");
                                        mojOdg.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                    }
                                }
                            } else {

                                if (mojOdg.getText().toString().equalsIgnoreCase(p.getTocno())) {
                                    mojOdg.setBackgroundColor(Color.parseColor("#141F4B"));
                                } else {
                                    tocanNapomena.setText("Točan odgovor je: " + p.getTocno() + "\nNapomena: Odgovor nije dodan u statistku.");
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
                    in.putExtra("end", end);
                    in.putExtra("rok", p.getRok());
                    in.putExtra("razina", p.getRazina());
                    a.finish();
                    getContext().startActivity(in);

                }
            });

            System.out.println(position + ":::" + items.size());

            final TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);

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
                setupABC(p, v, position);
            }
            else if(vrsta_pitanja == 2){
                pitanje.setText(p.getPitanje().replaceAll("_____", "..."));
            }
            else if(vrsta_pitanja == 1){
                pitanje.setText(p.getPitanje());
                final TextView tocno = (TextView) v.findViewById(R.id.textViewTocno);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                ikonica.setClickable(false);
                final TextView netocno = (TextView) v.findViewById(R.id.textViewNetocno);
                tocno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tocno.setClickable(false);
                        netocno.setClickable(false);
                        tocno.setAlpha((float) 0.4);
                        if(p.isTocno_netocno().equalsIgnoreCase("1"))
                        {
                            ikonica.setChecked(true);
                            tocno.setBackgroundColor(Color.parseColor("#141F4B"));
                            netocno.setBackgroundColor(Color.parseColor("#AAAAAA"));
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(true, "Tocno", p.getPitanje(), p.isTocno_netocno(),
                                            DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                    realm.insertOrUpdate(s);
                                }
                            });
                        }
                        else
                        {
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(false, "Ne tocno", p.getPitanje(), p.isTocno_netocno(),
                                            DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                    realm.insertOrUpdate(s);
                                }
                            });
                            tocno.setBackgroundColor(Color.parseColor("#AAAAAA"));
                            netocno.setBackgroundColor(Color.parseColor("#141F4B"));
                        }
                    }
                });
                netocno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tocno.setClickable(false);
                        netocno.setClickable(false);
                        netocno.setAlpha((float) 0.4);
                        if(p.isTocno_netocno().equalsIgnoreCase("0"))
                        {
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(true, "Tocno", p.getPitanje(), p.isTocno_netocno(),
                                            DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                    realm.insertOrUpdate(s);
                                }
                            });
                            ikonica.setChecked(true);
                            netocno.setBackgroundColor(Color.parseColor("#141F4B"));
                            tocno.setBackgroundColor(Color.parseColor("#AAAAAA"));
                        }
                        else
                        {
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(false, "Ne Tocno", p.getPitanje(), p.isTocno_netocno(),
                                            DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                    realm.insertOrUpdate(s);
                                }
                            });
                            netocno.setBackgroundColor(Color.parseColor("#AAAAAA"));
                            tocno.setBackgroundColor(Color.parseColor("#141F4B"));
                        }
                    }
                });
            }

        }



        return v;
    }

    private void setupABC(final Pig_Pitanja p, View v, final int position) {
        final ArrayList<TextView> odgovori = new ArrayList<>();

        if (p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null) {
            final TextView odgovorA = (TextView) v.findViewById(R.id.textViewOdgovorA);
            odgovori.add(odgovorA);
            final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
            odgovorA.setText(p.getOdgovorA());
            odgovorA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorA.setAlpha((float) 0.5);
                    ocijeniZadatak(odgovori, p.getTocno());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocno()), odgovorA.getText().toString(), p.getPitanje(), p.getTocno(),
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
                    ocijeniZadatak(odgovori, p.getTocno());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorB.getText().toString().equalsIgnoreCase(p.getTocno()), odgovorB.getText().toString(), p.getPitanje(), p.getTocno(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
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
                   ocijeniZadatak(odgovori, p.getTocno());
                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorC.getText().toString().equalsIgnoreCase(p.getTocno()), odgovorC.getText().toString(), p.getPitanje(), p.getTocno(),
                                    DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                            realm.insertOrUpdate(s);
                        }
                    });
                }
            });

            TextView odgovorD = (TextView) v.findViewById(R.id.textViewOdgovorD);

            TextView slovoD = (TextView) v.findViewById(R.id.odgDSlovo);
            odgovorD.setVisibility(View.GONE);
            slovoD.setVisibility(View.GONE);

        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Pig_Pitanja getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(Pig_Pitanja item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {
        view.setBackgroundResource(R.drawable.odgovor_border);
    }

    private void ocijeniZadatak(ArrayList<TextView> odgovori, String tocan)
    {
        for(TextView v : odgovori)
        {
            if(v.getText().toString().equalsIgnoreCase(tocan))
                v.setBackgroundColor(Color.parseColor("#141F4B"));
            else
                v.setBackgroundColor(Color.parseColor("#AAAAAA"));
        }
    }
}

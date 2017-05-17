package com.matura.drzavna.matura.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by mateosokac on 3/31/17.
 */

public class ListAdapterKnjizevnostPitanja extends ArrayAdapter<KnjizevnostPitanja> implements View.OnClickListener{
    List<KnjizevnostPitanja> items;
    Button nastavi, predaj;
    int start,end;
    String table_name,godina;
    TextView mojOdg;
    Activity a;
    Realm realm;

    public ListAdapterKnjizevnostPitanja(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterKnjizevnostPitanja(Context context, int resource, List<KnjizevnostPitanja> items, int start, int end, String table_name, String godina, Activity a) {
        super(context, resource);
        this.items = items;
        this.start = start;
        this.a=a;
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
        final KnjizevnostPitanja p = items.get(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());

            if(p.getOdgovorA() == null || p.getOdgovorC() == null || p.getOdgovorB() == null) {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                vrsta_pitanja = 2;
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                ikonica.setClickable(false);
                predaj = (Button)v.findViewById(R.id.buttonOcijeni);
                predaj.setVisibility(View.VISIBLE);
                predaj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mojOdg.getText().toString().equalsIgnoreCase("") ) {
                            if (p.getTocan().contains(",")) {
                                String[] parts = p.getTocan().split(",");
                                for (String s : parts) {
                                    if (s.equalsIgnoreCase(mojOdg.getText().toString())) {
                                        ikonica.setChecked(true);
                                        mojOdg.setBackgroundColor(Color.parseColor("#4d91e3"));
                                    } else {
                                        Toast.makeText(getContext(), "Točan odgovor je: " + p.getTocan(), Toast.LENGTH_LONG).show();
                                        mojOdg.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                    }
                                }
                            } else {

                                if (mojOdg.getText().toString().equalsIgnoreCase(p.getTocan())) {
                                    ikonica.setChecked(true);
                                    mojOdg.setBackgroundColor(Color.parseColor("#4d91e3"));
                                } else {
                                    Toast.makeText(getContext(), "Točan odgovor je: " + p.getTocan(), Toast.LENGTH_LONG).show();
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


            TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);

            pitanje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
                            + p.getPitanjeID() + ", " +p.getGodina() +","+ p.getRok() + ","+p.getRazina(), Toast.LENGTH_LONG).show();
                }
            });



            TextView broj_pitanja = (TextView) v.findViewById(R.id.textViewBrojPitanja);
//            broj_pitanja.setText(start + position + 1+  "");
            broj_pitanja.setVisibility(View.GONE);
            if(vrsta_pitanja == 3) {
                pitanje.setText(Html.fromHtml(p.getPitanje()));
                setupABCD(p, v, position);
            }
            else if(vrsta_pitanja == 2){
                pitanje.setText(Html.fromHtml(p.getPitanje()));
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
    public KnjizevnostPitanja getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(KnjizevnostPitanja item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {
        view.setBackgroundResource(R.drawable.odgovor_border);
    }

    public void setupABCD(final KnjizevnostPitanja p, View v, final int position)
    {
        final ArrayList<TextView> odgovori = new ArrayList<>();
        if (p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null && p.getOdgovorD() != null) {

            final TextView odgovorA = (TextView) v.findViewById(R.id.textViewOdgovorA);
            final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
            odgovorA.setText(p.getOdgovorA());
            odgovori.add(odgovorA);
            odgovorA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorA.setAlpha((float) 0.5);
                    ListAdapterPovijest.ocijeniZadatak(odgovori, p.getTocan());
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
            final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
            odgovori.add(odgovorB);
            odgovorB.setText(p.getOdgovorB());
            odgovorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorB.setAlpha((float) 0.5);
                    ListAdapterPovijest.ocijeniZadatak(odgovori, p.getTocan());
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
                    ListAdapterPovijest.ocijeniZadatak(odgovori, p.getTocan());
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
                   ListAdapterPovijest.ocijeniZadatak(odgovori, p.getTocan());
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
}

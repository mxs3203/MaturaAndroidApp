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

import com.matura.drzavna.matura.DrzavnaMaturaMainMenu;
import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.ispit_controllers.IspitActivity;
import com.matura.drzavna.matura.models.EngleskiPitanje;
import com.matura.drzavna.matura.models.KnjizevnostTekst;
import com.matura.drzavna.matura.models.Statistika;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by mateosokac on 4/15/17.
 */

public class ListAdapterEngleski extends ArrayAdapter<EngleskiPitanje> implements View.OnClickListener {
    List<EngleskiPitanje> items;
    Button nastavi, predaj;
    int start, end;
    Realm realm;
    TextView mojOdg;
    String table_name, godina;
    Activity a;


    public ListAdapterEngleski(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterEngleski(Context context, int resource, List<EngleskiPitanje> items, int start, int end, String table_name, String godina, Activity a) {
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
        final EngleskiPitanje p = items.get(position);


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());

            System.out.println("VRSTA PITANJA: "+p.getVrsta_pitanja());

            if(p.getVrsta_pitanja() == 1)
            {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                vrsta_pitanja = 1;
            }
            else if (p.getVrsta_pitanja() == 2) {
                v = vi.inflate(R.layout.pitanje, null);
                vrsta_pitanja = 2;
            } else if (p.getVrsta_pitanja() == 3) {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                ikonica.setClickable(false);
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
                predaj = (Button)v.findViewById(R.id.buttonOcijeni);
                predaj.setVisibility(View.VISIBLE);
               try {
                   predaj.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if (!mojOdg.getText().toString().equalsIgnoreCase("")) {
                               if (p.getTocan().contains(",")) {
                                   String[] parts = p.getTocan().split(",");
                                   for (String s : parts) {
                                       String sss = s.trim();
                                       if (sss.equalsIgnoreCase(mojOdg.getText().toString().trim())) {
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
               }catch (Exception e )
               {
                   e.printStackTrace();
               }
                vrsta_pitanja = 3;
            }
            else if (p.getVrsta_pitanja() == 4) {
                v = vi.inflate(R.layout.pitanje, null);
                vrsta_pitanja = 4;
            }
            else if (p.getVrsta_pitanja() == 5) {
                v = vi.inflate(R.layout.pitanje_nadopuna, null);
                vrsta_pitanja = 5;
            }
            else
            {
                Intent is = new Intent(getContext(), DrzavnaMaturaMainMenu.class);
                getContext().startActivity(is);
            }
        }


        if (p != null) {

            System.out.println("items size" + items.size());
            System.out.println(items.get(items.size() - 1).getPitanje());


            TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);
            TextView koja_praznina = (TextView) v.findViewById(R.id.textViewPitanjeText);

            pitanje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
//                            + p.getPitanje_id() + ", " + p.getGodina() + "," + p.getRok() + "," + p.getRazina(), Toast.LENGTH_LONG).show();
                }
            });


            TextView broj_pitanja = (TextView) v.findViewById(R.id.textViewBrojPitanja);
            broj_pitanja.setText(start + position + 1 + "");

            try {

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

                if(vrsta_pitanja == 1)
                {
                    pitanje.setText(p.getPitanje());
                    String pitanje_t = p.getPitanje().trim();
                    koja_praznina.setTextColor(Color.BLACK);
                    koja_praznina.setText("Nadopuna " + pitanje_t.substring(p.getPitanje().length() - 5, pitanje_t.length()));
                }
                else if (vrsta_pitanja == 2) {
                    pitanje.setText(p.getPitanje());
                    setupABCD(p, v, position);
                } else if (vrsta_pitanja == 3) {
                    pitanje.setText(p.getPitanje());
                    String pitanje_t = p.getPitanje().trim();
                    koja_praznina.setTextColor(Color.BLACK);
                    koja_praznina.setText("Nadopuna " + pitanje_t.substring(p.getPitanje().length() - 5, pitanje_t.length()));
                } else if (vrsta_pitanja == 4) {
                    pitanje.setText(p.getPitanje());
                    String pitanje_t = p.getPitanje().trim();
                    koja_praznina.setTextColor(Color.BLACK);
                    koja_praznina.setText("Nadopuna " + pitanje_t.substring(p.getPitanje().length() - 5, pitanje_t.length()));
                    setupABCD(p, v, position);
                } else if (vrsta_pitanja == 5) {
                    pitanje.setText(p.getPitanje().substring(p.getPitanje().length() - 5, p.getPitanje().length() - 1));
                    String pitanje_t = pitanje.getText().toString().trim();
                    koja_praznina.setTextColor(Color.BLACK);
                    koja_praznina.setText("Nadopuna " + pitanje_t.substring(p.getPitanje().length() - 5, pitanje_t.length()));
                }
                else
                {
                    pitanje.setText(p.getPitanje());
                }
            }catch(StringIndexOutOfBoundsException e)
            {
                //Toast.makeText(getContext(), "Error inserting question...", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
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
    public EngleskiPitanje getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(EngleskiPitanje item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {
        view.setBackgroundResource(R.drawable.odgovor_border);
    }

    public void setupABCD(final EngleskiPitanje p, View v, final int position) {
        final ArrayList<TextView> odgovori = new ArrayList<TextView>();

        if (p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null) {
            final TextView odgovorA = (TextView) v.findViewById(R.id.textViewOdgovorA);
            final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
            odgovori.add(odgovorA);
            odgovorA.setText(p.getOdgovorA());
            odgovorA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorA.setAlpha((float) 0.5);
                    ListAdapterPovijest.ocijeniZadatak(odgovori, p.getTocan());
                    odgovorA.setClickable(false);
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
            odgovorB.setText(p.getOdgovorB());
            odgovori.add(odgovorB);
            odgovorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorB.setAlpha((float) 0.5);
                    odgovorB.setClickable(false);
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
            odgovorC.setText(p.getOdgovorC());
            odgovori.add(odgovorC);
            odgovorC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorC.setAlpha((float) 0.5);
                    odgovorC.setClickable(false);
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
            if(p.getOdgovorD() != null) {

                odgovori.add(odgovorD);
                odgovorD.setText(p.getOdgovorD());
                odgovorD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgovorD.setAlpha((float) 0.5);
                        odgovorD.setClickable(false);
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
            }else {
                slovoD.setVisibility(View.GONE);
                odgovorD.setVisibility(View.GONE);
            }
        }
    }
}

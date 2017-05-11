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

import io.github.kexanie.library.MathView;
import io.realm.Realm;

/**
 * Created by mateosokac on 3/21/17.
 */

public class ListAdapterSociologija extends ArrayAdapter<SocioloijaPitanje> implements View.OnClickListener {

    List<SocioloijaPitanje> items;
    Button nastavi, predaj;
    TextView mojOdg;
    int start,end;
    Activity a;
    String table_name,godina;

    Realm realm;

    public ListAdapterSociologija(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterSociologija(Context context, int resource, List<SocioloijaPitanje> items, int start, int end, String table_name, String godina, Activity a) {
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

        final SocioloijaPitanje p = items.get(position);

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
                    in.putExtra("razina", p.getRazina());
                    in.putExtra("rok", p.getRok());
                    a.finish();
                    getContext().startActivity(in);

                }
            });

            System.out.println(position + ":::" + items.size());

            TextView pitanje = (TextView) v.findViewById(R.id.textViewPitanje);

            pitanje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
                            + p.getPitanje_id() + ", " +p.getGodina() +","+ p.getRok() + ","+p.getRazina(), Toast.LENGTH_LONG).show();
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
                final TextView tocno = (TextView) v.findViewById(R.id.textViewTocno);
                final TextView netocno = (TextView) v.findViewById(R.id.textViewNetocno);
                LinearLayout tocnoNetocno = (LinearLayout)v.findViewById(R.id.tocno_netocno);
                int cnt = 0;
                tocnoNetocno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tocno.getText().equals("Tocno"))
                        {
                            tocno.setBackgroundResource(R.drawable.default_button);
                            tocno.setTextColor(Color.WHITE);
                            netocno.setTextColor(Color.BLACK);
                            netocno.setBackgroundResource(R.drawable.gray_button);
                            tocno.setText("Tocno!");
                        }
                        else {
                            tocno.setBackgroundResource(R.drawable.gray_button);
                            netocno.setBackgroundResource(R.drawable.default_button);
                            netocno.setTextColor(Color.WHITE);
                            tocno.setTextColor(Color.BLACK);
                            tocno.setText("Tocno");
                        }
                    }
                });

                pitanje.setText(p.getPitanje());
            }

        }



        return v;
    }

    private void setupABC(final SocioloijaPitanje p, View v, final int position) {
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
            odgovorC.setText(p.getOdgovorC());
            odgovori.add(odgovorC);
            odgovorC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odgovorC.setAlpha((float) 0.5);
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
            final LinearLayout odgD = (LinearLayout) v.findViewById(R.id.odgovorD);
            if(p.getOdgovorD() != null) {
                odgovorD.setText(p.getOdgovorD());
                odgovori.add(odgovorD);
                odgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgovorD.setAlpha((float) 0.5);
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
            else
            {
                odgD.setVisibility(View.GONE);
                odgovorD.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public SocioloijaPitanje getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(SocioloijaPitanje item) {
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
            v.setClickable(false);
            if(v.getText().toString().equalsIgnoreCase(tocan)) {
                v.setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#141F4B"));
            }
            else
                v.setBackgroundColor(Color.parseColor("#AAAAAA"));
        }
    }
}

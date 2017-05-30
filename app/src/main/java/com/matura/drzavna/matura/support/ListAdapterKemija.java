package com.matura.drzavna.matura.support;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.matura.drzavna.matura.models.FizikaPitanje;
import com.matura.drzavna.matura.models.KemijaPitanje;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;
import io.realm.Realm;

/**
 * Created by mateosokac on 4/10/17.
 */

public class ListAdapterKemija  extends ArrayAdapter<KemijaPitanje> implements View.OnClickListener {
    List<KemijaPitanje> items;
    Button nastavi, predaj;
    int start, end;
    String table_name, godina;
    ProgressDialog dialog;
    EditText mojOdg;
    Realm realm;
    Activity a;

    public ListAdapterKemija(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterKemija(final Context context, int resource, List<KemijaPitanje> items, int start, int end, String table_name, String godina,Activity a) {
        super(context, resource);
        this.items = items;
        this.start = start;
        this.a =a;
        this.end = end;
        this.table_name = table_name;
        this.godina = godina;


        Realm.init(context);
        realm = DatabaseHelper.resetRealm();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = convertView;

        final KemijaPitanje p = items.get(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if(p.getKoliko_pitanja() == 1  && !p.getPitanje1().equalsIgnoreCase("")
                    && p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null )
                v = vi.inflate(R.layout.matematika_pitanje, null);
            else {
                v = vi.inflate(R.layout.matematika_nadopuna_pitanje, null);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                ikonica.setClickable(false);
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
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



            try {
                //1. abcd
                //2. samo nadopuna _____ i samo tocno
                //3. vise pitanja = 1

                SmartImageView slika = (SmartImageView) v.findViewById(R.id.my_image);

                final MathView pitanje = (MathView) v.findViewById(R.id.textViewPitanje);
                final TextView pitanje2 = (TextView) v.findViewById(R.id.textViewPitanje2);
//                pitanje.getSettings().setUseWideViewPort(true);
//                pitanje.destroyDrawingCache();
//                pitanje.setDrawingCacheEnabled(false);
//                pitanje.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                pitanje.getSettings().setLoadWithOverviewMode(true);
//                pitanje.getSettings().setBuiltInZoomControls(true);
//                pitanje.getSettings().setUseWideViewPort(true);
                pitanje.destroyDrawingCache();
                pitanje.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                pitanje.setDrawingCacheEnabled(false);



                TextView broj_pitanja = (TextView) v.findViewById(R.id.textViewBrojPitanja);
                broj_pitanja.setText(start + position + 1 + "");

                broj_pitanja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getContext(), "Pitanje koje nije dobro:  ID="
//                                + p.getPitanje_id() + ", " + p.getGodina() + "," + p.getRok() + "," + p.getRazina(), Toast.LENGTH_LONG).show();
                    }
                });

                if (p.getSlika() != null) {
                    slika.setDrawingCacheEnabled(false);
                    slika.destroyDrawingCache();
                    slika.setImageUrl("http://drzavnamatura.000webhostapp.com/images/kemija/" + p.getSlika() + ".png");
                }

                if (p.getKoliko_pitanja() == 1 && !p.getPitanje1().equalsIgnoreCase("")) {
                    if(p.getPitanje1().contains("$"))
                        pitanje.setText(p.getPitanje1());
                    else
                        pitanje2.setText(p.getPitanje1());
                    setupABCD(p, v, position);
                } else {
                    if(p.getPitanje1().contains("$"))
                        pitanje.setText(removeNewLine(p.getPitanje1().replaceAll("_____", ""), true));
                    else
                        pitanje2.setText(p.getPitanje1().replaceAll("_____", ""));
                }
            }catch (Exception e)
            {
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
    public KemijaPitanje getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(KemijaPitanje item) {
        return super.getPosition(item);
    }

    @Override
    public void onClick(View view) {

    }



    private String removeNewLine(String input, boolean pitanje) {
        try {
            Integer.parseInt(input);
            if (!pitanje)
                return "$$" + input + "$$";
        } catch (Exception e) {
            return input;
        }
        return input;
    }

    private void setupABCD(final KemijaPitanje p, View v, final int position) {
        final ArrayList<MathView> odgovori = new ArrayList<MathView>();

        if (p.getOdgovorA() != null || p.getOdgovorB() != null || p.getOdgovorC() != null || p.getOdgovorD() != null) {
            try {
                final MathView odgovorA = (MathView) v.findViewById(R.id.textViewOdgovorA);
                odgovorA.setLayerType(View.LAYER_TYPE_NONE, null);
                //sa ovim se moze centrirati ali ne radi bas najbolje na ostalim ponudenim odg
                //odgovorA.loadData("<html><head><style type=\"text/css\">body{text-align:center;}</style></head><body>"+removeNewLine(p.getOdgovorA(), false)+"</b‌​ody></html>", "text/html", "utf");
                odgovorA.setText(removeNewLine(p.getOdgovorA(), false));
                odgovorA.setDrawingCacheEnabled(false);
                odgovori.add(odgovorA);
                final LinearLayout odgA = (LinearLayout) v.findViewById(R.id.odgovorA);
                final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
                odgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgA.setAlpha((float) 0.5);
                        odgovorA.setClickable(false);
                        ListAdapterFizika.ocijeniZadatak(odgovori,p.getTocan());
                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                                Statistika s = new Statistika(odgovorA.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorA.getText().toString(), p.getPitanje1(), p.getTocan(),
                                        DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });
                    }
                });


                final MathView odgovorB = (MathView) v.findViewById(R.id.textViewOdgovorB);
                final LinearLayout odgB = (LinearLayout) v.findViewById(R.id.odgovorB);
                final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
                odgovorB.setLayerType(View.LAYER_TYPE_NONE, null);
                odgovorB.setDrawingCacheEnabled(false);
                odgovorB.setText(removeNewLine(p.getOdgovorB(), false));

                odgovori.add(odgovorB);
                odgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgB.setAlpha((float) 0.5);
                        odgovorB.setClickable(false);
                        ListAdapterFizika.ocijeniZadatak(odgovori,p.getTocan());
                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                                Statistika s = new Statistika(odgovorB.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorB.getText().toString(), p.getPitanje1(), p.getTocan(),
                                        DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });
                    }
                });

                final MathView odgovorC = (MathView) v.findViewById(R.id.textViewOdgovorC);
                TextView slovoC = (TextView) v.findViewById(R.id.odgCSlovo);
                final LinearLayout odgC = (LinearLayout) v.findViewById(R.id.odgovorC);
                odgovorC.setLayerType(View.LAYER_TYPE_NONE, null);
                odgovorC.setDrawingCacheEnabled(false);
                odgovorC.setText(removeNewLine(p.getOdgovorC(), false));

                odgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgC.setAlpha((float) 0.5);
                        odgovorC.setClickable(false);
                        ListAdapterFizika.ocijeniZadatak(odgovori,p.getTocan());
                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                                Statistika s = new Statistika(odgovorC.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorC.getText().toString(), p.getPitanje1(), p.getTocan(),
                                        DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });
                    }
                });
                odgovori.add(odgovorC);


                final MathView odgovorD = (MathView) v.findViewById(R.id.textViewOdgovorD);
                TextView slovoD = (TextView) v.findViewById(R.id.odgDSlovo);
                final LinearLayout odgD = (LinearLayout) v.findViewById(R.id.odgovorD);

                if(p.getOdgovorD() != null) {
                    odgovorD.setLayerType(View.LAYER_TYPE_NONE, null);
                    odgovorD.setText(removeNewLine(p.getOdgovorD(), false));
                    odgovorD.setDrawingCacheEnabled(false);
                    odgovori.add(odgovorD);
                    odgD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            odgD.setAlpha((float) 0.5);
                            odgovorD.setClickable(false);
                            ListAdapterFizika.ocijeniZadatak(odgovori, p.getTocan());
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(odgovorD.getText().toString().equalsIgnoreCase(p.getTocan()), odgovorD.getText().toString(), p.getPitanje1(), p.getTocan(),
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

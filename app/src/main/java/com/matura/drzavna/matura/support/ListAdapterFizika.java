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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.matura.drzavna.matura.models.MatematikaPitanje;
import com.matura.drzavna.matura.models.Statistika;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by mateosokac on 3/30/17.
 */

public class ListAdapterFizika extends ArrayAdapter<FizikaPitanje> implements View.OnClickListener  {
    List<FizikaPitanje> items;
    Button nastavi;
    int start, end;
    String table_name, godina;
    Dialog dialog;
    EditText mojOdg;
    Button predaj;
    Realm realm;
    Activity a;
    String URL_IMAGES = "http://drzavnamatura.000webhostapp.com/images/fizika/";


    public ListAdapterFizika(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapterFizika(final Context context, int resource, List<FizikaPitanje> items, int start, int end, String table_name, String godina, Activity a) {
        super(context, resource);
        this.items = items;
        this.a = a;
        this.start = start;
        this.end = end;
        this.table_name = table_name;
        this.godina = godina;


        Realm.init(context);
        realm = DatabaseHelper.resetRealm();


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = convertView;

        final FizikaPitanje p = items.get(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if(!p.getPitanje().contains("_____")
                    && p.getOdgovorA() != null && p.getOdgovorB() != null && p.getOdgovorC() != null )
                v = vi.inflate(R.layout.matematika_pitanje, null);
            else if(p.isSlike_u_odgovoru()) {
                v = vi.inflate(R.layout.fizika_slika_u_odg, null);
            }
            else {
                v = vi.inflate(R.layout.matematika_nadopuna_pitanje, null);
                mojOdg = (EditText)v.findViewById(R.id.mojOdg);
                final CheckBox ikonica = (CheckBox) v.findViewById(R.id.checkBoxTocno);
                final TextView tocanNapomena = (TextView)v.findViewById(R.id.textViewNapomenaTocan);
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
        }


        if (p != null) {

            nastavi = (Button) v.findViewById(R.id.nastavi_pitanja);
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

            //1. abcd
            //2. samo nadopuna _____ i samo tocno
            //3. vise pitanja = 1

            SmartImageView slika = (SmartImageView) v.findViewById(R.id.my_image);

            final MathView pitanje = (MathView) v.findViewById(R.id.textViewPitanje);
            final TextView pitanje2 = (TextView) v.findViewById(R.id.textViewPitanje2);
            pitanje.destroyDrawingCache();
            pitanje.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            pitanje.setDrawingCacheEnabled(false);

            try {

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
                    slika.setImageUrl(URL_IMAGES + p.getSlika() + ".png");
                }

                if (!p.isSlike_u_odgovoru() && !p.getPitanje().contains("_____")) {
                    if(p.getPitanje().contains("$"))
                        pitanje.setText(removeNewLine(removeNewLine(p.getPitanje(), true), false));
                    else
                        pitanje2.setText(p.getPitanje());
                    setupABCD(p, v, position);

                } else if (p.isSlike_u_odgovoru()) {
                    setupABCDSllike(p, v, position);
                } else {
                    if(p.getPitanje().contains("$"))
                        pitanje.setText(removeNewLine(p.getPitanje().replaceAll("_____", ""), true));
                    else
                        pitanje2.setText(p.getPitanje().replaceAll("_____", ""));


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return v;
    }

    private void setupABCDSllike(final FizikaPitanje p, View v, final int position) {
        final ArrayList<SmartImageView> odgovori = new ArrayList<SmartImageView>();


        if (p.getOdgovorA() != null || p.getOdgovorB() != null || p.getOdgovorC() != null || p.getOdgovorD() != null) {
            try {
                final SmartImageView odgovorA = (SmartImageView) v.findViewById(R.id.textViewOdgovorA);
                odgovori.add(odgovorA);
                LinearLayout odgA = (LinearLayout) v.findViewById(R.id.odgovorA);
                final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
                odgovorA.setTag(p.getOdgovorA());
                odgovorA.setImageUrl( URL_IMAGES + p.getOdgovorA() + ".png");
                odgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (odgovorA.getTag().toString().equalsIgnoreCase(p.getTocan()))
                        {

                        }

                    }
                });
                final SmartImageView odgovorB = (SmartImageView) v.findViewById(R.id.textViewOdgovorB);
                odgovori.add(odgovorB);
                LinearLayout odgB = (LinearLayout) v.findViewById(R.id.odgovorB);
                final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
                odgovorB.setTag(p.getOdgovorB());
                odgovorB.setImageUrl(URL_IMAGES + p.getOdgovorB() + ".png");
                odgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (odgovorB.getTag().toString().equalsIgnoreCase(p.getTocan()))
                        {

                        }

                    }
                });

                final SmartImageView odgovorC = (SmartImageView) v.findViewById(R.id.textViewOdgovorC);
                TextView slovoC = (TextView) v.findViewById(R.id.odgCSlovo);
                odgovori.add(odgovorC);
                odgovorC.setTag(p.getOdgovorC());
                LinearLayout odgC = (LinearLayout) v.findViewById(R.id.odgovorC);
                odgovorC.setImageUrl(URL_IMAGES + p.getOdgovorC() + ".png");
                odgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (odgovorC.getTag().toString().equalsIgnoreCase(p.getTocan()))
                        {

                        }
                    }
                });

                final SmartImageView odgovorD = (SmartImageView) v.findViewById(R.id.textViewOdgovorD);
                odgovorD.setTag(p.getOdgovorD());
                odgovori.add(odgovorD);
                TextView slovoD = (TextView) v.findViewById(R.id.odgDSlovo);
                LinearLayout odgD = (LinearLayout) v.findViewById(R.id.odgovorD);
                odgovorD.setImageUrl(URL_IMAGES + p.getOdgovorD() + ".png");
                odgD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (odgovorD.getTag().toString().equalsIgnoreCase(p.getTocan()))
                        {

                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public FizikaPitanje getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPosition(FizikaPitanje item) {
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

    private void setupABCD(final FizikaPitanje p, View v, final int position) {

        final ArrayList<MathView> odgovori = new ArrayList<MathView>();
        if (p.getOdgovorA() != null || p.getOdgovorB() != null || p.getOdgovorC() != null || p.getOdgovorD() != null) {
            try {
                final MathView odgovorA = (MathView) v.findViewById(R.id.textViewOdgovorA);
                LinearLayout odgA = (LinearLayout) v.findViewById(R.id.odgovorA);
                odgovori.add(odgovorA);
                final TextView slovoA = (TextView) v.findViewById(R.id.odgASlovo);
                odgovorA.setLayerType(View.LAYER_TYPE_NONE, null);
                odgovorA.setDrawingCacheEnabled(false);
                odgovorA.setText(removeNewLine(p.getOdgovorA(), false));
                odgA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgovorA.setAlpha((float) 0.5);
                        ocijeniZadatak(odgovori,p.getTocan());
                        odgovorA.setClickable(false);
                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                            Statistika s = new Statistika(odgovorA.getText().equalsIgnoreCase(p.getTocan()), odgovorA.getText(), p.getPitanje(), p.getTocan(),
                                DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });
                    }
                });


                final MathView odgovorB = (MathView) v.findViewById(R.id.textViewOdgovorB);
                LinearLayout odgB = (LinearLayout) v.findViewById(R.id.odgovorB);
                final TextView slovoB = (TextView) v.findViewById(R.id.odgBSlovo);
                odgovori.add(odgovorB);
                odgovorB.setLayerType(View.LAYER_TYPE_NONE, null);
                odgovorB.setText(removeNewLine(p.getOdgovorB(), false));
                odgovorB.setDrawingCacheEnabled(false);
                odgB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgovorB.setAlpha((float) 0.5);
                        ocijeniZadatak(odgovori,p.getTocan());
                        odgovorB.setClickable(false);
                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                        Statistika s = new Statistika(odgovorB.getText().equalsIgnoreCase(p.getTocan()), odgovorB.getText(), p.getPitanje(), p.getTocan(),
                                DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });
                    }
                });


                final MathView odgovorC = (MathView) v.findViewById(R.id.textViewOdgovorC);
                TextView slovoC = (TextView) v.findViewById(R.id.odgCSlovo);
                odgovori.add(odgovorC);
                LinearLayout odgC = (LinearLayout) v.findViewById(R.id.odgovorC);
                odgovorC.setLayerType(View.LAYER_TYPE_NONE, null);
                odgovorC.setDrawingCacheEnabled(false);
                odgovorC.setText(removeNewLine(p.getOdgovorC(), false));
                odgC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odgovorC.setAlpha((float) 0.5);
                        odgovorC.setClickable(false);
                        ocijeniZadatak(odgovori,p.getTocan());

                        realm.executeTransaction(new Realm.Transaction() {
                            public void execute(Realm realm) {
                        Statistika s = new Statistika(odgovorC.getText().equalsIgnoreCase(p.getTocan()), odgovorC.getText(), p.getPitanje(), p.getTocan(),
                                DatabaseHelper.mapTableNameToPresentationValue(table_name),p.getGodina(),p.getRok(), p.getRazina());
                                realm.insertOrUpdate(s);
                            }
                        });

                    }
                });

                final MathView odgovorD = (MathView) v.findViewById(R.id.textViewOdgovorD);
                TextView slovoD = (TextView) v.findViewById(R.id.odgDSlovo);

                if(p.getOdgovorD() != null) {

                    odgovori.add(odgovorD);
                    LinearLayout odgD = (LinearLayout) v.findViewById(R.id.odgovorD);
                    odgovorD.setLayerType(View.LAYER_TYPE_NONE, null);
                    odgovorD.setDrawingCacheEnabled(false);
                    odgovorD.setText(removeNewLine(p.getOdgovorD(), false));
                    odgD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            odgovorD.setAlpha((float) 0.5);
                            odgovorD.setClickable(false);
                            ocijeniZadatak(odgovori, p.getTocan());
                            realm.executeTransaction(new Realm.Transaction() {
                                public void execute(Realm realm) {
                                    Statistika s = new Statistika(odgovorD.getText().equalsIgnoreCase(p.getTocan()), odgovorD.getText(), p.getPitanje(), p.getTocan(),
                                            DatabaseHelper.mapTableNameToPresentationValue(table_name), p.getGodina(), p.getRok(), p.getRazina());
                                    realm.insertOrUpdate(s);
                                }
                            });
                        }
                    });
                }else
                {
                    odgovorD.setVisibility(View.GONE);
                    slovoD.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void ocijeniZadatak(ArrayList<MathView> odgovori, String tocan) {
        try {
            for (MathView v : odgovori) {
                v.setClickable(false);
                if (v.getText().equalsIgnoreCase(tocan)) {
                    v.setBackgroundColor(Color.parseColor("#141F4B"));
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

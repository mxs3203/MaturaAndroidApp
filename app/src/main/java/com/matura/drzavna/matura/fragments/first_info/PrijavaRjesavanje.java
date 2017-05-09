package com.matura.drzavna.matura.fragments.first_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.matura.drzavna.matura.DrzavnaMaturaMainMenu;
import com.matura.drzavna.matura.R;

/**
 * Created by mateosokac on 3/1/17.
 */

public class PrijavaRjesavanje extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prijava_rjesavanje, container, false);
        ImageView img = (ImageView)v.findViewById(R.id.imageViewPrijavi);
        img.setImageResource(R.drawable.pirjava_rjesavanje);

        Button nastavi = (Button)v.findViewById(R.id.buttonNastavi);
        nastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DrzavnaMaturaMainMenu.class);
                startActivity(i);
            }
        });
        return v;
    }
}

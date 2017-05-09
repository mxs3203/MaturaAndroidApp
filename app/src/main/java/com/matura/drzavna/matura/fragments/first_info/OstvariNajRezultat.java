package com.matura.drzavna.matura.fragments.first_info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.matura.drzavna.matura.R;

/**
 * Created by mateosokac on 3/1/17.
 */

public class OstvariNajRezultat extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ostvari_naj_rezultat, container, false);

        ImageView img = (ImageView)v.findViewById(R.id.imageViewOstvariNaj);
        img.setImageResource(R.drawable.ostvari_naj);
        return v;
    }
}

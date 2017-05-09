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

public class PripremiSeZaMaturu extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pripremi_za_maturu, container, false);
        ImageView img = (ImageView)v.findViewById(R.id.imageViewPripremiSe);
        img.setImageResource(R.drawable.pripremi_se);
        return v;
    }
}

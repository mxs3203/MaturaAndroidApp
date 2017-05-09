package com.matura.drzavna.matura.support;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.matura.drzavna.matura.FirstInfo;
import com.matura.drzavna.matura.fragments.first_info.OstvariNajRezultat;
import com.matura.drzavna.matura.fragments.first_info.PrijavaRjesavanje;
import com.matura.drzavna.matura.fragments.first_info.PripremiSeZaMaturu;
/**
 * Created by mateosokac on 11/24/16.
 */

public class FirstInfoFragmentPagerManager extends FragmentStatePagerAdapter
{
    int mNumOfTabs;
    FirstInfo a;
    Context c;

    public FirstInfoFragmentPagerManager(FragmentManager fm, int NumOfTabs, Context c, FirstInfo a) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.c = c;
        this.a = a;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PripremiSeZaMaturu tab1 = new PripremiSeZaMaturu();
                return tab1;
//            case 1:
//                OstvariNajRezultat tab2 = new OstvariNajRezultat();
//                return tab2;
            case 1:
                PrijavaRjesavanje tab3 = new PrijavaRjesavanje();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

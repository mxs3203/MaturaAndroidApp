/*
 * Copyright (c) 2017. MaturaApp
 */

package com.matura.drzavna.matura;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matura.drzavna.matura.models.DostupniIspiti;
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.models.User;
import com.matura.drzavna.matura.support.DatabaseHelper;
import com.matura.drzavna.matura.support.Download;
import com.matura.drzavna.matura.support.FirstInfoFragmentPagerManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;


public class FirstInfo extends FragmentActivity {

    Context c;
    Realm realm;
    RealmList<Ispit> ispitiZaDownload = new RealmList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        c = this;


        setContentView(R.layout.activity_first_info);
        setupTabs();


    }

    @Override
    public void onBackPressed() {


    }

    private void setupTabs() {

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final FirstInfoFragmentPagerManager adapter = new FirstInfoFragmentPagerManager(getSupportFragmentManager(), 2, c, this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setIcon(R.drawable.first_info_blue_dot);
        tabLayout.getTabAt(1).setIcon(R.drawable.first_info_gray_dot);
//        tabLayout.getTabAt(2).setIcon(R.drawable.first_info_gray_dot);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.first_info_blue_dot);
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.first_info_blue_dot);
                }
//                else if (tab.getPosition() == 2) {
//                    tab.setIcon(R.drawable.first_info_blue_dot);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.first_info_gray_dot);
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.first_info_gray_dot);
                }
//                else if (tab.getPosition() == 2) {
//                    tab.setIcon(R.drawable.first_info_gray_dot);
//                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}

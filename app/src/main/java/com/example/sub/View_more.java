package com.example.sub;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class View_more extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AdView mAdView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.view_more );
        LoadQuangCaoBanner();
        final Intent intent = this.getIntent();
        final String Getvietphare= intent.getStringExtra("vphare");
        final String Gethanviet= intent.getStringExtra("vhanviet");


        TabLayout tabLayout = (TabLayout) findViewById( R.id.tab_layout1 );
        tabLayout.addTab( tabLayout.newTab().setText( "Dịch nghĩa" ) );
        tabLayout.addTab( tabLayout.newTab().setText( "Hán việt" ) );
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL );

        final ViewPager viewPager = (ViewPager) findViewById( R.id.page1 );
        final com.example.sub.Page2 adapter = new com.example.sub.Page2(getSupportFragmentManager(), tabLayout.getTabCount() );
        viewPager.setAdapter( adapter );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.setOnTabSelectedListener( new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );

                final EditText text_tab3 = (EditText) findViewById(R.id.Txt_tab3);
                final EditText text_tab4 = (EditText) findViewById(R.id.Txt_tab4);
                text_tab3.setText(Getvietphare);
                text_tab4.setText(Gethanviet);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        } );

    }
    private void LoadQuangCaoBanner (){
        MobileAds.initialize(this, getString(R.string.id_app));
        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}

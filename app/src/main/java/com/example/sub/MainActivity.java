package com.example.sub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sub.Go_web;
import com.example.sub.R;
import com.example.sub.View_more;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //khai bao GUI
     WebView webview;
     Button btdich,clear;
   private   EditText text_vi,text_cn,text_hanviet;
    private static final String TAG = MainActivity.class.getSimpleName();
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        try {
            LoadQuangCaoBanner();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1 );
            tabLayout.addTab(tabLayout.newTab().setText("Dịch nghĩa"));
            tabLayout.addTab(tabLayout.newTab().setText("Hán việt"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new com.example.sub.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener( new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            System.out.println("loi " + e.getMessage());
        }
        //
        btdich = findViewById(R.id.bt_dich);
        clear = findViewById(R.id.bt_clear);
        text_vi = (EditText) findViewById(R.id.Txt_VN);
        text_cn = (EditText) findViewById(R.id.Text_china);
        text_hanviet = (EditText) findViewById(R.id.Txt_hanviet);

        btdich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ed_text = text_cn.getText().toString().trim();
                    if (ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals( "" )) {
                        Toast.makeText( MainActivity.this,
                                "Chưa nhập text!", Toast.LENGTH_SHORT ).show();
                    } else {
                        Getpost();
                        Getpost_Hanviet();
                        webview = (WebView) findViewById( R.id.webview );
                        webview.setWebViewClient( new WebViewClient() );
                        webview.getSettings().setJavaScriptEnabled( true );
                        openURL();
                    }
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }
            }

        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                text_vi = (EditText) findViewById(R.id.Txt_VN);
                text_cn = (EditText) findViewById(R.id.Text_china);
                text_hanviet = (EditText) findViewById(R.id.Txt_hanviet);
                        text_vi.getText().clear();
                        text_cn.getText().clear();
                        text_hanviet.getText().clear();
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText text_vi = (EditText) findViewById(R.id.Txt_VN);
                final EditText text_hanviet = (EditText) findViewById(R.id.Txt_hanviet);

                Intent intent = new
                        Intent(MainActivity.this, View_more.class);
                String vphare = text_vi.getText().toString();
                String vhanviet = text_hanviet.getText().toString();
                intent.putExtra("vphare",vphare);
                intent.putExtra("vhanviet",vhanviet);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(MainActivity.this, Go_web.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void  Getpost()
    {
        final RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final EditText text_vi = (EditText) findViewById(R.id.Txt_VN);
        final EditText text_cn = (EditText) findViewById(R.id.Text_china);
        final String Textchina = text_cn.getText().toString().trim();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://vietphrase.info//Vietphrase/TranslateVietPhraseS",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text_vi.setText(response);
                        Toast.makeText(MainActivity.this,
                                "Dịch Thành công!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Chưa nhập text hoặc, vui lòng kiểm tra lại kết nối của bạn!", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> postParam = new HashMap<String, String>();

                postParam.put("chineseContent", Textchina);

                return postParam;
            }

        };

        queue.add(jsonObjRequest);

    }
    private void LoadQuangCaoBanner (){
        MobileAds.initialize(this, getString(R.string.id_app));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void  Getpost_Hanviet()
    {
        final RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final EditText text_hanviet = (EditText) findViewById(R.id.Txt_hanviet);
        final EditText text_cn = (EditText) findViewById(R.id.Text_china);
        final String Textchina = text_cn.getText().toString().trim();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://vietphrase.info//Vietphrase/TranslateHanViet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text_hanviet.setText(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> postParam = new HashMap<String, String>();

                postParam.put("chineseContent", Textchina);

                return postParam;
            }

        };

        queue.add(jsonObjRequest);

    }

    private void openURL() {
        webview.loadUrl("https://trickgame24h.blogspot.com/p/chuyentrang.html");
        webview.requestFocus();
    }
}

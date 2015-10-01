package com.nautilus.demo.mywebapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.home) {

            Bundle bundle = new Bundle();
            bundle.putString("type", "file");
            bundle.putString("url", "home.html");
            fragment = new FragmentWeb();
            fragment.setArguments(bundle);

        } else if (id == R.id.about_us) {

            Bundle bundle = new Bundle();
            bundle.putString("type", "file");
            bundle.putString("url", "about_us.html");
            fragment = new FragmentWeb();
            fragment.setArguments(bundle);

        } else if (id == R.id.our_services) {

            Bundle bundle = new Bundle();
            bundle.putString("type", "file");
            bundle.putString("url", "services.html");
            fragment = new FragmentWeb();
            fragment.setArguments(bundle);

        } else if (id == R.id.contacts) {

            Bundle bundle = new Bundle();
            bundle.putString("type", "file");
            bundle.putString("url", "contacts.html");
            fragment = new FragmentWeb();
            fragment.setArguments(bundle);

        }

        // ##################### --------------- EXAMPLE ----------------------- #################

        else if (id == R.id.nav_1) {

                // ---------------------------------  Load WebiView with Local URL -------------------- //
                Bundle bundle = new Bundle();
                bundle.putString("type", "file");
                bundle.putString("url", "local_file.html");
                fragment = new FragmentWeb();
                fragment.setArguments(bundle);

            } else if (id == R.id.nav_2) {
                // ---------------------------------  Load WebiView with Remote URL -------------------- //
                Bundle bundle = new Bundle();
                bundle.putString("type", "url");
                bundle.putString("url", "http://www.w3schools.com/");
                fragment = new FragmentWeb();
                fragment.setArguments(bundle);

            } else if (id == R.id.nav_3) {
                // ---------------------------------  Load WebiView with Remote URL -------------------- //
                Bundle bundle = new Bundle();
                bundle.putString("type", "file");
                bundle.putString("url", "interactive.html");
                fragment = new FragmentWebInteractive();
                fragment.setArguments(bundle);

            } else if (id == R.id.nav_4) {
            // ---------------------------------  Load WebiView with Remote URL -------------------- //
            Bundle bundle = new Bundle();
            bundle.putString("type", "file");
            bundle.putString("url", "credits.html");
            fragment = new FragmentWebInteractive();
            fragment.setArguments(bundle);

        }



        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

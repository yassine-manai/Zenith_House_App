package dev.mobile.zenithhouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bt_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.home)
                    {
                        selectedFragment = new Home();
                    }

                    if (item.getItemId() == R.id.garden)
                    {
                        selectedFragment = new Garden();
                    }

                    if (item.getItemId() == R.id.garage)
                    {
                        selectedFragment = new Garage();
                    }


                    if (selectedFragment != null)
                    {
                        String URL="http://172.16.13.122:80/";
                        Bundle bundle = new Bundle();

                        bundle.putString("url", URL);

                        selectedFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.addplaceholer, selectedFragment)
                                .commit();
                    }

                    return true;
                }
            };
}
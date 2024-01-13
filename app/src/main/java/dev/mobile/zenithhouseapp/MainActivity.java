package dev.mobile.zenithhouseapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        loadFragment(new Home());
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
                        Bundle bundle = new Bundle();
                        selectedFragment.setArguments(bundle);

                        // Load the selected fragment
                        loadFragment(selectedFragment);
                        return true;
                    }

                    return false;
                }
            };


private void loadFragment(Fragment fragment)
{
        // Replace the current fragment with the selected one
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.addplaceholer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        }
}

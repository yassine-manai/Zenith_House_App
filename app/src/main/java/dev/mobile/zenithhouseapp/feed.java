package dev.mobile.zenithhouseapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class feed extends AppCompatActivity
{
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        bottomNavigationView = findViewById(R.id.btnavretro);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigListener);

        // Load the default fragment
        loadFragment(new AddFeed());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.addfeed)
                    {
                        selectedFragment = new AddFeed();
                    }

                    if (item.getItemId() == R.id.listfeed)
                    {
                        selectedFragment = new ListerFragment();
                    }

                    if (item.getItemId() == R.id.updatefeed)
                    {
                        selectedFragment = new UpdateFeed();
                    }

                    if (item.getItemId() == R.id.deletefeed)
                    {
                        selectedFragment = new DeleteFeed();
                    }

                    if (selectedFragment != null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", "https://liger-divine-surely.ngrok-free.app/ZHA:80");
                        selectedFragment.setArguments(bundle);

                        loadFragment(selectedFragment);
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            };

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("url", "https://liger-divine-surely.ngrok-free.app/ZHA:80");
        fragment.setArguments(bundle);

        transaction.replace(R.id.navretroadd, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

package dev.mobile.zenithhouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class feed extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        bottomNavigationView = findViewById(R.id.btnavretro);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigListener);

        // Load the default fragment
        loadFragment(new AddFeed());
    }

    private OnNavigationItemSelectedListener navigListener =
            new OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                    if (selectedFragment != null) {
                        // Pass data to the fragment using a bundle
                        Bundle bundle = new Bundle();
                        bundle.putString("url", "https://196.203.63.40:80");
                        selectedFragment.setArguments(bundle);

                        // Load the selected fragment
                        loadFragment(selectedFragment);
                        return true;
                    }

                    return false;
                }
            };

    private void loadFragment(Fragment fragment) {
        // Replace the current fragment with the selected one
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.navretroadd, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

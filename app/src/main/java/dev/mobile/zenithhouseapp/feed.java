package dev.mobile.zenithhouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class feed extends AppCompatActivity {
    BottomNavigationView btm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btm = findViewById(R.id.btnavretro);
        btm.setOnNavigationItemSelectedListener(navigListener);
    }

    private OnNavigationItemSelectedListener navigListener =
            new OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.addfrag)
                    {
                        selectedFragment = new AddFeed();
                    }


                    if (item.getItemId() == R.id.list)
                    {
                        selectedFragment = new ListerFragment();
                    }


                    if (item.getItemId() == R.id.update)
                    {
                        selectedFragment = new UpdateFeed();
                    }

                    if (item.getItemId() == R.id.delete)
                    {
                        selectedFragment = new DeleteFeed();
                    }




                    if (selectedFragment != null)
                    {
                        String URL="http://172.16.12.153:80";
                        Bundle bundle = new Bundle();

                        bundle.putString("url", URL);

                        selectedFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navretroadd, selectedFragment)
                                .commit();
                    }

                    return true;
                }
            };
}

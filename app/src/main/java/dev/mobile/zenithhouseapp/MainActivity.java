package dev.mobile.zenithhouseapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

        if (!isLoggedIn())
        {
            Intent loginIntent = new Intent(MainActivity.this, activity_Login.class);
            startActivity(loginIntent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bt_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigListener);

        loadFragment(new Home());
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                    if (item.getItemId() == R.id.maps)
                    {
                        selectedFragment = new Maps();
                    }

                    if (item.getItemId() == R.id.logout)
                    {
                        dialogdeconnect();
                        return true;
                    }

                    if (selectedFragment != null)
                    {
                        loadFragment(selectedFragment);
                        return true;
                    }

                    return false;
                }
            };

    private void dialogdeconnect()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logoapp);
        builder.setTitle("Se Déconnecter ? ")
                .setPositiveButton("Décoonecter", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        logout();
                    }
                })
                .setNegativeButton("Annuler ", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void logout()
    {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent loginIntent = new Intent(MainActivity.this, activity_Login.class);
        startActivity(loginIntent);
        finish();
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.addplaceholer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

package dev.mobile.zenithhouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import dev.mobile.zenithhouseapp.databinding.ActivityReminderHomeBinding;


public class Reminder_Home extends AppCompatActivity implements View.OnClickListener
{
    private ActivityReminderHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityReminderHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnFragAjout.setOnClickListener(this);
        binding.btnFragLister.setOnClickListener(this);
        binding.btnFragModif.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.equals(binding.btnFragAjout))
        {
            loadFragment(new Ajout());
        }

        if(view.equals(binding.btnFragLister))
        {
            loadFragment(new lister());
        }

        if(view.equals(binding.btnFragModif))
        {
            loadFragment(new Modifier());
        }

    }

    private void loadFragment(Fragment fragment)
    {
        Bundle bundle = new Bundle();
        ContactBDD db = new ContactBDD(Reminder_Home.this);
        bundle.putSerializable("Clé", db);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
    }
}

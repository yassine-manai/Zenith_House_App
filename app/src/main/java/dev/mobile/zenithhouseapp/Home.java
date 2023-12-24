package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.mobile.zenithhouseapp.databinding.ActivityMainBinding;
import dev.mobile.zenithhouseapp.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeBinding Binding;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mStatHome,mStatR1,mStatR2,mStatR3;

    private static final String TAG = "MainActivity";

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2)
    {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = Binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance("https://zha-1-430a4-default-rtdb.firebaseio.com");

        mStatHome = mDatabase.getReference().child("Home");
        mStatR1 = mDatabase.getReference().child("Room1");
        mStatR2 = mDatabase.getReference().child("Room2");
        mStatR3 = mDatabase.getReference().child("Room3");

        String reds = "#D3212C";
        String oranges = "#FF980E";
        String greens = "#069C56";

        int red = Color.parseColor(reds);
        int orange = Color.parseColor(oranges);
        int green = Color.parseColor(greens);


        Binding.reminder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Reminder_Home.class);
                startActivity(intent);
            }
        });

        Binding.feed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), feed.class);
                startActivity(intent);
            }
        });

        //Température
        mStatHome.child("Temp").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                float temperature = dataSnapshot.getValue(float.class);

                    Binding.TempHome.setText(temperature + " °C");

                    if (temperature<30)
                    {
                        Binding.cardtemp.setBackgroundColor(green);
                    }

                    if (temperature>31)
                    {
                        Binding.cardtemp.setBackgroundColor(red);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        //Humidité
        mStatHome.child("Humid").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int Humid = dataSnapshot.getValue(int.class);

                    Binding.HumidHome.setText(Humid + " %");

                if (Humid<20)
                {
                    Binding.cardhumid.setBackgroundColor(green);
                }

                if ((Humid>20) && (Humid<40))
                {
                    Binding.cardhumid.setBackgroundColor(orange);
                }

                if (Humid>40)
                {
                    Binding.cardhumid.setBackgroundColor(red);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        mStatHome.child("wifi").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String wifi = dataSnapshot.getValue(String.class);
                if (wifi != null)
                {
                    if (wifi.equals("ON"))
                    {
                        Binding.wifi.setText("Wifi Connecté");
                        Binding.wifiimg.setImageResource(R.drawable.wifion);

                    }
                    if (wifi.equals("OFF"))
                    {
                        Binding.wifi.setText("Wifi Non Connecté ");
                        Binding.wifiimg.setImageResource(R.drawable.wifioff);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        mStatHome.child("FB").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String firebase = dataSnapshot.getValue(String.class);
                if (firebase != null)
                {
                    if (firebase.equals("ON"))
                    {
                        Binding.firebase.setText("Firebase Connecté");
                        Binding.firebaseimg.setImageResource(R.drawable.serverconenct);

                    }
                    if (firebase.equals("OFF"))
                    {
                        Binding.firebase.setText("Firebase Non Connecté ");
                        Binding.firebaseimg.setImageResource(R.drawable.servererror);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        setSwitchChangeListener(Binding.switch1, Binding.switchImage1, "Led", mStatR1);
        setSwitchChangeListenerac(Binding.acr1Switch, Binding.acr1, "AC", mStatR1);

        setSwitchChangeListener(Binding.switch2, Binding.switchImage2, "Led", mStatR2);
        setSwitchChangeListenerac(Binding.acr2Switch, Binding.acr2, "AC", mStatR2);

        setSwitchChangeListener(Binding.switch3, Binding.switchImage3, "Led", mStatR3);
        setSwitchChangeListenerac(Binding.acr3Switch, Binding.acr3, "AC", mStatR3);

        return view;
    }

    private void setSwitchChangeListener(final Switch switchView, final ImageView switchImageView, final String switchName, final DatabaseReference roomReference)
    {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                updateFirebase(roomReference, switchName, isChecked);
                updateSwitchUI(switchView, switchImageView, isChecked);
            }
        });
    }

    private void setSwitchChangeListenerac(final Switch switchView, final ImageView switchImageView, final String switchName, final DatabaseReference roomReference)
    {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                updateFirebase(roomReference, switchName, isChecked);
                updateSwitchac(switchView, switchImageView, isChecked);
            }
        });
    }

    private void updateFirebase(DatabaseReference roomReference, String switchName, boolean isChecked)
    {
        roomReference.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateSwitchUI(Switch switchView, ImageView switchImageView, boolean isChecked)
    {
        if (isChecked)
        {
            switchView.setText("Light ON");
            switchImageView.setImageResource(R.drawable.bulb);
        }
        else
        {
            switchView.setText("Light OFF");
            switchImageView.setImageResource(R.drawable.lightbulb);
        }
    }

    private void updateSwitchac(Switch switchView, ImageView switchImageView, boolean isChecked)
    {
        if (isChecked)
        {
            switchView.setText("Climatisseur Activée");
            switchImageView.setImageResource(R.drawable.climon);
        }
        else
        {
            switchView.setText("Climatisseur Désactivée");
            switchImageView.setImageResource(R.drawable.climoff);
        }
    }
}
package dev.mobile.zenithhouseapp;

import android.content.Intent;
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

        Binding.reminder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Reminder_Home.class);
                startActivity(intent);
            }
        });

        //Température
        mStatHome.child("Temp").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String temperature = dataSnapshot.getValue(String.class);
                if (temperature != null) {
                    Binding.TempHome.setText(temperature + " °C");
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
                String Humid = dataSnapshot.getValue(String.class);
                if (Humid != null)
                {
                    Binding.HumidHome.setText(Humid + " %");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });






        return view;
    }


    private void setSwitchChangeListener(final Switch switchView, final ImageView switchImageView, final String switchName)
    {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                updateFirebaseR1(switchName, isChecked);
                updateSwitchUI(switchView, switchImageView, isChecked);
            }
        });
    }


    private void setSwitchChangeListenerac(final Switch switchView, final ImageView switchImageView, final String switchName)
    {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                updateFirebaseac1(switchName, isChecked);
                updateSwitchac(switchView, switchImageView, isChecked);
            }
        });
    }

    private void updateFirebaseR1(String switchName, boolean isChecked)
    {
        mStatR1.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateFirebaseR2(String switchName, boolean isChecked)
    {
        mStatR2.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateFirebaseR3(String switchName, boolean isChecked)
    {
        mStatR3.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateFirebaseac1(String switchName, boolean isChecked)
    {
        mStatR1.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateFirebaseac2(String switchName, boolean isChecked)
    {
        mStatR1.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateFirebaseac3(String switchName, boolean isChecked)
    {
        mStatR1.child(switchName).setValue(isChecked ? "ON" : "OFF");
    }

    private void updateSwitchUI(Switch switchView, ImageView switchImageView, boolean isChecked)
    {
        if (isChecked)
        {
            // Switch is checked (ON)
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.setText("Light ON");
            switchImageView.setImageResource(R.drawable.bulb);
        }
        else
        {
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.white));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.white));
            switchView.setText("Light OFF");
            switchImageView.setImageResource(R.drawable.lightbulb);
        }
    }

    private void updateSwitchac(Switch switchView, ImageView switchImageView, boolean isChecked)
    {
        if (isChecked)
        {
            // Switch is checked (ON)
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.setText("AC ON");
            switchImageView.setImageResource(R.drawable.ac);
        }
        else
        {
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.white));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.white));
            switchView.setText("AC OFF");
            switchImageView.setImageResource(R.drawable.ac);
        }
    }

}

package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.mobile.zenithhouseapp.databinding.FragmentGardenBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Garden#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Garden extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentGardenBinding Bind;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mStatusReference;
    private static final String TAG = "Garden";

    public Garden()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Garden.
     */
    // TODO: Rename and change types and number of parameters
    public static Garden newInstance(String param1, String param2)
    {
        Garden fragment = new Garden();
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
        Bind = FragmentGardenBinding.inflate(getLayoutInflater());
        View view = Bind.getRoot();

        mDatabase = FirebaseDatabase.getInstance("https://zha2-cb6f9-default-rtdb.firebaseio.com/");
        mStatusReference = mDatabase.getReference().child("Home");

        String reds = "#D3212C";
        String oranges = "#FF980E";
        String greens = "#069C56";

        int red = Color.parseColor(reds);
        int orange = Color.parseColor(oranges);
        int green = Color.parseColor(greens);

        Bind.remindergarden.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Reminder_Home.class);
                startActivity(intent);
            }
        });


        mStatusReference.child("Temp").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                float temperature = dataSnapshot.getValue(float.class);

                    Bind.TempHome.setText(temperature + " °C");

                if (temperature<30)
                {
                    Bind.tempgardencard.setBackgroundColor(green);
                }

                if (temperature>31)
                {
                    Bind.tempgardencard.setBackgroundColor(red);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        mStatusReference.child("Humid").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int Humid = dataSnapshot.getValue(int.class);

                    Bind.humidgarden.setText(Humid + " %");

                if (Humid<20)
                {
                    Bind.humdgardencard.setBackgroundColor(green);
                }

                if ((Humid>20) && (Humid<40))
                {
                    Bind.humdgardencard.setBackgroundColor(orange);
                }

                if (Humid>40)
                {
                    Bind.humdgardencard.setBackgroundColor(red);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle error
            }
        });

        mStatusReference.child("Hsol").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int Humidsol = dataSnapshot.getValue(int.class);

                    Bind.TempGarden.setText(Humidsol + " %");

                if (Humidsol<20)
                {
                    Bind.humsolcard.setBackgroundColor(green);
                }

                if ((Humidsol>20) && (Humidsol<40))
                {
                    Bind.humsolcard.setBackgroundColor(orange);
                }

                if (Humidsol>40)
                {
                    Bind.humsolcard.setBackgroundColor(red);
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
}


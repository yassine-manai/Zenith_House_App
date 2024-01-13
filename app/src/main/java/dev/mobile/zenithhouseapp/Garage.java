package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.mobile.zenithhouseapp.databinding.FragmentGarageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Garage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Garage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentGarageBinding Bind;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mStatusReference;
    private static final String TAG = "Garage";

    public Garage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Garage.
     */
    // TODO: Rename and change types and number of parameters
    public static Garage newInstance(String param1, String param2) {
        Garage fragment = new Garage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bind = FragmentGarageBinding.inflate(getLayoutInflater());
        View view = Bind.getRoot();


        mDatabase = FirebaseDatabase.getInstance("https://zha2-cb6f9-default-rtdb.firebaseio.com/");
        mStatusReference = mDatabase.getReference().child("Garage");

        setSwitchChangeListener(Bind.parkSwitch, Bind.garageOpen, "Garage");

        mStatusReference.child("GP").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String place = dataSnapshot.getValue(String.class);
                if (place != null)
                {
                    if ("Free".equals(place))
                    {
                        Bind.carImage.setVisibility(View.INVISIBLE);
                        Bind.Gate.setText("Status: Garage VIDE");
                    }

                    if ("Full".equals(place))
                    {
                        Bind.carImage.setVisibility(View.VISIBLE);
                        Bind.Gate.setText("Status: Garage PLEIN");
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        Bind.reminderpark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent start = new Intent(getActivity(), Reminder_Home.class);
                startActivity(start);
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
                updateFirebase(switchName, isChecked);
                updateSwitchUI(switchView, switchImageView, isChecked);
            }
        });
    }

    private void updateFirebase(String switchName, boolean isChecked)
    {
        mStatusReference.child(switchName).setValue(isChecked ? "OPEN" : "CLOSED");
    }

    private void updateSwitchUI(Switch switchView, ImageView switchImageView, boolean isChecked)
    {
        if (isChecked)
        {
            // Switch is checked (ON)
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.primaryColor));
            switchView.setText("Garage Open");
            switchImageView.setImageResource(R.drawable.go);
        }
        else
        {
            switchView.getThumbDrawable().setTint(getResources().getColor(R.color.white));
            switchView.getTrackDrawable().setTint(getResources().getColor(R.color.white));
            switchView.setText("Garage Closed");
            switchImageView.setImageResource(R.drawable.gc);
        }
    }
}

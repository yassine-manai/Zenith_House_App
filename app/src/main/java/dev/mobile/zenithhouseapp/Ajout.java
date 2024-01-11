package dev.mobile.zenithhouseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ajout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ajout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText editNom,editTxt;
    Button Ajouter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ajout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ajout.
     */
    // TODO: Rename and change types and number of parameters
    public static Ajout newInstance(String param1, String param2) {
        Ajout fragment = new Ajout();
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

        View v = inflater.inflate(R.layout.fragment_ajout, container, false);
        editNom = v.findViewById(R.id.editNomFrag);
        editTxt = v.findViewById(R.id.editText);
        Ajouter = v.findViewById(R.id.btnFragAjouter);

        Ajouter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (editNom.getText().toString().equals(""))
                {
                    editNom.setError("Invalide !");
                    editNom.requestFocus();
                    return;
                }

                if (editTxt.getText().toString().equals(""))
                {
                    editTxt.setError("Invalide Text !");
                    editTxt.requestFocus();
                    return;
                }


                noteBDD bd = (noteBDD) getArguments().getSerializable("Clé");
                note nt = new note(editNom.getText().toString(), editTxt.getText().toString());

                long rslt = bd.addnotes(nt);

                if (rslt != -1)
                {
                    Toast.makeText(getActivity().getApplication(), "Ajout avec Succés", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity().getApplication(), "Probléme d'ajout", Toast.LENGTH_LONG).show();

                }

            }

        });

        // Inflate the layout for this fragment
        return v;
    }
}
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
 * Use the {@link Modifier#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Modifier extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Modifier() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Modifier.
     */
    // TODO: Rename and change types and number of parameters
    public static Modifier newInstance(String param1, String param2) {
        Modifier fragment = new Modifier();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifier, container, false);

        EditText editId = view.findViewById(R.id.editid);
        EditText editNom = view.findViewById(R.id.editnom);
        EditText editNum = view.findViewById(R.id.editnum);
        Button modifyButton = view.findViewById(R.id.modify);

        ContactBDD db = new ContactBDD(getActivity());

        editId.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Contact C = db.searchContact(Integer.parseInt(editId.getText().toString()));

                        if (C != null)
                        {
                            editNom.setText(C.getNom());
                            editNum.setText(C.getNumber());
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Contact Introvable", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int contactId = Integer.parseInt(editId.getText().toString());
                String contactName = editNom.getText().toString();
                String contactNumber = editNum.getText().toString();

                Contact contact = new Contact(contactName, contactNumber);
                contact.setId(contactId);

                int result = db.updateContact(contact);

                if (result != 0)
                {
                    Toast.makeText(getActivity(), "Mise a jour avec succées . . . ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Mise a jour échoué", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}
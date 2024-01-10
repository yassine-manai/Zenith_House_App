package dev.mobile.zenithhouseapp;

import static java.lang.System.err;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFeed extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText idf,nom_add,phn_add,suggest_add;
    private Button BtnAdd;
    private TextView err;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFeed newInstance(String param1, String param2) {
        AddFeed fragment = new AddFeed();
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


        View v = inflater.inflate(R.layout.fragment_add_feed, container, false);

        nom_add=v.findViewById(R.id.et_nom_add);
        phn_add= v.findViewById(R.id.et_username_add);
        suggest_add=v.findViewById(R.id.et_password_add);
        BtnAdd=v.findViewById(R.id.btnAddUser);
        err=v.findViewById(R.id.error);

        BtnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addFeed();
            }
        });

        return v;
    }

    private void addFeed()
    {
        String name = nom_add.getText().toString().trim();
        String number = phn_add.getText().toString().trim();
        String feed =suggest_add.getText().toString().trim();

        String URL= getArguments().getString("url", "");
        Retrofit Rf = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiHandler api = Rf.create(ApiHandler.class);
        Call<feeds> addfeed = api.insertfeeds(name, number, feed);

        addfeed.enqueue(new Callback<feeds>()
        {
            @Override
            public void onResponse (Response<feeds> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    Toast.makeText(getActivity(),"Fedds added", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure (Throwable t)
            {
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                err.setText(t.getLocalizedMessage());
            }
        });
    }

}
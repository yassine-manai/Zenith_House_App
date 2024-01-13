package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AddFeed extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText idf, nom_add, phn_add, suggest_add;
    private Button BtnAdd;
    private TextView err;
    private ImageView back;

    private String mParam1;
    private String mParam2;

    public AddFeed() {
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_feed, container, false);

        idf = v.findViewById(R.id.et_id_add);
        nom_add = v.findViewById(R.id.et_nom_add);
        phn_add = v.findViewById(R.id.et_username_add);
        suggest_add = v.findViewById(R.id.et_password_add);
        BtnAdd = v.findViewById(R.id.btnAddUser);
        err = v.findViewById(R.id.error);
        back = v.findViewById(R.id.back);

        BtnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addFeed();
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent home = new Intent(getActivity(), MainActivity.class);
                startActivity(home);
            }
        });

        return v;
    }

    private void addFeed()
    {
        String idStr = idf.getText().toString().trim();
        String name = nom_add.getText().toString().trim();
        String number = phn_add.getText().toString().trim();
        String feed = suggest_add.getText().toString().trim();

        if (TextUtils.isEmpty(idStr) || TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(feed))
        {
            Toast.makeText(getActivity(), "Les Champs sont obligatoires . . .", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);

        String URL = getArguments().getString("url", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHandler api = retrofit.create(ApiHandler.class);

        FeedRequestBody requestBody = new FeedRequestBody(id, name, number, feed);

        Call<feeds> addfeed = api.insertfeeds(requestBody);

        addfeed.enqueue(new Callback<feeds>()
        {
            @Override
            public void onResponse(Response<feeds> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    if (response.body() != null)
                    {
                        Toast.makeText(getActivity(), "Feeds added", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Feed nt added", Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(Throwable t)
            {
                Toast.makeText(getActivity(), "Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                err.setText(t.getLocalizedMessage());
            }
        });
    }
}

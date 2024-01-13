package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.os.Bundle;
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

public class UpdateFeed extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText id, name, phone, feed;
    private Button btnUpdate;
    private TextView updateError;

    private String mParam1;
    private ImageView back;

    private String mParam2;

    public UpdateFeed()
    {
    }

    public static UpdateFeed newInstance(String param1, String param2)
    {
        UpdateFeed fragment = new UpdateFeed();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_update_feed, container, false);

        id = v.findViewById(R.id.et_user_id);
        name = v.findViewById(R.id.et_updated_name);
        phone = v.findViewById(R.id.et_updated_username);
        feed = v.findViewById(R.id.et_updated_email);
        btnUpdate = v.findViewById(R.id.btnUpdateUser);
        back = v.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent home = new Intent(getActivity(), MainActivity.class);
                startActivity(home);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        return v;
    }

    private void updateUser()
    {
        String idf = id.getText().toString().trim();
        String namef = name.getText().toString().trim();
        String phonef = phone.getText().toString().trim();
        String ffed = feed.getText().toString().trim();

        if (idf.isEmpty())
        {
            Toast.makeText(getActivity(), "Please enter a Feed ID", Toast.LENGTH_SHORT).show();
            return;
        }

        FeedRequestBody requestBody = new FeedRequestBody(Integer.parseInt(idf), namef, phonef, ffed);

        String URL = getArguments().getString("url", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiHandler api = retrofit.create(ApiHandler.class);
        Call<feeds> updateUserCall = api.updatetfeeds(requestBody);

        updateUserCall.enqueue(new Callback<feeds>()
        {
            @Override
            public void onResponse(Response<feeds> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    Toast.makeText(getActivity(), "Feed updated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Update failed: ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Toast.makeText(getActivity(), "Update failed: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                updateError.setText(t.getLocalizedMessage());
            }
        });
    }
}

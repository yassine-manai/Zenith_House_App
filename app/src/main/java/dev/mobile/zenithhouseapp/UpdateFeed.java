package dev.mobile.zenithhouseapp;

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
 * Use the {@link UpdateFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFeed extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText id, name, phone, feed;
    private Button btnUpdate;
    private TextView updateError;

    public UpdateFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFeed newInstance(String param1, String param2) {
        UpdateFeed fragment = new UpdateFeed();
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
        View v = inflater.inflate(R.layout.fragment_update_feed, container, false);

        id = v.findViewById(R.id.et_user_id);
        name = v.findViewById(R.id.et_updated_name);
        phone = v.findViewById(R.id.et_updated_username);
        feed = v.findViewById(R.id.et_updated_email);
        btnUpdate = v.findViewById(R.id.btnUpdateUser);
        updateError = v.findViewById(R.id.update_error);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        return v;
    }

    private void updateUser() {
        String idf = id.getText().toString().trim();
        String namef = name.getText().toString().trim();
        String phonef = phone.getText().toString().trim();
        String ffed = feed.getText().toString().trim();

        // Ensure ID is not empty
        if (idf.isEmpty())
        {
            Toast.makeText(getActivity(), "Please enter a Feed ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String URL = getArguments().getString("url", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiHandler api = retrofit.create(ApiHandler.class);
        Call<feeds> updateUserCall = api.updatetfeeds(Integer.parseInt(idf), namef, phonef, ffed);

        updateUserCall.enqueue(new Callback<feeds>()
        {
            @Override
            public void onResponse(Response<feeds> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    Toast.makeText(getActivity(), "User updated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show();
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
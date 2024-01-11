package dev.mobile.zenithhouseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFeed extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText feedIdToDelete;
    private Button btnDeletefeed;
    private TextView deleteError;

    public DeleteFeed() {
        // Required empty public constructor
    }

    public static DeleteFeed newInstance(String param1, String param2) {
        DeleteFeed fragment = new DeleteFeed();
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
        View v = inflater.inflate(R.layout.fragment_delete_feed, container, false);

        feedIdToDelete = v.findViewById(R.id.et_user_id_to_delete);
        btnDeletefeed = v.findViewById(R.id.btnDeleteUser);
        deleteError = v.findViewById(R.id.delete_error);

        btnDeletefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        return v;
    }

    private void deleteUser() {
        String id = feedIdToDelete.getText().toString().trim();

        // Ensure ID is not empty
        if (id.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a Feed ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve URL from arguments
        String URL = getArguments().getString("url", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiHandler api = retrofit.create(ApiHandler.class);
        FeedIdRequestBody requestBody = new FeedIdRequestBody(Integer.parseInt(id));

        Call<Void> deleteUserCall = api.deletefeeds(requestBody);

        deleteUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(getActivity(), "Feed deleted", Toast.LENGTH_LONG).show();
                    // You might want to clear the ID field or perform any other UI update here
                } else {
                    Toast.makeText(getActivity(), "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Delete failed: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                deleteError.setText(t.getLocalizedMessage());
            }
        });
    }
}

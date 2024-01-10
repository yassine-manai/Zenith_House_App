package dev.mobile.zenithhouseapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListerFragment extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewUser;
    private RecyclerView.LayoutManager layoutManager;

    public ListerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListerFragment newInstance(String param1, String param2) {
        ListerFragment fragment = new ListerFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lister, container, false);

        recyclerViewUser = v.findViewById(R.id.feed_recycleview);
        layoutManager = new LinearLayoutManager(getActivity());

        // Retrieve URL from arguments
        String URL = getArguments().getString("url", "");

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API service
        ApiHandler api = retrofit.create(ApiHandler.class);

        // Make a network request to get the list of users
        Call<List<feeds>> userListCall = api.getAllfeeds();
        userListCall.enqueue(new Callback<List<feeds>>()
        {
            @Override
            public void onResponse(Response<List<feeds>> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    List<feeds> listfeeds = response.body();

                    FeedAdapter userAdapter = new FeedAdapter(getActivity(), listfeeds);
                    recyclerViewUser.setAdapter(userAdapter);
                    recyclerViewUser.setLayoutManager(layoutManager);



                    // Set up RecyclerView


                    if(getActivity() != null)
                    {
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                        recyclerViewUser.addItemDecoration(dividerItemDecoration);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Toast.makeText(getActivity(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        return v;
    }
}

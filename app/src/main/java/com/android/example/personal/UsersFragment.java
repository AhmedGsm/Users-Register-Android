package com.android.example.personal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView  mRecyclerView;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private PersonAdapter mAdapter;
    private static final String TAG = UsersFragment.class.getSimpleName() ;

    public UsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersFragment.
     */
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind Butter knife to fragment
        ButterKnife.bind(this,view);

        // Setup recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PersonAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        // Use Retrofit to register a user
        final RegistrationInterface retrofit =
                RetrofitClient.buildRetrofit().create(RegistrationInterface.class);
        Call<List<Person>> call = retrofit.getPersons();
        call.enqueue(new Callback<List<Person>>() {

            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                // Logging response message
                Log.i(TAG, response.message());
                if(response.body() == null ) {
                    Toast.makeText(getContext(),R.string.errorReadData,Toast.LENGTH_SHORT).show();
                   return;
                }
                // If no user registered
                if(response.body().get(0).getMassage() != null) {
                    Toast.makeText(getContext(),R.string.noUserRegistered,Toast.LENGTH_SHORT).show();
                    return;
                }
                // Populate users ids & emails in recycler view..
                mAdapter.swapPersons(response.body());
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                // Logging error message
                if(t == null)return;
                Log.e(TAG, t.getMessage());
                Toast.makeText(getContext(),R.string.errorReadData,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

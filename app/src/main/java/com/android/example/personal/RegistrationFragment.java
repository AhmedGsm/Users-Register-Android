package com.android.example.personal;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    @BindView(R.id.registerBtn)Button registerButton;
    @BindView(R.id.nameEditText) EditText nameEdit;
    @BindView(R.id.emailEditText) EditText emailEdit;
    @BindView(R.id.passwordEditText) EditText passwordEdit;
    @BindView(R.id.ageEditText) EditText ageEdit;
    @BindView(R.id.occupationEditText) EditText occupationEdit;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind Butterknife to fragment
        ButterKnife.bind(this,view);
        // Use Retrofit to register a user
        final RegistrationInterface retrofit =
                RetrofitClient.buildRetrofit().create(RegistrationInterface.class);

        // Insert person informations to remote database when click register button
        registerButton.setOnClickListener(view1 -> {
            // Disable button when clicking
            registerButton.setEnabled(false);
            registerButton.setText(R.string.registeringStr);
            // Read values from edit texts
            final String name = nameEdit.getText().toString();
            final String email = emailEdit.getText().toString();
            final String password = passwordEdit.getText().toString();
            final String age = ageEdit.getText().toString();
            final String occupation = occupationEdit.getText().toString();
            Call<Person> call = retrofit.insertPerson(name,email,password,age,occupation);
            call.enqueue(new Callback<Person>() {
                private String TAG = RegistrationFragment.class.getSimpleName();
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    // On registering success clear all edit text, and enable register button
                    registerButton.setEnabled(true);
                    registerButton.setText(R.string.registerBtn);
                    nameEdit.setText("");
                    ageEdit.setText("");
                    emailEdit.setText("");
                    occupationEdit.setText("");
                    passwordEdit.setText("");
                    // Log the response message
                    Log.w(TAG ,"response,errorBody "  + response.errorBody());
                    assert response.body() != null;
                    Log.w(TAG ,"response,getMassage"  + response.body().getMassage());
                    // Display the registration state for user in a message Toast
                    if(response.body().getValue().equals("0") ) {
                        Toast.makeText(getContext(),R.string.errorRegisteringStr, Toast.LENGTH_LONG).show();
                    } else if(response.body().getValue().equals("1")) {
                        Toast.makeText(getContext(), R.string.successRegisteringStr, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.w(TAG ,"response onFailure "  + t.getMessage());
                }
            });

        });
    }
}

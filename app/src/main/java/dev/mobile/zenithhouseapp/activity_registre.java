package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.mobile.zenithhouseapp.databinding.ActivityRegistreBinding;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class activity_registre extends AppCompatActivity {

    ActivityRegistreBinding Binding;
    ApiHandler apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Binding = ActivityRegistreBinding.inflate(getLayoutInflater());
        View view = Binding.getRoot();
        setContentView(view);

        Binding.Show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Binding.Show.setVisibility(View.INVISIBLE);
                Binding.Hide.setVisibility(View.VISIBLE);
                Binding.PassRegEdit.setTransformationMethod(null);

            }
        });

        Binding.Hide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Binding.Show.setVisibility(View.VISIBLE);
                Binding.Hide.setVisibility(View.INVISIBLE);
                Binding.PassRegEdit.setTransformationMethod(new PasswordTransformationMethod());

            }
        });

        Binding.Show2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Binding.Show2.setVisibility(View.INVISIBLE);
                Binding.Hide2.setVisibility(View.VISIBLE);
                Binding.PassConfRegEdit.setTransformationMethod(null);

            }
        });

        Binding.Hide2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Binding.Show2.setVisibility(View.VISIBLE);
                Binding.Hide2.setVisibility(View.INVISIBLE);
                Binding.PassConfRegEdit.setTransformationMethod(new PasswordTransformationMethod());

            }
        });

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d98b-196-176-164-36.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create ApiService instance
        apiService = retrofit.create(ApiHandler.class);

        Binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });

        Binding.logRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(activity_registre.this, activity_Login.class);
                startActivity(start);
            }
        });
    }

    private void handleRegistration() {
        if (validateInput()) {
            saveUserToServer(new User(
                    Binding.NameRegEdit.getText().toString(),
                    Binding.EmailRegEdit.getText().toString(),
                    Binding.PassRegEdit.getText().toString()
            ));
        }
    }

    private boolean validateInput()
    {
        if (Binding.NameRegEdit.getText().toString().isEmpty()) {
            Binding.NameRegEdit.setError("REQUIRED !");
            return false;
        }

        if (Binding.EmailRegEdit.getText().toString().isEmpty()) {
            Binding.EmailRegEdit.setError("REQUIRED !");
            return false;
        }

        if (Binding.PassRegEdit.getText().toString().isEmpty()) {
            Binding.PassRegEdit.setError("REQUIRED !");
            return false;
        }

        if (Binding.PassConfRegEdit.getText().toString().isEmpty()) {
            Binding.PassConfRegEdit.setError("REQUIRED !");
            return false;
        }

        if (!Binding.PassRegEdit.getText().toString().equals(Binding.PassConfRegEdit.getText().toString())) {
            Binding.PassRegEdit.setError("Password doesn't match!");
            Binding.PassConfRegEdit.setError("Password doesn't match!");
            return false;
        }

        return true;
    }

    private void saveUserToServer(User user) {
        Call<Void> call = apiService.insertuser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(activity_registre.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    Intent nextAct = new Intent(activity_registre.this, activity_Login.class);
                    startActivity(nextAct);
                } else {
                    Log.d("Error", "Failed to add user. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Error", "Failed to add user. " + t.getMessage());
            }
        });
    }
}

package dev.mobile.zenithhouseapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dev.mobile.zenithhouseapp.databinding.ActivityLoginBinding;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class activity_Login extends AppCompatActivity
{

    private ActivityLoginBinding Bind;
    private ApiHandler apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bind = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = Bind.getRoot();
        setContentView(view);

        Bind.LShow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bind.LShow.setVisibility(View.INVISIBLE);
                Bind.LHide.setVisibility(View.VISIBLE);
                Bind.PassLogEdit.setTransformationMethod(null);
            }
        });

        Bind.LHide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bind.LShow.setVisibility(View.VISIBLE);
                Bind.LHide.setVisibility(View.INVISIBLE);
                Bind.PassLogEdit.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://liger-divine-surely.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiHandler.class);

        Bind.btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleLogin();
            }
        });

        Bind.logRegBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent start = new Intent(activity_Login.this, activity_registre.class);
                startActivity(start);
            }
        });
    }

    private void handleLogin()
    {
        if (valide())
        {
            loginUser(
                    Bind.EmailEditLogin.getText().toString(),
                    Bind.PassLogEdit.getText().toString()
            );
        }
    }

    private boolean valide()
    {
        if (Bind.EmailEditLogin.getText().toString().isEmpty())
        {
            Bind.EmailEditLogin.setError("REQUIRED !");
            return false;
        }

        if (Bind.PassLogEdit.getText().toString().isEmpty())
        {
            Bind.PassLogEdit.setError("REQUIRED !");
            return false;
        }

        return true;
    }

    private void loginUser(String email, String password)
    {
        Call<List<User>> call = apiService.loginUser(email, password);

        call.enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit)
            {
                if (response.isSuccess() && response.body() != null)
                {
                    List<User> userList = response.body();

                    if (!userList.isEmpty())
                    {
                        for (User user : userList)
                        {
                            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                            {
                                saveLoginStatus(true);
                                Toast.makeText(activity_Login.this, "W E L C O M E", Toast.LENGTH_LONG).show();
                                Intent start = new Intent(activity_Login.this, MainActivity.class);
                                startActivity(start);
                                finish();
                                return;
                            }
                        }
                        Toast.makeText(activity_Login.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(activity_Login.this, "User Not Found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(activity_Login.this, "Failed to fetch user data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Toast.makeText(activity_Login.this, "Failed to login. " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Retrofit", "Failed to execute API request", t);
            }
        });

    }

    private void saveLoginStatus(boolean log)
    {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", log);
        editor.apply();
    }
}

package dev.mobile.zenithhouseapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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
    private AlertDialog progressDialog;
    private TextView dialogText;

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
                Login();
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

    private void Login()
    {
        if (validate())
        {
            showProgressDialog();
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    loginUser(
                            Bind.EmailEditLogin.getText().toString(),
                            Bind.PassLogEdit.getText().toString()
                    );
                }
            }, 1000);
        }
    }

    private boolean validate()
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
                dismissProgressDialog();
                if (response.isSuccess() && response.body() != null)
                {
                    List<User> userList = response.body();

                    if (!userList.isEmpty())
                    {
                        int userListSize = userList.size();

                        for (int i = 0; i < userListSize; i++)
                        {
                            User user = userList.get(i);
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
                dismissProgressDialog();
                Toast.makeText(activity_Login.this, "Failed to login. " + t.getMessage(), Toast.LENGTH_LONG).show();
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



    private void showProgressDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null);
        dialogText = view.findViewById(R.id.dialogText);
        builder.setView(view);
        progressDialog = builder.create();
        progressDialog.show();

        dialogText.setText("Connexion en cours ...");
    }

    private void dismissProgressDialog()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}

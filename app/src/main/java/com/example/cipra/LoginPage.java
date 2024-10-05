package com.example.cipra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {
    private Button signIn;
    private TextInputEditText userEmail,userPassword;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initViews();
        inintActions();
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cipra.ai:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void inintActions() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEmail.getText().toString();
                String userpassword = userPassword.getText().toString();
                if(!username.isEmpty()&&!userpassword.isEmpty()){
                    VerifyUserCredentials(username,userpassword);
                }
            }
        });
    }

    private void VerifyUserCredentials(String username, String userpassword) {
    System.out.println("username = "+ username);
    System.out.println("userpassword = "+userpassword);

    RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Void> call = retrofitInterface.login(username,userpassword);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Logged in sucessfully", Toast.LENGTH_SHORT).show();
                    NavigateToNext();
                }else{
                    Toast.makeText(LoginPage.this, "Credentials Missmatch", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(LoginPage.this, "Network Issue encountered", Toast.LENGTH_SHORT).show();
                System.out.println("network issue encountred = "+ throwable.getLocalizedMessage());
            }
        });
    }

    private void NavigateToNext() {
        Intent intent = new Intent(LoginPage.this,MainActivity.class);
        startActivity(intent);
    }

    private void initViews() {
        signIn = findViewById(R.id.SignIn_btn);
        userEmail = findViewById(R.id.userEmail_input);
        userPassword = findViewById(R.id.userPassword_input);
    }
}
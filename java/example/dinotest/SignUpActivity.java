package example.dinotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import example.dinotest.entity.User;
import example.dinotest.repository.UserRepository;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp0;
    EditText etUsername;
    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp0 = (Button) findViewById(R.id.btnSignUp0);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignUp0.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp0:
                User user = new User(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                UserRepository.signUp(user);
                finish();
                break;
        }

    }
}

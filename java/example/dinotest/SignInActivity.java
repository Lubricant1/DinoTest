package example.dinotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.dinotest.entity.Session;
import example.dinotest.entity.User;
import example.dinotest.repository.UserRepository;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etLogin;
    EditText etLogPass;
    Button btnSignIn0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etLogPass = (EditText) findViewById(R.id.etLogPass);
        btnSignIn0 = (Button) findViewById(R.id.btnSignIn0);
        btnSignIn0.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent0 = null;
        switch (v.getId()) {
            case R.id.btnSignIn0:
                User user = new User();
                user.setName(etLogin.getText().toString());
                user.setPassword(etLogPass.getText().toString());
                Session session = UserRepository.login(user);
                if (session != null) {
                    intent0 = new Intent(this, DinoActivity.class);
                    intent0.putExtra("id", session.getId());
                    intent0.putExtra("name", session.getName());
                    intent0.putExtra("token", session.getToken());
                    intent0.putExtra("user", session.getUser());
                    startActivity(intent0);
                    finish();
                } else
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show();
                break;
        }

    }
}

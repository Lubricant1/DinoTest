package example.dinotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent0 = null;
        switch (v.getId()) {
            case R.id.btnSignIn:
                intent0 = new Intent(this, SignInActivity.class);
                break;
            case R.id.btnSignUp:
                intent0 = new Intent(this, SignUpActivity.class);
                break;
        }
        startActivity(intent0);
    }
}

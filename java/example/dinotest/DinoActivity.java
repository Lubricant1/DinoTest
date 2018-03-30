package example.dinotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import example.dinotest.entity.Session;
import example.dinotest.repository.UserRepository;

public class DinoActivity extends AppCompatActivity implements View.OnClickListener {

    Session session;
    TextView tvUserName;
    Button btnLogout;
    Button btnDinoList;
    Button btnDinoAdd;
    String cookie;
    String token;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino);

        Intent intent = getIntent();
        session = new Session(intent.getStringExtra("name"), intent.getStringExtra("id"), intent.getStringExtra("token"), intent.getStringExtra("user"));
        cookie = intent.getStringExtra("name") + "=" + intent.getStringExtra("id");
        token = intent.getStringExtra("token");
        user = intent.getStringExtra("user");

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnDinoAdd = (Button) findViewById(R.id.btnDinoAdd);
        btnDinoList = (Button) findViewById(R.id.btnDinoList);
        btnLogout.setOnClickListener(this);
        btnDinoAdd.setOnClickListener(this);
        btnDinoList.setOnClickListener(this);
        tvUserName.setText(session.getUser());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                UserRepository.logout(session);
                finish();
                break;
            case R.id.btnDinoList:
                Intent intent0 = null;
                intent0 = new Intent(this, DinoListActivity.class);
                intent0.putExtra("cookie", cookie);
                intent0.putExtra("token", token);
                intent0.putExtra("user", user);
                startActivity(intent0);
                break;
            case R.id.btnDinoAdd:
                Intent intent1 = null;
                intent1 = new Intent(this, DinoAddActivity.class);
                intent1.putExtra("cookie", cookie);
                intent1.putExtra("token", token);
                intent1.putExtra("user", user);
                startActivity(intent1);
                break;
        }
    }
}

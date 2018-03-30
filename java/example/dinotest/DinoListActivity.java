package example.dinotest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.dinotest.entity.DinoResp;
import example.dinotest.repository.DinoRepository;

public class DinoListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    ListView lvDinoList;
    List<Map<String, Object>> data;
    List<DinoResp> dinos;
    ExtendedAdapter eAdapter;
    SwipeRefreshLayout refreshLayout;
    Button btnAddDino;

    String cookie;
    String token;
    String user;

    String[] from = {"dino_title", "dino_color", "dino_birthdate", "dino_about", "dino_image"};
    int[] to = {R.id.tvItemTitle, R.id.tvItemColor, R.id.tvItemBirthdate, R.id.tvItemAbout, R.id.imgItemImg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_list);

        Intent intent = getIntent();
        cookie = intent.getStringExtra("cookie");
        token = intent.getStringExtra("token");
        user = intent.getStringExtra("user");

        btnAddDino = (Button) findViewById(R.id.btnAddDino);
        btnAddDino.setOnClickListener(this);
        lvDinoList = (ListView) findViewById(R.id.lvDinoList);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_dark, android.R.color.holo_green_dark);

        fillList();

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        Toast.makeText(this, "Updating Dino List", Toast.LENGTH_SHORT).show();
        fillList();
        refreshLayout.setRefreshing(false);
    }

    void fillList() {
        dinos = DinoRepository.getDinos();
        data = new ArrayList<Map<String, Object>>();
        Map<String, Object> m;
        for (int i = 0; i < dinos.size(); i++) {
            m = new HashMap<String, Object>();
            m.put("dino_title", dinos.get(i).getName());
            m.put("dino_color", dinos.get(i).getColor());
            m.put("dino_birthdate", dinos.get(i).getBirthdate());
            m.put("dino_about", dinos.get(i).getAbout());
            m.put("dino_image", dinos.get(i).getImgURL());
            data.add(m);
        }
        eAdapter = new ExtendedAdapter(this, data, R.layout.dino_list_item, from, to);
        lvDinoList.setAdapter(eAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddDino:
                Intent intent1 = null;
                intent1 = new Intent(this, DinoAddActivity.class);
                intent1.putExtra("cookie", cookie);
                intent1.putExtra("token", token);
                intent1.putExtra("user", user);
                startActivity(intent1);
                break;
        }
    }

    class ExtendedAdapter extends SimpleAdapter {

        Context cont;

        public ExtendedAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            cont = context;
        }

        @Override
        public void setViewImage(ImageView v, String value) {
            super.setViewImage(v, value);
            Glide.with(cont).load(value).into(v);
        }
    }
}

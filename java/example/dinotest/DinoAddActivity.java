package example.dinotest;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import example.dinotest.entity.Dino;
import example.dinotest.entity.File;
import example.dinotest.repository.DinoRepository;
import example.dinotest.repository.FileRepository;
import example.dinotest.repository.TaxonomyRepository;

public class DinoAddActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDinoOk;
    Button btnSelectImg;
    ImageView imgDino;
    EditText etDinoName;
    EditText etDinoDay;
    EditText etDinoMonth;
    EditText etDinoYear;
    EditText etDinoAbout;
    File file;
    Dino dino;
    Spinner spinColor;

    String cookie;
    String token;
    String user;
    Map<String, String> colors;

    static final int reqCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_add);

        Intent intent = getIntent();
        cookie = intent.getStringExtra("cookie");
        token = intent.getStringExtra("token");
        user = intent.getStringExtra("user");

        btnDinoOk = (Button) findViewById(R.id.btnDinoOk);
        btnSelectImg = (Button) findViewById(R.id.btnSelectImg);
        imgDino = (ImageView) findViewById(R.id.imgDino);
        etDinoName = (EditText) findViewById(R.id.etDinoName);
        etDinoDay = (EditText) findViewById(R.id.etDinoDay);
        etDinoMonth = (EditText) findViewById(R.id.etDinoMonth);
        etDinoYear = (EditText) findViewById(R.id.etDinoYear);
        etDinoAbout = (EditText) findViewById(R.id.etDinoAbout);
        spinColor = (Spinner) findViewById(R.id.spinColor);
        btnDinoOk.setOnClickListener(this);
        btnSelectImg.setOnClickListener(this);

        colors = TaxonomyRepository.getTaxonomy();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>(colors.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinColor.setAdapter(adapter);
        spinColor.setSelection(0);

        file = null;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDinoOk:
                if ((etDinoName.getText().toString().length() == 0) ||
                        (etDinoDay.getText().toString().length() == 0) ||
                        (etDinoMonth.getText().toString().length() == 0) ||
                        (etDinoYear.getText().toString().length() == 0) ||
                        (etDinoAbout.getText().toString().length() == 0) ||
                        (file == null)) {
                    Log.d("Dlog", etDinoName.getText().toString().length() + " " +
                            etDinoDay.getText().toString().length() + " " +
                            etDinoMonth.getText().toString().length() + " " +
                            etDinoYear.getText().toString().length() + " " +
                            etDinoAbout.getText().toString().length() + " " +
                            (file == null));
                    Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show();
                    break;
                } else {

                    String fid = FileRepository.createFile(file, cookie, token);

                    dino = new Dino(etDinoName.getText().toString(),
                            user,
                            colors.get(spinColor.getSelectedItem().toString()),
                            etDinoAbout.getText().toString(),
                            Integer.parseInt(etDinoDay.getText().toString()),
                            Integer.parseInt(etDinoMonth.getText().toString()),
                            Integer.parseInt(etDinoYear.getText().toString()),
                            fid);
                    String dinoId = DinoRepository.createDino(dino, cookie, token);
                    Toast.makeText(this, "Dino added, id is " + dinoId, Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case R.id.btnSelectImg:
                Intent intent0 = new Intent(Intent.ACTION_PICK);
                intent0.setType("image/*");
                startActivityForResult(intent0, reqCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        String mimeType = null;
        String fileName = null;
        String fileSize = null;

        switch (requestCode) {
            case reqCode:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imgDino.setImageBitmap(bitmap);
                        mimeType = getContentResolver().getType(selectedImage);

                        Cursor returnCursor = getContentResolver().query(selectedImage, null, null, null, null);
                        try {
                            if (returnCursor != null && returnCursor.moveToFirst()) {
                                fileName = returnCursor.getString(returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                fileSize = Long.toString(returnCursor.getLong(returnCursor.getColumnIndex(OpenableColumns.SIZE)));
                            }
                        } finally {
                            returnCursor.close();
                        }
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteFormat = stream.toByteArray();
                        String baseString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
                        file = new File(fileName, selectedImage.toString(), mimeType, fileSize, baseString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}

package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.luvizatnika1501512.utsmobprog.myojs.R;
import com.luvizatnika1501512.utsmobprog.myojs.model.Paper;
import com.luvizatnika1501512.utsmobprog.myojs.model.Register;
import com.luvizatnika1501512.utsmobprog.myojs.service.AppController;
import com.luvizatnika1501512.utsmobprog.myojs.service.RequestHandler;
import com.luvizatnika1501512.utsmobprog.myojs.service.Server;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Profil extends AppCompatActivity {

    Button simpan;

    @BindView(R.id.iVProfil)
    ImageView iVProfil;
    ConnectivityManager conMgr;
    Bitmap bitmap, decode;

    Unbinder unbinder;

    private String urld = Server.URL + "get_profil.php";
    private static final int REQUEST_CHOOSE_IMAGE = 3;
    SharedPreferences sharedPreferences;
    private String TAG_ID = "ID";
    String tag_json_obj = "json_obj_req";


    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //id = sharedPreferences.getString("ID", null);

        TextView namalengkap = findViewById(R.id.namaLengkap);
        TextView email =findViewById(R.id.email);
        TextView afiliasi =findViewById(R.id.afiliasi);
        TextView negara =findViewById(R.id.negara);

        getUserProfile();
        namalengkap.setText(getIntent().getStringExtra("NAMA_LENGKAP"));
        email.setText(getIntent().getStringExtra("EMAIL"));
        afiliasi.setText(getIntent().getStringExtra("AFILIASI"));
        negara.setText(getIntent().getStringExtra("NEGARA"));
        Button buttonprofil = (Button) findViewById(R.id.buttonprofil);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        iVProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fungsi untuk memanggil library choose image
                */
                EasyImage.openChooserWithGallery(Profil.this, "Choose Picture",
                        REQUEST_CHOOSE_IMAGE
                );

            }
        });

        buttonprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fungsi untuk memanggil library choose image
                */
                Intent intent = new Intent(Profil.this, MainMenu.class);
                finish();
                startActivity(intent);

            }
        });

    }

    private void getUserProfile(){
        class GetUser extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profil.this,"Tunggu",".....",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showUser(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(urld,"1");
                return s;
            }
        }
        GetUser gu = new GetUser();
        gu.execute();
    }

    private void showUser(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject data = result.getJSONObject(0);
            Register md = new Register();
            md.setNamalengkap(data.getString("NAMA_LENGKAP"));
            md.setAfiliasi(data.getString("AFILIASI"));
            md.setNegara(data.getString("NEGARA"));
            md.setEmail(data.getString("EMAIL"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // method untuk menghandle ketika user sudah memilih gambar.
        // ketika gambar sudah dipilih maka gambar akan di redirect ke activity
        // library android-image-picker
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                CropImage.activity(Uri.fromFile(imageFile))
                        //u can comment 3 lines of code for hide cropping circle image
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setCropShape(CropImageView.CropShape.OVAL)
//                        .setFixAspectRatio(true)
                        .start(Profil.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(Profil.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
            }
        });
        // ----

        // Method ini berfungsi ketika sudah selesai dari activity android-image-picker
        // Jika result_ok maka gambar yang sudah di crop akan dimasukan kedalam imageview
        // yang kita olah menggunakan library glide.
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Glide.with(this)
                        .load(new File(resultUri.getPath()))
                        //u can use this method for display circle picture
//                        .apply(new RequestOptions().circleCrop())
                        .apply(new RequestOptions())
                        .into(iVProfil);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}

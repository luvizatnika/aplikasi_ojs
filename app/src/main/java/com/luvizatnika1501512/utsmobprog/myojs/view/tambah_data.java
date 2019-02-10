package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luvizatnika1501512.utsmobprog.myojs.R;
import com.luvizatnika1501512.utsmobprog.myojs.service.AppController;
import com.luvizatnika1501512.utsmobprog.myojs.service.Server;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class tambah_data extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_tambah, btn_tambah_data;
    EditText judul, author, tahunRilis, jumlahHalaman, bahasa, deskripsi;
    Intent intent;

    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "paper.php";
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @BindView(R.id.tambahgambar)
    ImageView tambahgambar;
    @BindView(R.id.buttontambah)
    Button buttongambar;

    Unbinder unbinder;

    private static final int REQUEST_CHOOSE_IMAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        buttongambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fungsi untuk memanggil library choose image

                EasyImage.openChooserWithGallery(tambah_data.this, "Choose Picture",
                        REQUEST_CHOOSE_IMAGE);
            }
        });

        btn_tambah_data = (Button) findViewById(R.id.btnTambah);
        judul = (EditText) findViewById(R.id.judul);
        author = (EditText) findViewById(R.id.author);
        tahunRilis = (EditText) findViewById(R.id.tahunPublis);
        jumlahHalaman = (EditText) findViewById(R.id.jumlahHalaman);
        bahasa = (EditText) findViewById(R.id.bahasa);
        deskripsi = (EditText) findViewById(R.id.deskripsi);

        btn_tambah_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = judul.getText().toString();
                String password = author.getText().toString();
                String confirm_password = tahunRilis.getText().toString();
                String Nama_lengkap = jumlahHalaman.getText().toString();
                String Afiliasi = bahasa.getText().toString();
                String Negara = deskripsi.getText().toString();

                conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);{
                    if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkRegister(username, password, confirm_password, Nama_lengkap, Afiliasi, Negara);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void checkRegister(final String username, final String password, final String confirm_password, final String Nama_lengkap,
                               final String Afiliasi, final String Negara) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Tambah Data ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Tambah Data Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        Intent tamdat = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(tamdat);
                        Log.e("Success Tambah Data!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Tambah Data Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("JUDUL", username);
                params.put("AUTHOR", password);
                params.put("TAHUN_RILIS", confirm_password);
                params.put("JUMLAH_HALAMAN", Nama_lengkap);
                params.put("BAHASA", Afiliasi);
                params.put("DESKRIPSI", Negara);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(tambah_data.this, MainMenu.class);
        finish();
        startActivity(intent);
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
                        .start(tambah_data.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(tambah_data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        .into(tambahgambar);

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



    /*public void btnTambah (View v){
        Intent goTambah = new Intent(tambah_data.this, MainMenu.class);
        startActivity(goTambah);
    }*/
}

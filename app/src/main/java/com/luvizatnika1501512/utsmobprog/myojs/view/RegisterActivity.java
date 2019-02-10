package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.luvizatnika1501512.utsmobprog.myojs.R;
import com.luvizatnika1501512.utsmobprog.myojs.service.AppController;
import com.luvizatnika1501512.utsmobprog.myojs.service.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_regis;
    EditText namaLengkap, afiliasi, negara, email, namaPengguna, kataSandi, cKataSandi;
    Intent intent;

    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "register.php";
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_regis = (Button) findViewById(R.id.btnRegis);
        namaLengkap = (EditText) findViewById(R.id.namaLengkap);
        afiliasi = (EditText) findViewById(R.id.afiliasi);
        negara = (EditText) findViewById(R.id.negara);
        email = (EditText) findViewById(R.id.email);
        namaPengguna = (EditText) findViewById(R.id.namaPengguna);
        kataSandi = (EditText) findViewById(R.id.kataSandi);
        cKataSandi = (EditText) findViewById(R.id.cKataSandi);

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = namaPengguna.getText().toString();
                String password = kataSandi.getText().toString();
                String confirm_password = cKataSandi.getText().toString();
                String Nama_lengkap = namaLengkap.getText().toString();
                String Afiliasi = afiliasi.getText().toString();
                String Negara = negara.getText().toString();
                String Email = email.getText().toString();

                conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);{
                    if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkRegister(Nama_lengkap, Afiliasi, Negara, Email, username, password, confirm_password);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void checkRegister(final String Nama_lengkap, final String Afiliasi, final String Negara, final String Email,
                               final String username, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        Intent login = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(login);
                        Log.e("Successfully Register!", jObj.toString());
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("NAMA_PENGGUNA", username);
                params.put("KATA_SANDI", password);
                params.put("CONFIRM_KATA_SANDI", confirm_password);
                params.put("NAMA_LENGKAP", Nama_lengkap);
                params.put("AFILIASI", Afiliasi);
                params.put("NEGARA", Negara);
                params.put("EMAIL", Email);
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
        intent = new Intent(RegisterActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}

package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luvizatnika1501512.utsmobprog.myojs.model.Paper;
import com.luvizatnika1501512.utsmobprog.myojs.R;
import com.luvizatnika1501512.utsmobprog.myojs.adapter.AdapterPaper;
import com.luvizatnika1501512.utsmobprog.myojs.service.AppController;
import com.luvizatnika1501512.utsmobprog.myojs.service.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {


    //untuk menampilkan data pada recyclerview
    ProgressDialog pd;
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<Paper> mItems;

    private String urld = Server.URL + "get_all.php";

    String id;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "ID";
    public final static String TAG_NAMA = "NAMA_LENGKAP";
    public final static String TAG_EMAIL = "EMAIL";

    private static final String TAG = MainMenu.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sharedpreferences = getSharedPreferences(MainActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerview = findViewById(R.id.recyclerview);
        pd = new ProgressDialog(this);
        mItems = new ArrayList<>();
        loadJson();
        mManager = new LinearLayoutManager(MainMenu.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterPaper(this,mItems);
        mRecyclerview.setAdapter(mAdapter);

    }

//    private void getUserProfile(){
//        class GetUser extends AsyncTask<Void,Void,String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                showUser(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequestParam(urlp,idx);
//                return s;
//            }
//        }
//        GetUser gu = new GetUser();
//        gu.execute();
//    }
//
//    private void showUser(String json){
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray result = jsonObject.getJSONArray("result");
//            JSONObject c = result.getJSONObject(0);
//            String nama = c.getString(TAG_NAMA);
//            String email = c.getString(TAG_EMAIL);
//            String foto = c.getString(TAG_FOTO);
//
//            txt_nama.setText(nama);
//            txt_email.setText(email);
//            Picasso.with(this).load(foto).placeholder(R.drawable.user).error(R.drawable.user).into(user_picture);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    //melakukan pengambilan data dari database
    private void loadJson() {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(urld, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley","response : " + response.toString());
                for(int i = 0 ; i < response.length(); i++)
                {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        Paper md = new Paper();
                        md.setJudul(data.getString("JUDUL"));
                        md.setAuthor(data.getString("AUTHOR"));
                        md.setFoto(data.getString("FOTO"));
                        md.setBahasa(data.getString("BAHASA"));
                        md.setTahun(data.getString("TAHUN_RILIS"));
                        md.setJumlah(data.getString("JUMLAH_HALAMAN"));
                        md.setDeskripsi(data.getString("DESKRIPSI"));
                        mItems.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(reqData);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        cariData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

//    private void cariData(final String keyword) {
//        pd = new ProgressDialog(HomeActivity.this);
//        pd.setCancelable(false);
//        pd.setMessage("Loading...");
//        pd.show();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST, urlc, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e("Response: ", response.toString());
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//
//                    int value = jObj.getInt(TAG_VALUE);
//
//                    if (value == 1) {
//                        mItems.clear();
//                        mAdapter.notifyDataSetChanged();
//
//                        String getObject = jObj.getString(TAG_RESULTS);
//                        JSONArray jsonArray = new JSONArray(getObject);
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject obj = jsonArray.getJSONObject(i);
//
//                            ModelData data = new ModelData();
//
//                            data.setId(obj.getString(TAG_IDL));
//                            data.setNama(obj.getString(TAG_NAMAL));
//                            data.setDate(obj.getString(TAG_TGL));
//                            data.setTime(obj.getString(TAG_TIME));
//                            data.setDeskripsi(obj.getString(TAG_DESKRIPSI));
//                            data.setHarga(obj.getString(TAG_HARGA));
//
//                            mItems.add(data);
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                }
//
//                mAdapter.notifyDataSetChanged();
//                pd.dismiss();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                pd.dismiss();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("nama", keyword);
//
//                return params;
//            }
//
//        };
//
//        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
//    }


    @Override
    public void onRefresh() {
//        loadJson();
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin logout ?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(MainActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_NAMA, null);
                editor.putString(TAG_EMAIL, null);
//                    editor.putString(TAG_FOTO, null);
                editor.commit();
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem item){

        int id = item.getItemId();
        if (id == R.id.profil) {
            Intent prof = new Intent(getApplicationContext(), Profil.class);
            startActivity(prof);
            Toast.makeText(getApplicationContext(), "Show Profil", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.tambah_jurnal) {
            Intent tamjur = new Intent(getApplicationContext(), tambah_data.class);
            startActivity(tamjur);
            Toast.makeText(getApplicationContext(), "Show Tambah Jurnal", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.keluar) {
            logout();
        }
        return false;
    }

}



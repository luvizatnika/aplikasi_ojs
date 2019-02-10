package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.luvizatnika1501512.utsmobprog.myojs.R;
import com.luvizatnika1501512.utsmobprog.myojs.service.CustomVolleyRequest;
import com.luvizatnika1501512.utsmobprog.myojs.service.Server;

public class Deskripsi extends AppCompatActivity {

    private String urld = Server.URL + "delete_paper.php";
    private ImageLoader imageLoader;
    private Button buttonEdit, buttonHapus;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi);

        NetworkImageView foto = findViewById(R.id.imageView3);
        TextView title = findViewById(R.id.textView12);
        TextView author =findViewById(R.id.textView13);
        TextView tahun =findViewById(R.id.textView14);
        TextView jumlah =findViewById(R.id.textView15);
        TextView bahasa =findViewById(R.id.textView16);
        TextView deskripsi =findViewById(R.id.textView18);

        imageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();
        imageLoader.get(getIntent().getStringExtra("FOTO"), ImageLoader.getImageListener(foto, R.drawable.ic_book, R.drawable.ic_book));
        foto.setImageUrl(getIntent().getStringExtra("FOTO"), imageLoader);

        title.setText(getIntent().getStringExtra("JUDUL"));
        author.setText(getIntent().getStringExtra("AUTHOR"));
        tahun.setText(getIntent().getStringExtra("TAHUN_RILIS"));
        jumlah.setText(getIntent().getStringExtra("JUMLAH_HALAMAN"));
        bahasa.setText(getIntent().getStringExtra("BAHASA"));
        deskripsi.setText(getIntent().getStringExtra("DESKRIPSI"));

        Button buttonEdit = (Button) findViewById(R.id.btnEdit);
        Button buttonHapus = (Button) findViewById(R.id.btnHapus);

        /*FloatingActionButton lfab = (FloatingActionButton) findViewById(R.id.fab_label);
        FloatingActionButton eFab = (FloatingActionButton) findViewById(R.id.fab_edit);
        FloatingActionButton dFab = (FloatingActionButton) findViewById(R.id.fab_delete);
        final LinearLayout eLayout = (LinearLayout) findViewById(R.id.edit_layout);
        final LinearLayout dLayout = (LinearLayout) findViewById(R.id.delete_layout);

        /*lfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eLayout.getVisibility() == View.VISIBLE && dLayout.getVisibility() == View.VISIBLE){
                    eLayout.setVisibility(View.GONE);
                    dLayout.setVisibility(View.GONE);
                }else{
                    eLayout.setVisibility(View.VISIBLE);
                    dLayout.setVisibility(View.VISIBLE);
                }
            }
        });*/



        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goEdit = new Intent(Deskripsi.this, edit_data.class);
                startActivity(goEdit);
            }
        });

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hapus Data", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

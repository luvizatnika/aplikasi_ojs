package com.luvizatnika1501512.utsmobprog.myojs.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class edit_data extends AppCompatActivity {



    @BindView(R.id.editgambar)
    ImageView editgambar;
    @BindView(R.id.buttoneditgambah)
    Button buttonedit;

    Unbinder unbinder;

    private static final int REQUEST_CHOOSE_IMAGE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fungsi untuk memanggil library choose image

                EasyImage.openChooserWithGallery(edit_data.this, "Choose Picture",
                        REQUEST_CHOOSE_IMAGE);
            }
        });



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
                        .start(edit_data.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(edit_data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        .into(editgambar);

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

    public void btnEdits (View v){
        Intent goEdits = new Intent(edit_data.this, MainMenu.class);
        startActivity(goEdits);
    }
}

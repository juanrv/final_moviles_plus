package com.example.esqueleto_kianart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SubtitleData;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.esqueleto_kianart.entity.Imagen;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class SubirImagenActivity extends AppCompatActivity {

    @BindView(R.id.btnSubir)
    Button btnSubir;
    @BindView(R.id.imgVer)
    ImageView buscarImagen;

    DatabaseReference imgref;
    StorageReference storageReference;
    ProgressDialog cargando;

    Bitmap thump_bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_imagen);
        ButterKnife.bind(this);

        imgref = FirebaseDatabase.getInstance().getReference().child("imagen_subida");
        storageReference = FirebaseStorage.getInstance().getReference().child("imagen_comprimida");
        cargando = new ProgressDialog(this);

        buscarImagen.setOnClickListener(view -> {
            CropImage.startPickImageActivity(SubirImagenActivity.this);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resulUri = result.getUri();
                File url = new File(resulUri.getPath());
                Picasso.with(this).load(url).into(buscarImagen);
                try {
                    thump_bitmap = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                }catch (IOException e){
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thump_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte [] thump_byte = byteArrayOutputStream.toByteArray();
                int p = (int) (Math.random() * 25 +1);int s = (int) (Math.random() * 25 +1);
                int t = (int) (Math.random() * 25 +1);int c = (int) (Math.random() * 25 +1);
                int numero1 = (int) (Math.random() * 1012 + 2111);
                int numero2 = (int) (Math.random() * 1012 + 2111);

                String[] elementos = {"a", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"
                        , "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

                final String aleatorio = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] +numero2 + "_comprimido.jpg";

                btnSubir.setOnClickListener(view -> {
                    cargando.setTitle("Subiendo Imagen...");
                    cargando.setMessage("Espere por favor...");
                    cargando.show();

                    StorageReference ref = storageReference.child(aleatorio);
                    UploadTask uploadTask = ref.putBytes(thump_byte);

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()){
                                throw Objects.requireNonNull(task.getException());
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri downloaduri = task.getResult();
                            Imagen img = new Imagen(0, downloaduri.toString());
                            imgref.push().setValue(img);
                            cargando.dismiss();
                            Toast.makeText(getApplicationContext(), "Subida con Exito", Toast.LENGTH_LONG);
                        }
                    });
                });
            }
        }
    }

    public void goToActivityMisImagenes(View view) {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
    }


    public void goToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
    }
}
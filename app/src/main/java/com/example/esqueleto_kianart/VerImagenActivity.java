package com.example.esqueleto_kianart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esqueleto_kianart.entity.Imagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.esqueleto_kianart.R.drawable.abc_ic_ab_back_material;
import static com.example.esqueleto_kianart.R.drawable.boton_redondeado;

public class VerImagenActivity extends AppCompatActivity {


    @BindView(R.id.btnDarMeGusta)
    Button meGusta;
    @BindView(R.id.btnNoMeGusta)
    Button noMeGusta;
    @BindView(R.id.imgVer)
    ImageView imageView;
    @BindView(R.id.txtMeGustaVerImagenes)
    TextView txtNroMeGusta;

    private List<Imagen> imagenes;

    private Imagen imagen;

    boolean meGustaPresionado = false;
    boolean noMeGustaPresionado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);
        ButterKnife.bind(this);
        cargarInformacion();

        Intent intent = getIntent();
        imagen = (Imagen) intent.getParcelableExtra("Imagen");


        txtNroMeGusta.setText(String.valueOf(imagen.getNumeroMeGusta()));
        Picasso.with(this).load(imagen.getImagen()).into(imageView);

        meGusta.setOnClickListener(view ->{
            if(meGustaPresionado){
                meGusta.setBackgroundColor(getColor(R.color.boton_color));
                txtNroMeGusta.setText(String.valueOf(imagen.getNumeroMeGusta()));
                noMeGustaPresionado = false;
                meGustaPresionado = false;

            }
            else {
                meGusta.setBackgroundColor(getColor(R.color.verde));
                meGustaPresionado = true;
                noMeGustaPresionado = false;
                txtNroMeGusta.setText(String.valueOf(imagen.getNumeroMeGusta() + 1));
                noMeGusta.setBackgroundColor(getColor(R.color.boton_color));
            }
        });
        noMeGusta.setOnClickListener(view -> {
            if(noMeGustaPresionado){
                txtNroMeGusta.setText(String.valueOf(imagen.getNumeroMeGusta()));
                noMeGusta.setBackgroundColor(getColor(R.color.boton_color));
                noMeGustaPresionado = false;
                meGustaPresionado = false;
            }
            else {
                noMeGustaPresionado = true;
                meGustaPresionado = false;
                txtNroMeGusta.setText(String.valueOf(imagen.getNumeroMeGusta() - 1));
                meGusta.setBackgroundColor(getColor(R.color.boton_color));
                noMeGusta.setBackgroundColor(getColor(R.color.rojo));

            }
        });

    }
    /*private Imagen buscarImagen (long idImagen){
        Imagen imagenABuscar = new Imagen(6, R.drawable.imagen1, 1);
        for (int i = 0; i<imagenes.size(); i++){
            if(imagenes.get(i).getIdImagen() == idImagen){
                imagenABuscar = imagenes.get(i);
            }
        }
        return imagenABuscar;
    }*/

    public void goToMenuActivity(View view) {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
    }

    private void cargarInformacion(){
        imagenes = new ArrayList<>();
        /*imagenes.add(new Imagen(6, R.drawable.imagen1, 1));
        imagenes.add(new Imagen(16, R.drawable.imagen2, 2));
        imagenes.add(new Imagen(9, R.drawable.imagen3, 3));*/
    }
}
package com.example.esqueleto_kianart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.esqueleto_kianart.adapter.ImagenAdapter;
import com.example.esqueleto_kianart.entity.Imagen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColeccionActivity extends AppCompatActivity {
    private List<Imagen> imagenes;
    private ImagenAdapter imagenAdapter;

    @BindView(R.id.listImagenesColeccion)
    ListView listViewImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleccion);
        ButterKnife.bind(this);
        cargarInformacion();
        imagenAdapter = new ImagenAdapter(this, imagenes);
        listViewImagenes.setAdapter(imagenAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("imagen_subida");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imagenes.removeAll(imagenes);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        Imagen img = snapshot1.getValue(Imagen.class);
                        imagenes.add(img);
                    }
                    imagenAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listViewImagenes.setClickable(true);
        listViewImagenes.setOnItemClickListener((parent, view, position, id) -> {
            Imagen imagenSeleccionada = (Imagen) listViewImagenes.getItemAtPosition(position);
            goToVerImagenActivity(view, imagenSeleccionada);
        });
    }

    private void goToVerImagenActivity(View view, Imagen imagenSeleccionada) {
        Intent intent = new Intent(this, VerImagenActivity.class);
        intent.putExtra("Imagen", (Parcelable) imagenSeleccionada);
        startActivity(intent);
    }

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
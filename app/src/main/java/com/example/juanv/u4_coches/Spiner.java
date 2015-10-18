package com.example.juanv.u4_coches;

/*Na activity principal débese disporá dun botón de modo que chame a unha activity secundaria que amose:
ListView ou Spinner: (Segundo escollera o usuario), amosará o contido do ficheiro, de xeito que,
cada liña do ficheiro será un ítem que pode seleccionar o usuario.
 Cando o usuario seleccione un ítem realizar un Toast que indique a posición do elemento e o seu valor.*/

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Spiner extends Activity {

    Spinner spin_coches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        spin_coches = (Spinner) findViewById(R.id.idCoches);

        // Fonte de datos
        ArrayList<String> coches;
        coches=getIntent().getStringArrayListExtra("car");

        // Enlace do adaptador cos datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coches);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Enlace do adaptador co Spiner do Layout.
        spin_coches.setAdapter(adaptador);

        // Escoitador
        spin_coches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Toast.makeText(getBaseContext(), "Position "+pos +"  "+ ((TextView) view).getText(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }); // Fin da clase anónima
    }
}

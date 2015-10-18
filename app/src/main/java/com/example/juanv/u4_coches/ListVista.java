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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListVista extends Activity {
    ListView Lstcoches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Lstcoches = (ListView) findViewById(R.id.listaCoches);


        ArrayList<String> coches;
        coches=getIntent().getStringArrayListExtra("car");


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coches);

        //Enlace do adaptador co ListView
        Lstcoches.setAdapter(adaptador);

        //Escoitador
        Lstcoches.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext()," Position: "+position  +"  " +
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}

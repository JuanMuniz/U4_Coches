package com.example.juanv.u4_coches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class U4_Coches extends Activity {
    EditText marca;
    RadioButton Lview;
    RadioButton Spiner;
    boolean sdDisponhible;
    Button addOver;
    RadioButton add;
    Calendar c=Calendar.getInstance();
    RadioButton overwrite;
    public static String nomeFicheiro = "coches.txt";
    File dirFicheiroSD;
    File rutaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u4__coches);
        marca = (EditText) findViewById(R.id.brandText);
        marca.setTextColor(Color.BLUE);
        Lview= (RadioButton) findViewById(R.id.Lista);
        Spiner= (RadioButton)findViewById(R.id.desplegable);
        addOver= (Button) findViewById(R.id.addOverButton);
        add= (RadioButton)findViewById(R.id.addRadio);
        overwrite= (RadioButton)findViewById(R.id.OverwriteRadio);

        comprobarEstadoSD();
        establecerDirectorioFicheiro();
    }

   public void engadirSobreescribir(View v){

       //Si seleccionamos add
       if (add.isChecked()==true && (marca.getText().toString().equals("")==false)){


           try {


               OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta,true));
               osw.write(marca.getText() + " " + c.get(Calendar.DATE) + "\n");
               osw.close();

               marca.setText("");

           } catch (Exception ex) {
               Log.e("INTERNA", "Error escribindo no ficheiro");
               ex.printStackTrace();
           }
       }

       //si seleccionamos overwrite
       else if(overwrite.isChecked()== true && (marca.getText().toString().equals("")==false)){
           try {


               OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta,false));
               osw.write(marca.getText() + " " + c.get(Calendar.DATE) + "\n");
               osw.close();

               marca.setText("");

           } catch (Exception ex) {
               Log.e("INTERNA", "Error escribindo no ficheiro");
           }
       }else{
           Toast.makeText(getApplicationContext()," Check add or overwrite please",Toast.LENGTH_SHORT).show();
       }
   }



    public void establecerDirectorioFicheiro() {

        if (sdDisponhible) {
            // dirFicheiroSD = Environment.getExternalStorageDirectory();
            dirFicheiroSD = getExternalFilesDir(null);
            rutaCompleta = new File(dirFicheiroSD.getAbsolutePath(), nomeFicheiro);
            Toast.makeText(getApplicationContext(),rutaCompleta.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    public void comprobarEstadoSD() {
        String estado = Environment.getExternalStorageState();
        Log.e("SD", estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponhible=true;

            Toast.makeText(getApplicationContext(),"Mounted ",Toast.LENGTH_SHORT).show();
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponhible=true;
            Toast.makeText(getApplicationContext(), "Read only ", Toast.LENGTH_SHORT).show();
        }else{
            finish();}
    }


   //chamar as segundas activities
    public void callList(View v){


        ArrayList<String> coches=new ArrayList<>();
        //creo as intents
        Intent intent=new Intent(this, ListVista.class);
        Intent intentSp=new Intent(this, Spiner.class);

        //leo o ficheiro
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomeFicheiro)));

            while (br.ready()) {
                coches.add(br.readLine());
            }
            br.close();
            Toast.makeText(this, "Datos leidos ", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Problemas lendo o ficheiro", Toast.LENGTH_SHORT).show();
            Log.e("INTERNA", "Erro lendo o ficheiro. ");

        }


        //inicio a que esté marcada
        if (Lview.isChecked()){

            intent.putStringArrayListExtra("car", coches);
            startActivity(intent);
        }
        if (Spiner.isChecked()){

            intent.putStringArrayListExtra("coches",coches);
            startActivity(intentSp);
        }
        if (!(Spiner.isChecked()|| Lview.isChecked())){
            Toast.makeText(getApplicationContext()," Select the view mode",Toast.LENGTH_SHORT).show();
        }
    }


}

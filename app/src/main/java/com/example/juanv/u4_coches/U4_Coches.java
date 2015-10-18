package com.example.juanv.u4_coches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class U4_Coches extends FragmentActivity {
    EditText marca;
    boolean sdDisponhible;
    boolean escritura;
    Button addOver;
    RadioButton add;
    Calendar c=Calendar.getInstance();
    RadioButton overwrite;
    public static String nomeFicheiro = "coches.txt";
    File dirFicheiroSD;
    File rutaCompleta;
    TextView logGato;
    private dia dialogoFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u4__coches);
        marca = (EditText) findViewById(R.id.brandText);
        marca.setTextColor(Color.BLUE);
        addOver= (Button) findViewById(R.id.addOverButton);
        add= (RadioButton)findViewById(R.id.addRadio);
        overwrite= (RadioButton)findViewById(R.id.OverwriteRadio);
        logGato= (TextView)findViewById(R.id.log);
        dialogoFragmento =new dia();

        comprobarEstadoSD();
        establecerDirectorioFicheiro();
    }

   public void engadirSobreescribir(View v){

       //Si seleccionamos add
       if (add.isChecked()==true && (marca.getText().toString().equals("")==false)){


           try {


               OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta,true));
               osw.write(marca.getText() + "-" + c.get(Calendar.DATE) +"_"+c.get(Calendar.MONTH) +
                       "_"+c.get(Calendar.YEAR) +"_"+c.get(Calendar.HOUR_OF_DAY) +   "\n");
               osw.close();

               //Con estas duas views vexo na aplicación as mensaxes do LogCat
               logGato.setText(rutaCompleta.toString() + "\n" + marca.getText() + "-" + c.get(Calendar.DATE) + "_" +
                       c.get(Calendar.MONTH) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY) + "\n");

               //Mensaxes que aparecen no LogCat
               Log.i("Dir: ", rutaCompleta.toString());
               Log.i("Info: ", marca.getText() + "-" + c.get(Calendar.DATE) + "_" +
                       c.get(Calendar.MONTH) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY));
               marca.setText("");

           } catch (Exception ex) {
               Log.e("INTERN", "writing file error");
               ex.printStackTrace();
           }
       }

       //si seleccionamos overwrite
       else if(overwrite.isChecked()== true && (marca.getText().toString().equals("")==false)){
           try {


               OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta,false));
               osw.write(marca.getText() + "-" + c.get(Calendar.DATE) +"_"+c.get(Calendar.MONTH) +
                       "_"+c.get(Calendar.YEAR) +"_"+c.get(Calendar.HOUR_OF_DAY) +   "\n");
               osw.close();

               //Con estas duas views vexo na aplicación as mensaxes do LogCat
               logGato.setText(rutaCompleta.toString() + "\n" + marca.getText() + "-" + c.get(Calendar.DATE) + "_" +
                       c.get(Calendar.MONTH) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY) + "\n");

               //Mensaxes que aparecen no LogCat
               Log.i("Dir: ", rutaCompleta.toString());
               Log.i("Info: ", marca.getText() + "-" + c.get(Calendar.DATE) + "_" +
                       c.get(Calendar.MONTH) + "_" + c.get(Calendar.YEAR) + "_" + c.get(Calendar.HOUR_OF_DAY));
               marca.setText("");

           } catch (Exception ex) {
               Log.e("INTERN", "writing file error");
           }
       }else if((add.isChecked() || overwrite.isChecked()) && marca.getText().toString().equals("")){
           Toast.makeText(getApplicationContext()," Write a car brand please",Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(getApplicationContext()," Check add or overwrite please",Toast.LENGTH_SHORT).show();
       }
   }



    public void establecerDirectorioFicheiro() {

        if (sdDisponhible) {

            dirFicheiroSD = getExternalFilesDir(null);
            rutaCompleta = new File(dirFicheiroSD.getAbsolutePath(), nomeFicheiro);

        }
    }


    public void comprobarEstadoSD() {
        String estado = Environment.getExternalStorageState();
        Log.e("SD", estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponhible=true;
            escritura=true;

        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponhible=true;
            Toast.makeText(getApplicationContext(), "Read only ", Toast.LENGTH_SHORT).show();
        }else if(escritura==false){
            Toast.makeText(getApplicationContext(), "Read only, closing app ", Toast.LENGTH_SHORT).show();
            finish();}
    }


   //chamar as segundas activities
    public void callList(View v){

        Bundle feixe= new Bundle();
        ArrayList<String> coches=new ArrayList<>();


        //leo o ficheiro
        try {


            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaCompleta)));
            while (br.ready()) {
                coches.add(br.readLine());
            }
            br.close();

        } catch (Exception ex) {
            Toast.makeText(this, "Problems reading file", Toast.LENGTH_SHORT).show();
            Log.e("INTERN", "Reading file error ");

        }
        feixe.putStringArrayList("car",coches);
        dialogoFragmento.setArguments(feixe);
        dialogoFragmento.show(getFragmentManager(), "");

    }


}

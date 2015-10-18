package com.example.juanv.u4_coches;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import java.util.ArrayList;

/**
 * Created by juanv on 18/10/2015.
 */
public class dia extends DialogFragment {
    ArrayList<String>coches;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        coches=getArguments().getStringArrayList("car");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Dialog Box").setIcon(R.mipmap.coche)
                .setMessage("Click an option")
                .setPositiveButton("List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getActivity(),ListVista.class);
                        intent.putStringArrayListExtra("car", coches);
                        startActivity(intent);
                    }
                }).setNegativeButton("Spinner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getActivity(),Spiner.class);
                        intent.putStringArrayListExtra("car", coches);
                        startActivity(intent);

                    }
                });
        return builder.create();


    }

}


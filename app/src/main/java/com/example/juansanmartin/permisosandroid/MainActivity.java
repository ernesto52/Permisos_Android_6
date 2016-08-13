package com.example.juansanmartin.permisosandroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int REQUEST_CONTACTS = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_READ_STORAGE = 2;
    private static final int REQUEST_WRITE_STORAGE = 3;


    String permisos [] = {Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    int solicitud_permisos[]={R.string.permiso_contactos, R.string.permiso_camera,R.string.permiso_almacenamiento};
    int solicitudes[]={REQUEST_CAMERA,REQUEST_CONTACTS,REQUEST_READ_STORAGE};

    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.sample_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        verifiar_permisos();

    }

    private void verifiar_permisos() {
        for (int i = 0; i <permisos.length ; i++) {
            // Verifica si tenemos los permisos concedidos.
            if (ContextCompat.checkSelfPermission(this,permisos[i])!= PackageManager.PERMISSION_GRANTED) {
                // Hay que mostrar alguna explicacion?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,permisos[i])) {

                    // Muestra un expancion al usuario de forma asíncrona * * - no bloquee
                    // Este hilo a la espera de la respuesta del usuario! Después de que el usuario
                    // Ve la explicación, inténtelo de nuevo para solicitar el permiso.
                    //Explain to the user why we need to read the contacts

                    //Expande un cuadro de explicacion de mensaje cuando aun no se ha consedido el permiso
                    final int finalI = i;
                    Snackbar.make(mLayout,solicitud_permisos[i],Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this,permisos,
                                            solicitudes[finalI]);
                                }
                            })
                            .show();

                } else {
                    // No necesita explicación, podemos solicitar el permiso.
                    ActivityCompat.requestPermissions(this,permisos,solicitudes[i]);

                }
            }


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode){
            case REQUEST_CONTACTS:{
                // Hemos pedido varios permisos para los contactos, por lo que todos ellos necesitará ser evaluada
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    // All required permissions have been granted, display contacts fragment.
                    Snackbar.make(mLayout, R.string.permiso_contactos_disponible,
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else {

                    Snackbar.make(mLayout, R.string.permiso_contactos_no_disponible,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                return;
            }

            case REQUEST_CAMERA:{
                // Compruebe si se ha concedido el único permiso necesario
                if (grantResults.length >= 1 && grantResults[REQUEST_CAMERA] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso de la Camara se ha concedido.
                    Snackbar.make(mLayout, R.string.permiso_camara_disponible,
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mLayout, R.string.permiso_camara_no_disponible,
                            Snackbar.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_READ_STORAGE:{
                // Compruebe si se ha concedido el único permiso necesario
                if (grantResults.length >= 1 && grantResults[REQUEST_READ_STORAGE] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso de la Leer se ha concedido.
                    Snackbar.make(mLayout, R.string.permiso_lectura_disponible,
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mLayout, R.string.permiso_lectura_no_disponible,
                            Snackbar.LENGTH_SHORT).show();
                }
                return;
            }
            default:{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
/*
        if (requestCode == REQUEST_CAMERA) {
            // Compruebe si se ha concedido el único permiso necesario
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de la Camara se ha concedido.
                Snackbar.make(mLayout, R.string.permiso_camara_disponible,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.permiso_camara_no_disponible,
                        Snackbar.LENGTH_SHORT).show();
            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == REQUEST_CONTACTS) {

            // Hemos pedido varios permisos para los contactos, por lo que todos ellos necesitará ser evaluada
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                Snackbar.make(mLayout, R.string.permiso_contactos_disponible,
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else {

                Snackbar.make(mLayout, R.string.permiso_contactos_no_disponible,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

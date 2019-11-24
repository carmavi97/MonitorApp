package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.presenter.FormularioPresenter;

public class FormularioActivity extends AppCompatActivity {

    private FormularioPresenter presenter;
    private Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myContext=this;
        setContentView(R.layout.activity_formulario);

        Button cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                presenter.cancel();
            }
        });
        Button add=findViewById(R.id.acept);
        cancel.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                presenter.add();
            }
        });

        ArrayList<String> secciones=new ArrayList<>();
        secciones.add("Colonia");
        secciones.add("Manada");
        secciones.add("Tropa");
        secciones.add("Unidad");
        secciones.add("Clan");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, secciones);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.seccion);
        spinner.setAdapter(adapter);

        Button addSpinner = (Button) findViewById(R.id.addSpinner);
        addSpinner.setOnClickListener(new android.view.View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                alertDialog.setView(viewAlertDialog);
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                alertDialog
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void lazarList(){
        Intent intent=new Intent(FormularioActivity.this,
                ListadoActivity.class);
        startActivity(intent);
        this.onDestroy();
    }

    public void add(){
        Intent intent=new Intent(FormularioActivity.this ,
                ListadoActivity.class);
        startActivity(intent);
        this.onDestroy();
    }
}

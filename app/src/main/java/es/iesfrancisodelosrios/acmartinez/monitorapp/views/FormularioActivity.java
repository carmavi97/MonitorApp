package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.date:
                        showDatePickerDialog(date);
                        break;
                }
            }
        });
        TextInputEditText nombreEditText = (TextInputEditText) findViewById(R.id.email);
        nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    Log.d("AppCRUD", et.getText().toString());
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
                    Matcher matcher = pattern.matcher(et.getText().toString());
                    if (matcher.find()) {
                        TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                        nombreInputLayout.setError("");
                    } else {
                        TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                        nombreInputLayout.setError("E-mail no valido");
                    }
                }
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

    private void exit(){
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_exit, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                myContext);
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        FormularioActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
            .create()
            .show();

    }

    public void add(){
        Intent intent=new Intent(FormularioActivity.this ,
                ListadoActivity.class);
        startActivity(intent);
        this.onDestroy();
    }

    public void showDatePickerDialog(final EditText date) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                date.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    /*
    public void match(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                TextInputEditText et = (TextInputEditText) v;
                Log.d("AppCRUD", et.getText().toString());
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
                Matcher matcher = pattern.matcher(et.getText().toString());
                if (matcher.find()) {
                    TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                    nombreInputLayout.setError("");
                } else {
                    TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                    nombreInputLayout.setError("E-mail no valido");
                }
            }
        }
    }
    */


}

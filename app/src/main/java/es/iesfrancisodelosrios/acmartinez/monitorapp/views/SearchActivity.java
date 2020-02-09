package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.ListadoInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.SearchInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;
import es.iesfrancisodelosrios.acmartinez.monitorapp.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements SearchInterface.View{

    private PersonModel pm;
    private SearchInterface.Presenter presenter;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new SearchPresenter(this);
        setContentView(R.layout.activity_search);
        Button search= findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.search();
            }
        });
        pm=new PersonModel(this);
        ArrayList<String> secciones=pm.getSpinner();
        secciones.add("");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, secciones);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.seccion);
        spinner.setAdapter(adapter);

    }

        @Override
        public void toList() {
            Intent intent = new Intent(SearchActivity.this,
                    ListadoActivity.class);
            String[] args=new String[3];
            args[0]=((EditText) findViewById(R.id.name)).getText().toString();
            args[1]=((Spinner) findViewById(R.id.seccion)).getSelectedItem().toString();
            args[2]=((EditText) findViewById(R.id.date)).getText().toString();
            intent.putExtra("EXTRA_ARGS",args);
            startActivity(intent);
        }
    }

package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.ListadoInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.presenter.ListadoPresenter;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View{


    private ListadoInterface.Presenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MonitorApp");
        setSupportActionBar(toolbar);
        presenter= new ListadoPresenter(this);
        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Lista);

        // Crea el Adaptador con los datos de la lista anterior
        AcontecimientoAdapter adaptador = new AcontecimientoAdapter(presenter.getAllPeople());

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.toADD);
        fab.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                lanzarFormulario();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar:
                presenter.search();
                return true;
            case R.id.action_Sobre:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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


    @Override
    public void VOID() {

    }

    @Override
    public void lanzarFormulario() {
        Intent intent=new Intent(ListadoActivity.this,
                FormularioActivity.class);
        startActivity(intent);
    }

    public void search(){
        Intent intent=new Intent(ListadoActivity.this,
                SearchActivity.class);
        this.onPause();
        startActivity(intent);
    }

    public void about(){
        Intent intent=new Intent(ListadoActivity.this,
                AboutUsActivity.class);
        this.onPause();
        startActivity(intent);
    }
}

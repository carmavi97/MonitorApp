package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.ListadoInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;
import es.iesfrancisodelosrios.acmartinez.monitorapp.presenter.ListadoPresenter;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View{

    private  PersonModel pm;
    private ListadoInterface.Presenter presenter;
    private AcontecimientoAdapter adaptador;
    private ArrayList<Person> items=new ArrayList<Person>();
    private String TAG="MonitorApp";
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MonitorApp");
        setSupportActionBar(toolbar);
        presenter= new ListadoPresenter(this, context);
        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Lista);

        // Crea el Adaptador con los datos de la lista anterior
        items= presenter.initialicePeople();
        adaptador = new AcontecimientoAdapter(presenter.getAllPeople());
        TextView contador=findViewById(R.id.contadorTextView);
        contador.setText("Hay "+items.size()+" resultados");
        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(TAG,"Click RV: "+ position+": "+String.valueOf(items.get(position).getId()));
                presenter.onClickAdd(items.get(position).getId().intValue());
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.toADD);
        fab.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                lanzarFormulario(-1);
            }
        });

        SwipeController swipeController = new SwipeController(this);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        pm=new PersonModel(this);
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
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Lista);

        // Crea el Adaptador con los datos de la lista anterior
        items= presenter.getAllPeople();
        adaptador = new AcontecimientoAdapter(presenter.getAllPeople());
        TextView contador=findViewById(R.id.contadorTextView);
        contador.setText("Hay "+items.size()+" resultados");
        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(TAG,"Click RV: "+ position+": "+String.valueOf(items.get(position).getId()));
                presenter.onClickAdd(items.get(position).getId().intValue());
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Lista);

        // Crea el Adaptador con los datos de la lista anterior
        items= presenter.getAllPeople();
        adaptador = new AcontecimientoAdapter(presenter.getAllPeople());
        TextView contador=findViewById(R.id.contadorTextView);
        contador.setText("Hay "+items.size()+" resultados");
        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(TAG,"Click RV: "+ position+": "+String.valueOf(items.get(position).getId()));
                presenter.onClickAdd(items.get(position).getId().intValue());
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public ListadoInterface.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void lanzarFormulario(int id) {
        if(id==-1){
            Intent intent=new Intent(ListadoActivity.this,
                    FormularioActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(ListadoActivity.this,
                    FormularioActivity.class);

            intent.putExtra("EXTRA_PERSON_ID", Long.valueOf(items.get(id).getId()));
            Log.d(TAG, String.valueOf(id));
            startActivity(intent);
        }
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

    @Override
    public void showDeleteItemDialog(final int itemSelected) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Seguro de que desea eliminar?")
                .setMessage("No podra recuperar sus datos")
                .setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItemInList(itemSelected);
                                Toast.makeText(ListadoActivity.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        dialog.show();
    }

    public void removeItemInList(int index) {
        Person toDelete=items.get(index);
        this.items.remove(index);
        pm.delete(toDelete.getId());
        this.adaptador.notifyDataSetChanged();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Lista);

        // Crea el Adaptador con los datos de la lista anterior
        items= presenter.getAllPeople();
        adaptador = new AcontecimientoAdapter(presenter.getAllPeople());
        TextView contador=findViewById(R.id.contadorTextView);
        contador.setText("Hay "+items.size()+" resultados");
        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(TAG,"Click RV: "+ position+": "+String.valueOf(items.get(position).getId()));
                presenter.onClickAdd(items.get(position).getId().intValue());
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

/**
 * Clase para almacenar el adaptador con los datos
 * de los acontecimientos que va a mostrar
 * el RecyclerView
 *
 * Hay que añadir al proyecto la siguiente
 * dependencia en el archivo /app/build.gradle
 * con la versión que estemos trabajando
 * 'com.android.support:recyclerview-v7:27.1.1'
 */
public class AcontecimientoAdapter extends RecyclerView.Adapter<AcontecimientoAdapter.AcontecimientoViewHolder>
        implements View.OnClickListener {
    private ArrayList<Person> items;
    private View.OnClickListener listener;

    // Contruye el objeto adaptador recibiendo la lista de datos
    public AcontecimientoAdapter(@NonNull ArrayList<Person> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public AcontecimientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        row.setOnClickListener(this);

        AcontecimientoViewHolder avh = new AcontecimientoViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(AcontecimientoViewHolder viewHolder, int position) {
        Person item = items.get(position);
        viewHolder.AcontecimientoBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class AcontecimientoViewHolder
            extends RecyclerView.ViewHolder {

        private TextView TextView_email;
        private TextView TextView_id;
        private TextView TextView_nombre;
        private ImageView imageImageView;
        private TextView TextView_lastName;
        private CheckBox CheckBox_mtl;
        private TextView TextView_fnaciemiento;
        private TextView TextView_seccion;
        private TextView TextView_phone;
        private TextView TextView_nif;


        public AcontecimientoViewHolder(View itemView) {
            super(itemView);
            TextView_id = (TextView) itemView.findViewById(R.id.id);
            TextView_nombre = (TextView) itemView.findViewById(R.id.name);
            TextView_email = (TextView) itemView.findViewById(R.id.email);
            TextView_lastName= itemView.findViewById(R.id.lastName);
            CheckBox_mtl=itemView.findViewById(R.id.MTL);
            TextView_fnaciemiento= (TextView) itemView.findViewById(R.id.fnacimiento);
            TextView_seccion= (TextView) itemView.findViewById(R.id.seccion);
            TextView_nif= (TextView) itemView.findViewById(R.id.nif);
            TextView_phone= (TextView) itemView.findViewById(R.id.phone);
            imageImageView = (ImageView) itemView.findViewById(R.id.imageView);

        }

        public void AcontecimientoBind(@org.jetbrains.annotations.NotNull Person item) {
            if (item != null){
                TextView_id.setText(item.getId().toString());
                if(item.getEmail()!=null) {
                    TextView_email.setText(item.getEmail());
                }else{
                    TextView_email.setText("No disponible");
                }
                if(item.getName()!=null) {
                    TextView_nombre.setText(item.getName());
                }else{
                    TextView_nombre.setText("Unknow");
                }
                if(item.getBirthDate()!=null){
                    TextView_fnaciemiento.setText(item.getBirthDate());
                }else {
                    TextView_fnaciemiento.setText("Unknow");
                }
                if(item.getLastName()!=null){
                    TextView_lastName.setText(item.getLastName());
                }else{
                    TextView_lastName.setText("Unknow");
                }
                CheckBox_mtl.setChecked(item.getMtl());
                if(item.getSection()!=null){
                    TextView_seccion.setText(item.getSection());
                }else{
                    TextView_seccion.setText("Unknow");
                }
                if(item.getNif()!=null){
                    TextView_nif.setText(item.getNif());
                }else{
                    TextView_nif.setText("Unknow");
                }
                if(item.getPhone()!=null){
                    TextView_phone.setText(item.getPhone());
                }else{
                    TextView_phone.setText("Unknow");
                }
                if(item.getPhoto()!=null) {
                    byte[] decodedString = Base64.decode(item.getPhoto(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(
                            decodedString,
                            0,
                            decodedString.length);
                    imageImageView.setImageBitmap(decodedByte);
                }else{
                    String imgDefault="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";
                    byte[] decodedString = Base64.decode(imgDefault, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(
                            decodedString,
                            0,
                            decodedString.length);
                    imageImageView.setImageBitmap(decodedByte);
                }

            }
        }


    }
}

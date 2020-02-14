package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.FormularioInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;
    private PersonModel pm;
    public FormularioPresenter(FormularioInterface.View view, Context context) {

        this.view = view;
        this.pm=new PersonModel(context);
    }

    @Override
    public void onClickAdd() {

    }

    @Override
    public void cancel() {
        view.lazarList();
    }

    @Override
    public void add() {
            view.add();

    }

    @Override
    public void match() {
        //view.match();
    }

    @Override
    public void toExit(){
        view.exit();
    }

    @Override
    public void update(Person p) {
        pm.update(p);
    }

    @Override
    public void insert(Person p) {
        pm.insert(p);
    }

    @Override
    public ArrayList<String> getSpinner() {
        ArrayList spinner=pm.getSpinner();
        return spinner;
    }

    @Override
    public Person selectById(long id) {
        Person p=pm.selectById(id);
        return p;
    }

    @Override
    public void onClickAddImage(Context myContext) {
        int ReadPremission= ContextCompat.checkSelfPermission(
                myContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if (ReadPremission != PackageManager.PERMISSION_GRANTED){
            view.requestPermision();
        }
    }

    @Override
    public void selectImg() {
        view.selectPicture();
    }
    /*
    @Override
    public void markDate(EditText date){
        view.showDatePickerDialog(date);
    }
*/
}


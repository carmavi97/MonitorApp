package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.FormularioInterface;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;

    public FormularioPresenter(FormularioInterface.View view) {
        this.view = view;
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


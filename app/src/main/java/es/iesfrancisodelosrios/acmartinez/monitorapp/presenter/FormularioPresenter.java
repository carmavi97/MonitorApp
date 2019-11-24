package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.widget.EditText;

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
/*
    @Override
    public void markDate(EditText date){
        view.showDatePickerDialog(date);
    }
*/
}


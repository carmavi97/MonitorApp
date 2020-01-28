package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.content.Context;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.ListadoInterface;

import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;

public class ListadoPresenter implements ListadoInterface.Presenter{
    private ListadoInterface.View view;
    private PersonModel person;

    public ListadoPresenter(ListadoInterface.View view, Context context) {
        this.view = view;
        this.person=new PersonModel(context);
    }

    @Override
    public void onClickAdd(Integer id) {
        view.lanzarFormulario(-1);
    }

    @Override
    public void search() {
        view.search();
    }

    @Override
    public void about() {
        view.about();
    }

    @Override
    public ArrayList<Person> getAllPeople(){
        return person.getAllPersons();
    }

    @Override
    public void onItemSwipped(int id) {
        view.showDeleteItemDialog(id);
    }
}

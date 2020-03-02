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
    public void ayuda() {
        view.help();
    }

    @Override
    public void about() {
        view.about();
    }

    @Override
    public ArrayList<Person> initialicePeople(){
        ArrayList<Person> items=new ArrayList<>();
        items=person.getPersonList();
        if(items.size()==0) {
            person.generatePersons();
            items=person.getPersonList();
        }
        return items;
    }


    @Override
    public ArrayList<Person> getAllPeople(){
        ArrayList<Person> items=new ArrayList<>();
        items=person.getPersonList();

        return items;
    }

    @Override
    public ArrayList<Person> doSearch(String[] args){
        ArrayList<Person> items=new ArrayList<>();
        items=person.search(args);
        return items;
    }

    @Override
    public void delete(long id) {
        person.delete(id);
    }

    @Override
    public void onItemSwippedRight(int id) {
        view.showDeleteItemDialog(id);
    }

    @Override
    public void onItemSwippedLeft(int id){
        view.lanzarFormulario(id);
    }
}

package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public interface ListadoInterface {

    public interface View{
        void VOID();

        void lanzarFormulario(int id);

        void search();

        void about();

        void help();

        void showDeleteItemDialog(int id);

        void removeItemInList(int index);

    }

    public interface Presenter{
        void onClickAdd(Integer id);
        void search();
        ArrayList<Person> doSearch(String[] args);
        void about();
        ArrayList<Person> getAllPeople();
        void onItemSwippedRight(int id);
        void onItemSwippedLeft(int id);
        public ArrayList<Person> initialicePeople();
        void delete(long id);
    void ayuda();
     }
}

package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public interface ListadoInterface {

    public interface View{
        void VOID();

        void lanzarFormulario(int id);

        void search();

        void about();
    }

    public interface Presenter{
        void onClickAdd(Integer id);
        void search();
        void about();
        ArrayList<Person> getAllPeople();
    }
}

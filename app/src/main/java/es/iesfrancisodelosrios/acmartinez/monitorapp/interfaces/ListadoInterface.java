package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public interface ListadoInterface {

    public interface View{
        void VOID();

        void lanzarFormulario();

        void search();

        void about();
    }

    public interface Presenter{
        void onClickAdd();
        void search();
        void about();
        ArrayList<Person> getAllPeople();
    }
}

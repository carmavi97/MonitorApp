package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

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
    }
}

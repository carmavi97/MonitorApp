package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

public interface ListadoInterface {

    public interface View{
        void VOID();

        void lanzarFormulario();
    }

    public interface Presenter{
        void onClickAdd();
    }
}

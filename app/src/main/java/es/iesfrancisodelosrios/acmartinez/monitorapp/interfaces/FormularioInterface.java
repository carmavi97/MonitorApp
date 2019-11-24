package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

public interface FormularioInterface {

    public interface View{
        void VOID();

        void lazarList();

        void add();
    }

    public interface Presenter{
        void onClickAdd();
        void cancel();
        void add();
    }
}

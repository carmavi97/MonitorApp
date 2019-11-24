package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import android.widget.EditText;

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
        //void markDate(EditText date);
        void match();
    }
}

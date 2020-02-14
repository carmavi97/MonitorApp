package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public interface FormularioInterface {

    public interface View{
        void VOID();

        void lazarList();
        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
        void add();
        void requestPermision();
        void selectPicture();
        void exit();

    }

    public interface Presenter{
        void onClickAdd();
        void cancel();
        void add();
        //void markDate(EditText date);
        void match();
        void onClickAddImage(Context myContext);
        void selectImg();
        void toExit();
        void update(Person p);
        void insert(Person p);
        ArrayList<String> getSpinner();
        Person selectById(long id);
    }
}

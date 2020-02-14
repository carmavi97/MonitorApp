package es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces;

import java.util.ArrayList;

public interface SearchInterface {
     interface View{
        void toList();
    }

    interface Presenter{
         void search();
        ArrayList<String> getSpinner();
    }
}

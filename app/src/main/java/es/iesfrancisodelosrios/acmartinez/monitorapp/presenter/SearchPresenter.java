package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.content.Context;


import java.util.ArrayList;

import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.SearchInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;

public class SearchPresenter implements SearchInterface.Presenter {

    private SearchInterface.View view;
    private PersonModel pm;

    public SearchPresenter(SearchInterface.View view,Context context){
        this.view=view;
        this.pm=new PersonModel(context);
    }

    @Override
    public void search(){
        view.toList();
    }

    @Override
    public ArrayList<String> getSpinner() {
        ArrayList spinner=pm.getSpinner();
        return spinner;
    }
}

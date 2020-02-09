package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import android.content.Context;


import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.SearchInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.PersonModel;

public class SearchPresenter implements SearchInterface.Presenter {

    private SearchInterface.View view;


    public SearchPresenter(SearchInterface.View view){
        this.view=view;
    }

    @Override
    public void search(){
        view.toList();
    }

}

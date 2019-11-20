package es.iesfrancisodelosrios.acmartinez.monitorapp.presenter;

import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.ListadoInterface;
public class ListadoPresenter implements ListadoInterface.Presenter{
    private ListadoInterface.View view;

    public ListadoPresenter(ListadoInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickAdd() {
        view.lanzarFormulario();
    }
}

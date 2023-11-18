package com.alberto.familysyncapp.presenter.centro;

import com.alberto.familysyncapp.adapter.CentroAdapter;
import com.alberto.familysyncapp.contract.centro.DeleteCentroContract;
import com.alberto.familysyncapp.model.centro.DeleteCentroModel;

public class DeleteCentroPresenter implements DeleteCentroContract.Presenter,
    DeleteCentroContract.Model.OnDeleteCentroListener {
    private DeleteCentroModel model;
    private CentroAdapter view;

    public DeleteCentroPresenter(CentroAdapter view){
        this.view = view;
        model = new DeleteCentroModel();
    }

    @Override
    public void deleteCentro(long id) {
        model.deleteCentro(id,this);
    }

    @Override
    public void onDeleteCentroSuccess(String message) {
        view.showMessage(message);
    }

    @Override
    public void onDeleteCentroError(String message) {
        view.showError(message);

    }
}

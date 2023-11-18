package com.alberto.familysyncapp.presenter.centro;


import com.alberto.familysyncapp.contract.centro.ModifyCentroContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.model.centro.ModifyCentroModel;
import com.alberto.familysyncapp.view.centro.RegisterCentroView;

public class ModifyCentroPresenter implements ModifyCentroContract.Presenter,
    ModifyCentroContract.Model.OnModifyCentroListener {
    private ModifyCentroModel model;
    private RegisterCentroView view;

    public ModifyCentroPresenter(RegisterCentroView view){
        this.view = view;
        model = new ModifyCentroModel();
    }

    @Override
    public void modifyCentro(Centro centro) {
        model.modifyCentro(centro,this);
    }

    @Override
    public void onModifyCentroSuccess(Centro centro) {
        view.showMessage("El centro: "+ centro.getNombre() + " se ha modificado correctamente.");
    }

    @Override
    public void onModifyCentroError(String message) {
        view.showError(message);

    }
}

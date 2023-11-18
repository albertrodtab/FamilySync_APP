package com.alberto.familysyncapp.presenter.profesional;


import com.alberto.familysyncapp.contract.profesional.ModifyProfesionalContract;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.model.profesional.ModifyProfesionalModel;
import com.alberto.familysyncapp.view.profesional.RegisterProfesionalView;

public class ModifyProfesionalPresenter implements ModifyProfesionalContract.Presenter,
    ModifyProfesionalContract.Model.OnModifyProfesionalListener {
    private ModifyProfesionalModel model;
    private RegisterProfesionalView view;

    public ModifyProfesionalPresenter(RegisterProfesionalView view){
        this.view = view;
        model = new ModifyProfesionalModel();
    }

    @Override
    public void modifyProfesional(Profesional profesional) {
        model.modifyProfesional(profesional,this);
    }

    @Override
    public void onModifyProfesionalSuccess(Profesional profesional) {
        view.showMessage("El profesional: "+ profesional.getNombre() + " se ha modificado correctamente.");
    }

    @Override
    public void onModifyProfesionalError(String message) {
        view.showError(message);

    }
}

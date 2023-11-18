package com.alberto.familysyncapp.presenter.profesional;


import com.alberto.familysyncapp.contract.profesional.RegisterProfesionalContract;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.model.profesional.RegisterProfesionalModel;
import com.alberto.familysyncapp.view.profesional.RegisterProfesionalView;

public class RegisterProfesionalPresenter implements RegisterProfesionalContract.Presenter,
        RegisterProfesionalContract.Model.OnRegisterProfesionalListener{

    private RegisterProfesionalModel model;
    private RegisterProfesionalView view;

    public RegisterProfesionalPresenter(RegisterProfesionalView view){
        this.view = view;
        this.model = new RegisterProfesionalModel();
    }

    @Override
    public void registerProfesional(Profesional profesional) {
        model.registerProfesional(profesional, this);

    }

    @Override
    public void onRegisterProfesionalSuccess(Profesional profesional) {
        view.showMessage("Profesional registrado");
    }

    @Override
    public void onRegisterProfesionalError(String message) {
        view.showError("Se ha producido un error al registrar el Profesional. Inténtalo de nuevo");
    }
}
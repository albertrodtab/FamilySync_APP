package com.alberto.familysyncapp.presenter.centro;


import android.content.Context;

import com.alberto.familysyncapp.contract.centro.RegisterCentroContract;
import com.alberto.familysyncapp.domain.Centro;

import com.alberto.familysyncapp.model.centro.RegisterCentroModel;
import com.alberto.familysyncapp.view.centro.RegisterCentroView;

public class RegisterCentroPresenter implements RegisterCentroContract.Presenter,
        RegisterCentroContract.Model.OnRegisterCentroListener{

    private RegisterCentroModel model;
    private RegisterCentroView view;
    private Context context; // Agrega esta línea

    public RegisterCentroPresenter(Context context){
        this.context = context; // Asigna el contexto recibido
        this.view = (RegisterCentroView) context;
        this.model = new RegisterCentroModel(context);
    }

    @Override
    public void registerCentro(Centro centro) {
        model.registerCentro(centro, this);

    }

    @Override
    public void onRegisterCentroSuccess(Centro centro) {
        view.showMessage("Centro registrado");
    }

    @Override
    public void onRegisterCentroError(String message) {
        view.showError("Se ha producido un error al registrar el centro. Inténtalo de nuevo");
    }
}
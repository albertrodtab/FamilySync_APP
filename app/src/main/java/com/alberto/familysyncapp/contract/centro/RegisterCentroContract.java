package com.alberto.familysyncapp.contract.centro;

import com.alberto.familysyncapp.domain.Centro;

public interface RegisterCentroContract {

    interface Model{
        interface OnRegisterCentroListener {
            void onRegisterCentroSuccess(Centro centro);
            void onRegisterCentroError(String message);
        }
        boolean registerCentro(Centro centro, OnRegisterCentroListener listener);
    }

    interface view {
        void showError(String errorMessage);
        void showMessage(String message);
        void resetForm();
    }

    interface Presenter{
        void registerCentro(Centro centro);

    }
}

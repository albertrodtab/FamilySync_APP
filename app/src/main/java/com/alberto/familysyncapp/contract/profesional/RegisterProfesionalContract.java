package com.alberto.familysyncapp.contract.profesional;

import com.alberto.familysyncapp.domain.Profesional;

public interface RegisterProfesionalContract {

    interface Model{
        interface OnRegisterProfesionalListener {
            void onRegisterProfesionalSuccess(Profesional profesional);
            void onRegisterProfesionalError(String message);
        }
        boolean registerProfesional(Profesional profesional, OnRegisterProfesionalListener listener);
    }

    interface view {
        void showError(String errorMessage);
        void showMessage(String message);
        void resetForm();
    }

    interface Presenter{
        void registerProfesional(Profesional profesional);

    }
}

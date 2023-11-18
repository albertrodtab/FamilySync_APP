package com.alberto.familysyncapp.contract.profesional;

import com.alberto.familysyncapp.domain.Profesional;

public interface DetailsProfesionalContract {

    interface Model{
        interface OnLoadProfesionalListener {
            void onLoadProfesionalSuccess(Profesional profesional);
            void onLoadProfesionalError(String message);
        }
        void loadProfesional(long id, OnLoadProfesionalListener listener);
    }

    interface view {
        void showError(String errorMessage);
        void showMessage(String message);

    }

    interface Presenter{
        void loadProfesional(long id);

    }
}

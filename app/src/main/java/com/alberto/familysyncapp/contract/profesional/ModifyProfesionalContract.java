package com.alberto.familysyncapp.contract.profesional;

import com.alberto.familysyncapp.domain.Profesional;

public interface ModifyProfesionalContract {

    interface Model{
        interface OnModifyProfesionalListener {
            void onModifyProfesionalSuccess(Profesional profesional);
            void onModifyProfesionalError(String error);
        }
        void modifyProfesional(Profesional profesional, OnModifyProfesionalListener listener);
    }

    interface view {
        void showError(String message);
        void showMessage(String message);
    }

    interface Presenter{
        void modifyProfesional(Profesional profesional);

    }

}

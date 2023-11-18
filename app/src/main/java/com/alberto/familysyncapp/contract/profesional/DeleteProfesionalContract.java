package com.alberto.familysyncapp.contract.profesional;

public interface DeleteProfesionalContract {

    interface Model{
        interface OnDeleteProfesionalListener {
            void onDeleteProfesionalSuccess(String message);
            void onDeleteProfesionalError(String error);
        }
        void deleteProfesional(long id, OnDeleteProfesionalListener listener);
    }

    interface view {
        void showError(String message);
        void showMessage(String message);
    }

    interface Presenter{
        void deleteProfesional(long id);

    }

}

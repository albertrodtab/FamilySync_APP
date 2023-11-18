package com.alberto.familysyncapp.contract.centro;

public interface DeleteCentroContract {

    interface Model{
        interface OnDeleteCentroListener {
            void onDeleteCentroSuccess(String message);
            void onDeleteCentroError(String error);
        }
        void deleteCentro(long id, OnDeleteCentroListener listener);
    }

    interface view {
        void showError(String message);
        void showMessage(String message);
    }

    interface Presenter{
        void deleteCentro(long id);

    }

}

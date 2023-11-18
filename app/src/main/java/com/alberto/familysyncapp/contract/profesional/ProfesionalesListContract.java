package com.alberto.familysyncapp.contract.profesional;

import com.alberto.familysyncapp.domain.Profesional;

import java.util.List;

public interface ProfesionalesListContract {

    interface Model{
        interface OnLoadProfesionalListener {
            void onLoadProfesionalSuccess(List<Profesional> ProfesionalesList);
            void onLoadProfesionalError(String message);
        }
        void loadAllProfesionales(ProfesionalesListContract.Model.OnLoadProfesionalListener listener);
    }

    interface view{
        void showProfesionales(List<Profesional> profesionales);
        void showMessage(String message);
    }

    interface Presenter{
        void loadAllProfesionales();
        /*void loadProfesionalesByName(String name);
        void deleteProfesional(String name);*/
    }

}

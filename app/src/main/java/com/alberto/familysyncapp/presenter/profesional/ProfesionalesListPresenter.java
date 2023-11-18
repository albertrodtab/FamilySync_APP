package com.alberto.familysyncapp.presenter.profesional;

import com.alberto.familysyncapp.contract.profesional.ProfesionalesListContract;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.model.profesional.ProfesionalesListModel;
import com.alberto.familysyncapp.view.profesional.ProfesionalesListView;

import java.util.List;

public class ProfesionalesListPresenter implements ProfesionalesListContract.Presenter,
    ProfesionalesListContract.Model.OnLoadProfesionalListener{

    private ProfesionalesListModel model;
    private ProfesionalesListView view;

    public ProfesionalesListPresenter(ProfesionalesListView view){
        this.view = view;
        this.model = new ProfesionalesListModel();
    }

    @Override
    public void loadAllProfesionales() {
        model.loadAllProfesionales(this);
    }


    @Override
    public void onLoadProfesionalSuccess(List<Profesional> ProfesionalesList) {
        view.showProfesionales(ProfesionalesList);

    }

    @Override
    public void onLoadProfesionalError(String message) {
        view.showMessage(message);

    }
}

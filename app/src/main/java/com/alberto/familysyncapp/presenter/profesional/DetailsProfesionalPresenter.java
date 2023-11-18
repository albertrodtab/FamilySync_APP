package com.alberto.familysyncapp.presenter.profesional;


import com.alberto.familysyncapp.contract.profesional.DetailsProfesionalContract;
import com.alberto.familysyncapp.domain.Profesional;
import com.alberto.familysyncapp.model.profesional.DetailsProfesionalModel;
import com.alberto.familysyncapp.view.profesional.DetailsProfesionalView;

public class DetailsProfesionalPresenter implements DetailsProfesionalContract.Presenter,
    DetailsProfesionalContract.Model.OnLoadProfesionalListener {
    private DetailsProfesionalModel model;
    private DetailsProfesionalView view;

    public DetailsProfesionalPresenter(DetailsProfesionalView view){
        this.view = view;
        model = new DetailsProfesionalModel();
    }

    @Override
    public void loadProfesional (long id) {
        model.loadProfesional(id,this);
    }

    @Override
    public void onLoadProfesionalSuccess(Profesional profesional) {
        view.showProfesional(profesional);

    }

    @Override
    public void onLoadProfesionalError(String message) {
        view.showError(message);

    }
}

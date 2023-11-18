package com.alberto.familysyncapp.presenter.centro;

import com.alberto.familysyncapp.contract.centro.CentrosListContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.model.centro.CentrosListModel;
import com.alberto.familysyncapp.view.centro.CentrosListView;

import java.util.List;

public class CentrosListPresenter implements CentrosListContract.Presenter,
    CentrosListContract.Model.OnLoadCentroListener{

    private CentrosListModel model;
    private CentrosListView view;

    public CentrosListPresenter(CentrosListView view){
        this.view = view;
        this.model = new CentrosListModel();
    }

    @Override
    public void loadAllCentros() {
        model.loadAllCentros(this);

    }


    @Override
    public void onLoadCentrosSuccess(List<Centro> centrosList) {
        view.showCentros(centrosList);
    }

    @Override
    public void onLoadCentrosError(String message) {
        view.showMessage(message);

    }
}

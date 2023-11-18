package com.alberto.familysyncapp.presenter.centro;

import com.alberto.familysyncapp.contract.centro.CentrosListMapsContract;
import com.alberto.familysyncapp.domain.Centro;
import com.alberto.familysyncapp.model.centro.CentrosListMapsModel;
import com.alberto.familysyncapp.view.MapsActivityView;

import java.util.List;

public class CentrosListMapsPresenter implements CentrosListMapsContract.Presenter,
    CentrosListMapsContract.Model.OnLoadCentroListener{

    private CentrosListMapsModel model;
    private MapsActivityView view;

    public CentrosListMapsPresenter(MapsActivityView view){
        this.view = view;
        this.model = new CentrosListMapsModel();
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

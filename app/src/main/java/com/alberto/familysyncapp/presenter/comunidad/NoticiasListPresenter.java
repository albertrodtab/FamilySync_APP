package com.alberto.familysyncapp.presenter.comunidad;

import com.alberto.familysyncapp.contract.comunicadad.NoticiasListContract;
import com.alberto.familysyncapp.contract.residente.ResidentesListContract;
import com.alberto.familysyncapp.domain.Noticias;
import com.alberto.familysyncapp.domain.Residente;
import com.alberto.familysyncapp.model.comunidad.NoticiasListModel;
import com.alberto.familysyncapp.model.residente.ResidentesListModel;
import com.alberto.familysyncapp.view.comunidad.NoticiasListView;
import com.alberto.familysyncapp.view.residente.ResidentesListView;

import java.util.List;

public class NoticiasListPresenter implements NoticiasListContract.Presenter,
    NoticiasListContract.Model.OnLoadNoticiasListener{

    private NoticiasListModel model;
    private NoticiasListView view;

    public NoticiasListPresenter(NoticiasListView view){
        this.view = view;
        this.model = new NoticiasListModel();
    }

    @Override
    public void loadAllNoticias() {
        model.loadAllNoticias(this);

    }


    @Override
    public void onLoadNoticiasSuccess(List<Noticias> NoticiasList) {
        view.showNoticias(NoticiasList);

    }

    @Override
    public void onLoadNoticiasError(String message) {
        view.showMessage(message);

    }
}

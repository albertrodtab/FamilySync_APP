package com.alberto.familysyncapp.contract.comunicadad;

import com.alberto.familysyncapp.domain.Noticias;
import com.alberto.familysyncapp.domain.Residente;

import java.util.List;

public interface NoticiasListContract {

    interface Model{
        interface OnLoadNoticiasListener {
            void onLoadNoticiasSuccess(List<Noticias> NoticiasList);
            void onLoadNoticiasError(String message);
        }
        void loadAllNoticias(NoticiasListContract.Model.OnLoadNoticiasListener listener);
    }

    interface view{
        void showNoticias(List<Noticias> noticias);
        void showMessage(String message);
    }

    interface Presenter{
        void loadAllNoticias();
    }

}

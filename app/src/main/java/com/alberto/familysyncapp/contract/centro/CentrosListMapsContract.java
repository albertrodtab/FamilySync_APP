package com.alberto.familysyncapp.contract.centro;

import com.alberto.familysyncapp.domain.Centro;

import java.util.List;

public interface CentrosListMapsContract {

    interface Model{
        interface OnLoadCentroListener {
            void onLoadCentrosSuccess(List<Centro> centrosList);
            void onLoadCentrosError(String message);
        }
        void loadAllCentros(OnLoadCentroListener listener);
    }
        /*List<Centro> loadAllCentros();
        List<Centro> loadCentrosByName(String name);
        boolean deleteCentro(String name);*/


    interface view {
        void showCentros(List<Centro> centros);
        void showMessage(String message);
    }

    interface Presenter{
        void loadAllCentros();
/*        void loadCentrosByName(String name);
        void deleteCentro(String name);*/
    }

}

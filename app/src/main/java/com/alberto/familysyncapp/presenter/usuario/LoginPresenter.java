package com.alberto.familysyncapp.presenter.usuario;

import com.alberto.familysyncapp.domain.Usuario;
import com.alberto.familysyncapp.model.usuario.LoginModel;
import com.alberto.familysyncapp.view.MainActivityView;

public class LoginPresenter {
    private LoginModel loginModel;
    private MainActivityView mainActivityView;

    public LoginPresenter(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
        this.loginModel = new LoginModel();
    }

    public void performLogin(Usuario usuario) {
        loginModel.login(usuario, new LoginModel.OnLoginListener() {
            @Override
            public void onSuccess(String token) {
                // Notificar a la vista sobre el éxito del login
                mainActivityView.onLoginSuccess(token);
            }

            @Override
            public void onError(String errorMessage) {
                // Notificar a la vista sobre el error en el login
                mainActivityView.onLoginError(errorMessage);
            }
        });
    }
}

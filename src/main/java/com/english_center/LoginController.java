package com.english_center;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.utilities.*;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private void login() throws Exception {
        App.setRoot(Constants.FXML_HOMEPAGE, 1616, 939);
    }
}

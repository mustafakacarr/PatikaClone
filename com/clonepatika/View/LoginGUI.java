package com.clonepatika.View;

import com.clonepatika.Helper.Helper;
import com.clonepatika.Model.Operator;
import com.clonepatika.Model.User;

import javax.swing.*;
import java.sql.SQLException;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_username_login;
    private JButton btn_login;
    private JPasswordField fld_password_login;

    public LoginGUI() {
        add(wrapper);
        setSize(500, 350);
        setLocation(Helper.getScreenCenter("x", getSize()), Helper.getScreenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if (!Helper.isFieldEmpty(fld_username_login) || !Helper.isFieldEmpty(fld_password_login)) {
                try {
                    User userObj = User.login(fld_username_login.getText(), fld_password_login.getText());
                    if (userObj != null) {
                        Helper.showMessage("done");
                        dispose();
                        switch (userObj.getType()) {
                            case "educator":
                                //EducatorGUI
                                break;
                            case "operator":
                                OperatorGUI operatorGUI = new OperatorGUI((Operator) userObj);
                                break;
                            case "student":
                                //StudentGUI
                                break;
                            default:
                                dispose();
                                break;
                        }
                    } else {
                        Helper.showMessage("error");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}


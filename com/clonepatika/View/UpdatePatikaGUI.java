package com.clonepatika.View;

import com.clonepatika.Helper.Config;
import com.clonepatika.Helper.Helper;
import com.clonepatika.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patikaname;
    private JButton btn_updatepatika;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika) {
        this.patika = patika;
        setSize(500, 150);
        add(wrapper);
        setLocation(Helper.getScreenCenter("x", getSize()), Helper.getScreenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_patikaname.setText(patika.getName());
        btn_updatepatika.addActionListener((e)->{
            try {
                if (Patika.updatePatika(new Patika(patika.getId(),fld_patikaname.getText()))){
                    Helper.showMessage("done");
                    dispose();
                }else {
                    Helper.showMessage("error");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

}

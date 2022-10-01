package com.clonepatika.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
            if ("Mac OS X".equals(i.getName())) {
                try {
                    UIManager.setLookAndFeel(i.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int getScreenCenter(String cordinate, Dimension size) {
        int point;
        if (cordinate.equalsIgnoreCase("x")) {
            point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
        } else {
            point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().isEmpty();
    }

    public static void showMessage(String str) {
        String message = "";
        String errorTitle = "Hata!";
        int iconNo = 0;
        switch (str) {
            case "fillUp":
                message = "Lütfen boş alan bırakmadığınızdan emin olunuz!";
                break;
            case "loginProblem":
                message = "Lütfen girmiş olduğunuz bilgilerin doğruluğunu kontrol ediniz.";
                break;
            case "done":
                message = "İşlem Başarılı.";
                iconNo = 2;
                break;
            case "error":
                message = "Şu an işleminizi gerçekleştiremiyoruz";
                break;
            case "alreadyExistUsername":
                message = "Bu kullanıcı adı zaten kullanılıyor. Lütfen başka bir kullanıcı adı giriniz.";
                break;
            case "typeisWrong":
                message = "Bu yetki tipi sistemde tanımlı değil. Yetki tipleri educator, student veya operator olabilir.";
                break;
            default:
                message = str;
        }
        JOptionPane.showMessageDialog(null, message, errorTitle, iconNo);
    }

    public static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText(null);
        }
    }
}

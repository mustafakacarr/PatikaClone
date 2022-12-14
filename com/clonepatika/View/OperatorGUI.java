package com.clonepatika.View;

import com.clonepatika.Helper.Config;
import com.clonepatika.Helper.Helper;
import com.clonepatika.Helper.Item;
import com.clonepatika.Model.Course;
import com.clonepatika.Model.Operator;
import com.clonepatika.Model.Patika;
import com.clonepatika.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JPanel pnl_userlist;
    private JTable tbl_userlist;
    private JScrollPane scrll_userlist;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JTextField fld_name;
    private JComboBox cbx_type;
    private JButton btn_adduser;
    private JButton btn_deleteuser;
    private JTextField fld_deleteuser;
    private JLabel lbl_userid;
    private JButton btn_searchuser;
    private JTextField fld_name_search;
    private JTextField fld_username_search;
    private JComboBox cbx_usertype_search;
    private JPanel pnl_search;
    private JPanel pnl_adduser;
    private JTable tbl_patikalist;
    private JScrollPane scrll_patikalist;
    private JPanel pnl_addpatika;
    private JTextField fld_patikaname;
    private JButton btn_addpatika;
    private JTable tbl_courselist;
    private JPanel pnl_addcourse;
    private JTextField fld_coursename;
    private JTextField fld_softwarelang;
    private JButton btn_addcourse;
    private JComboBox cbx_selectpatika;
    private JComboBox cbx_selecteducator;
    private DefaultTableModel mdl_userlist;
    private Object[] row_userlist;
    private final Operator operator;
    private DefaultTableModel mdl_patikalist;
    private Object[] row_patikalist;
    private JPopupMenu patikaMenu;
    private Object[] row_courselist;
    private DefaultTableModel mdl_courselist;


    public OperatorGUI(Operator operator) throws SQLException {
        this.operator = operator;
        Helper.setLayout();
        add(wrapper);
        setSize(800, 550);
        setLocation(Helper.getScreenCenter("x", getSize()), Helper.getScreenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Ho??geldin " + operator.getName());


        //ModelUserList

        mdl_userlist = new DefaultTableModel() {
            @Override

            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_userlist = {"ID", "Ad Soyad", "Kullan??c?? Ad??", "??ifre", "Kullan??c?? T??r??"};
        mdl_userlist.setColumnIdentifiers(col_userlist);
        tbl_userlist.setModel(mdl_userlist);
        tbl_userlist.getTableHeader().setReorderingAllowed(false);
        loadUserModel();
        tbl_userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tbl_userlist.getSelectionModel().addListSelectionListener(e -> {
            String selectedVal = tbl_userlist.getSelectedRow() != -1 ? tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 0).toString() : null;
            fld_deleteuser.setText(selectedVal);
        });

        tbl_userlist.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int userId = Integer.parseInt(tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 0).toString());
                String name = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 1).toString();
                String username = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 2).toString();
                String password = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 3).toString();
                String type = tbl_userlist.getValueAt(tbl_userlist.getSelectedRow(), 4).toString();
                List<String> usertypesArray = new ArrayList<>(3);
                usertypesArray.add("educator");
                usertypesArray.add("operator");
                usertypesArray.add("student");

                try {
                    if (User.getFetch(username) != null && User.getFetch(username).getId() != userId) {
                        Helper.showMessage("alreadyExistUsername");
                    }
                    if (usertypesArray.contains(type)) {
                        if (User.updateUser(new User(userId, name, username, password, type))) {
                            Helper.showMessage("done");
                        } else {
                            Helper.showMessage("error");
                        }
                    } else {
                        Helper.showMessage("typeisWrong");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        // ## ModelUserList

        // ModelPatikaList
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("G??ncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);
        tbl_patikalist.setComponentPopupMenu(patikaMenu);

        updateMenu.addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_patikalist.getValueAt(tbl_patikalist.getSelectedRow(), 0).toString());

            try {
                UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(selectedId));
                updatePatikaGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            loadPatikaModel();
                            loadPatikaCombo();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        deleteMenu.addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_patikalist.getValueAt(tbl_patikalist.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                try {
                    if (Patika.deletePatika(selectedId)) {
                        Helper.showMessage("done");
                        loadPatikaModel();
                        loadPatikaCombo();
                    } else {
                        Helper.showMessage("error");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        mdl_patikalist = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_patikalist = {"ID", "Patika Ad??"};
        mdl_patikalist.setColumnIdentifiers(col_patikalist);
        tbl_patikalist.setModel(mdl_patikalist);
        tbl_patikalist.getTableHeader().setReorderingAllowed(false);
        tbl_patikalist.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patikalist.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = tbl_patikalist.rowAtPoint(point);
                tbl_patikalist.setRowSelectionInterval(selectedRow, selectedRow);

            }
        });
        loadPatikaModel();
        // ## ModelPatikaList

        //ModelCourseList
        mdl_courselist = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_courselist = {"ID", "Kurs Ad??", "Programlama Dili", "??lgili Patika", "E??itmen"};
        mdl_courselist.setColumnIdentifiers(col_courselist);
        tbl_courselist.setModel(mdl_courselist);
        tbl_courselist.getTableHeader().setReorderingAllowed(false);
        loadCourseModel();

        loadPatikaCombo();
        loadEducatorCombo();
        // ##ModelCourseList

        btn_adduser.addActionListener((e) -> {
            if (Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_password)) {
                Helper.showMessage("fillUp");
            } else {
                User userForm = new User(0, fld_name.getText(), fld_username.getText(), fld_password.getText(), cbx_type.getSelectedItem().toString());
                try {
                    if (User.addUser(userForm)) {
                        Helper.showMessage("done");
                        loadUserModel();
                        loadEducatorCombo();
                        Helper.clearFields(fld_name, fld_username, fld_password);
                    } else {
                        Helper.showMessage("alreadyExistUsername");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btn_deleteuser.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                try {
                    if (User.deleteUser(Integer.parseInt(fld_deleteuser.getText()))) {
                        Helper.showMessage("done");
                        loadUserModel();
                        loadEducatorCombo();
                        Helper.clearFields(fld_deleteuser);
                    } else {
                        Helper.showMessage("error");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btn_searchuser.addActionListener((e) -> {
            String name = fld_name_search.getText();
            String username = fld_username_search.getText();
            String type = cbx_usertype_search.getSelectedItem().toString();
            try {
                ArrayList<User> listUsers = User.searchUser(User.searchQuery(name, username, type));
                loadUserModel(listUsers);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btn_logout.addActionListener(e -> {
                    dispose();
                    LoginGUI loginGUI = new LoginGUI();
                }
        );
        btn_addpatika.addActionListener(e -> {
            Patika patika = new Patika(0, fld_patikaname.getText());
            try {
                if (Patika.addPatika(patika)) {
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                } else {
                    Helper.showMessage("error");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        btn_addcourse.addActionListener(e -> {
            String courseName = fld_coursename.getText();
            String softwareLang = fld_softwarelang.getText();
            Item educatorItem = (Item) cbx_selecteducator.getSelectedItem();
            Item patikaItem = (Item) cbx_selectpatika.getSelectedItem();
            try {
                Course course = new Course(0, courseName, softwareLang, educatorItem.getKey(), patikaItem.getKey());
                if (Course.addCourse(course)) {
                    Helper.showMessage("done");
                    Helper.clearFields(fld_softwarelang, fld_coursename);
                    loadCourseModel();
                } else {
                    Helper.showMessage("error");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void loadUserModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_userlist.getModel();
        clearModel.setRowCount(0);
        for (User user : User.showUsers()) {
            row_userlist = new Object[]{user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getType()};
            mdl_userlist.addRow(row_userlist);
        }
    }

    public void loadUserModel(ArrayList<User> list) throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_userlist.getModel();
        clearModel.setRowCount(0);
        for (User user : list) {
            row_userlist = new Object[]{user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getType()};
            mdl_userlist.addRow(row_userlist);
        }
    }

    public void loadPatikaModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patikalist.getModel();
        clearModel.setRowCount(0);
        for (Patika patika : Patika.showPatikaList()) {
            row_patikalist = new Object[]{patika.getId(), patika.getName()};
            mdl_patikalist.addRow(row_patikalist);
        }
    }

    public void loadCourseModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_courselist.getModel();
        clearModel.setRowCount(0);
        for (Course course : Course.showCourseList()) {
            row_courselist = new Object[]{course.getId(), course.getName(), course.getLang(), course.getPatika().getName(), course.getEducator().getName()};
            mdl_courselist.addRow(row_courselist);
        }
    }

    public void loadPatikaCombo() throws SQLException {
        cbx_selectpatika.removeAllItems();
        for (Patika patika : Patika.showPatikaList())
            cbx_selectpatika.addItem(new Item(patika.getId(), patika.getName()));
    }

    public void loadEducatorCombo() throws SQLException {
        cbx_selecteducator.removeAllItems();
        for (User educator : User.searchUser(User.searchQuery("", "", "educator")))
            cbx_selecteducator.addItem(new Item(educator.getId(), educator.getName()));
    }
}

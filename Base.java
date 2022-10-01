import com.clonepatika.Helper.DBConnector;
import com.clonepatika.Model.Operator;
import com.clonepatika.Model.User;
import com.clonepatika.View.OperatorGUI;

import java.sql.SQLException;

public class Base {
    public static void main(String[] args) throws SQLException {
        final Operator operator=new Operator(1,"Operatör","12","12","");

        OperatorGUI operatorGUI=new OperatorGUI(operator);
        try {
            System.out.println(User.showUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

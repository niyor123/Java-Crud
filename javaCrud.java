package CRUD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class javaCrud {
    private JPanel Main;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public static void main(String[] args) {
        JFrame frame = new JFrame("javaCrud");
        frame.setContentPane(new javaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton SAVEButton;
    private JButton DELETEButton;
    private JButton UPDATEButton;
    private JTextField textField4;
    private JButton SEARCHButton;

    Connection con;
    PreparedStatement pst;

    public javaCrud() {
        Connect();

        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty;
                name = textField1.getText();
                price = textField2.getText();
                qty = textField3.getText();
                try
                {
                    pst = con.prepareStatement("insert into gbproducts(pname,price,qty)values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added");
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    String pid = textField4.getText();
                    pst = con.prepareStatement("select pname,price,qty from gbproducts where pid = ?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        textField1.setText(name);
                        textField2.setText(price);
                        textField3.setText(qty);
                    } else {
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product Id");
                        textField4.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty,pid;
                name = textField1.getText();
                price = textField2.getText();
                qty = textField3.getText();
                pid = textField4.getText();

                try
                {
                    pst = con.prepareStatement("update gbproducts set pname = ?,price = ?,qty = ? where pid = ?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Successfully Updated");
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                    textField4.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid;
                bid = textField4.getText();

                try
                {
                    pst = con.prepareStatement("delete from gbproducts where pid = ?");
                    pst.setString(1,bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Successfully Deleted");
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                    textField4.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void Connect() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/products","root","");
            System.out.println("Success...");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


}

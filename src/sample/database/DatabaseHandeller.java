package sample.database;

import com.mysql.cj.log.Log;
import sample.controller.LoginController;
import sample.model.Accounts;
import sample.model.Funds;
import sample.model.Staff;
import sample.model.User;

import java.sql.*;

public class DatabaseHandeller {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private int id=0;

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
      //  System.out.println(connection.getCatalog());
        return connection;
    }




    public ResultSet getAccuntNo() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT MAX(accountno) FROM accounts");
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }


    public int getMobileVerify(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE mobileno=?");
        preparedStatement.setString(1,user.getMobileno());
        resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
        }
        if(c==0) {
            return 0;
        }
        else return 1;
    }

    public int getEmailVerify(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE emailid=?");
        preparedStatement.setString(1,user.getEmailId());
        resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
        }
        if(c==0) {
            return 0;
        }
        else return 1;
    }

    public void userPersonalDetailsSignup(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("INSERT INTO users(firstname,lastname,idcard,idcardno,mobileno,emailid,gender,meritalstatus,religion,dob,age,pincode,city,address,lastupdatetime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,user.getFirstname());
        preparedStatement.setString(2,user.getLastname());
        preparedStatement.setString(3,user.getIdcard());
        preparedStatement.setString(4,user.getIdcardno());
        preparedStatement.setString(5,user.getMobileno());
        preparedStatement.setString(6,user.getEmailId());
        preparedStatement.setString(7,user.getGender());
        preparedStatement.setString(8,user.getMeritalstatus());
        preparedStatement.setString(9,user.getReligion());
        preparedStatement.setString(10,user.getDob());
        preparedStatement.setString(11,user.getAge());
        preparedStatement.setString(12,user.getPincode());
        preparedStatement.setString(13,user.getCity());
        preparedStatement.setString(14,user.getAddress());
        preparedStatement.setTimestamp(15,user.getLastupdate());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT id FROM users WHERE mobileno=?");
        preparedStatement.setString(1,user.getMobileno());
        resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            id=resultSet.getInt("id");
        }
    }

    public int getUsernameVerify(Accounts accounts) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM accounts WHERE username=?");
        preparedStatement.setString(1,accounts.getUsername());
        resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
            System.out.println(c);
        }
        if(c==0) {
            return 0;
        }
        else return 1;
    }

    public void userAccountDetailsSignup(Accounts accounts) throws SQLException, ClassNotFoundException {
            preparedStatement = getConnection().prepareStatement("INSERT INTO accounts (userid,accountno,ifsc,balance,accounttype,username,password,securityq,answar,photo,pin,latupdate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, accounts.getAccuntNo());
            preparedStatement.setString(3, accounts.getIfscno());
            preparedStatement.setString(4, accounts.getBalance());
            preparedStatement.setString(5, accounts.getAccountType());
            preparedStatement.setString(6, accounts.getUsername());
            preparedStatement.setString(7, accounts.getPassword());
            preparedStatement.setString(8, accounts.getSecurityQ());
            preparedStatement.setString(9, accounts.getAnswar());
            preparedStatement.setBinaryStream(10, accounts.getPhoto());
            preparedStatement.setString(11,"");
            preparedStatement.setTimestamp(12,accounts.getLastUpdate());
            preparedStatement.executeUpdate();
           // preparedStatement.clearParameters();
            preparedStatement.close();
    }

    public ResultSet getUserAccount(Accounts accounts) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM accounts WHERE username=? AND password=?");
        preparedStatement.setString(1,accounts.getUsername());
        preparedStatement.setString(2,accounts.getPassword());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getUserInformation() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE id=?");
        preparedStatement.setInt(1, LoginController.lUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getAccountInformation() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM accounts WHERE userid=?");
        preparedStatement.setInt(1, LoginController.lUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void setFund(Funds fund) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("INSERT INTO fund (useraccountno,userid,transactiondate,accounttype,accountno,credit,debit,totalammount) VALUES (?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,fund.getUserAccountno());
        preparedStatement.setInt(2,LoginController.lUserId);
        preparedStatement.setString(3,fund.getDate());
        preparedStatement.setString(4,fund.getAccountType());
        preparedStatement.setString(5,fund.getAccountno());
        preparedStatement.setInt(6,0);
        preparedStatement.setInt(7,fund.getDebit());
        preparedStatement.setInt(8,fund.getTotalammount());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public void updateAmountUser(int amount) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE accounts SET balance=? WHERE userid=?");
        preparedStatement.setString(1,String.valueOf(amount));
        preparedStatement.setInt(2,LoginController.lUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet getTransaction() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM fund WHERE userid=?");
        preparedStatement.setInt(1,LoginController.lUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void updatePin(Accounts accounts) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE accounts SET pin=? WHERE userid=?");
        preparedStatement.setString(1,accounts.getPin());
        preparedStatement.setInt(2,LoginController.lUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet getPin() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT pin FROM accounts WHERE userid=?");
        preparedStatement.setInt(1, LoginController.lUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getStaffs(Staff staff) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM staffs WHERE username=? AND password=?");
        preparedStatement.setString(1,staff.getUsername());
        preparedStatement.setString(2,staff.getPassword());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getStaffInformation() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM staffs WHERE idstaffs=?");
        preparedStatement.setInt(1, LoginController.sUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getAllAccounts() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM accounts");
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getManager() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT designation from staffs Where idstaffs=?");
        preparedStatement.setInt(1,LoginController.sUserId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void getUpdateUser(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE users SET firstname=?,lastname=?,idcard=?,idcardno=?,mobileno=?,emailid=?,gender=?,meritalstatus=?,religion=?,dob=?,age=?,pincode=?,city=?,address=?,lastupdatetime=? WHERE id=?");
        preparedStatement.setString(1,user.getFirstname());
        preparedStatement.setString(2,user.getLastname());
        preparedStatement.setString(3,user.getIdcard());
        preparedStatement.setString(4,user.getIdcardno());
        preparedStatement.setString(5,user.getMobileno());
        preparedStatement.setString(6,user.getEmailId());
        preparedStatement.setString(7,user.getGender());
        preparedStatement.setString(8,user.getMeritalstatus());
        preparedStatement.setString(9,user.getReligion());
        preparedStatement.setString(10,user.getDob());
        preparedStatement.setString(11,user.getAge());
        preparedStatement.setString(12,user.getPincode());
        preparedStatement.setString(13,user.getCity());
        preparedStatement.setString(14,user.getAddress());
        preparedStatement.setTimestamp(15,user.getLastupdate());
        preparedStatement.setInt(16,LoginController.lUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void getUpdateAccount(Accounts accounts) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE accounts SET accounttype=?,answar=?,photo=?,pin=?,latupdate=?  WHERE userid=?");
        preparedStatement.setString(1, accounts.getAccountType());
        preparedStatement.setString(2, accounts.getAnswar());
        preparedStatement.setBinaryStream(3, accounts.getPhoto());
        preparedStatement.setString(4,accounts.getPin());
        preparedStatement.setTimestamp(5,accounts.getLastUpdate());
        preparedStatement.setInt(6,LoginController.lUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet getAccountNumber() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT userid,ifsc,accountno,accounttype FROM accounts");
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getUserName(int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT firstname,lastname FROM users where id=?");
        preparedStatement.setInt(1,userId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getBenifisharyAccount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT userid,ifsc,accountno,balance,accounttype FROM accounts");
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void updateAmountUser(int amount,int userId) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE accounts SET balance=? WHERE userid=?");
        preparedStatement.setString(1,String.valueOf(amount));
        preparedStatement.setInt(2,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void setCreditFund(Funds fund,int userId) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("INSERT INTO fund (useraccountno,userid,transactiondate,accounttype,accountno,credit,debit,totalammount) VALUES (?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,fund.getUserAccountno());
        preparedStatement.setInt(2,userId);
        preparedStatement.setString(3,fund.getDate());
        preparedStatement.setString(4,fund.getAccountType());
        preparedStatement.setString(5,fund.getAccountno());
        preparedStatement.setInt(6,fund.getCredit());
        preparedStatement.setInt(7,fund.getDebit());
        preparedStatement.setInt(8,fund.getTotalammount());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public ResultSet getBalance(int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT balance FROM accounts WHERE userid=?");
        preparedStatement.setInt(1,userId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
}

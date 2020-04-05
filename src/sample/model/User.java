package sample.model;

import java.sql.Timestamp;

public class User {
    private String firstname,lastname,idcard,idcardno,mobileno,gender,meritalstatus,religion,dob,age,pincode,city,address,emailId;
    private int id;
    private Timestamp lastupdate;


    public User() {
    }

    public User(String firstname, String lastname, String idcard, String idcardno, String mobileno,String emailId, String gender, String meritalstatus, String religion, String dob, String age, String pincode, String city, String address,Timestamp lastupdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.idcard = idcard;
        this.idcardno = idcardno;
        this.mobileno = mobileno;
        this.emailId=emailId;
        this.gender = gender;
        this.meritalstatus = meritalstatus;
        this.religion = religion;
        this.dob = dob;
        this.age = age;
        this.pincode = pincode;
        this.city = city;
        this.address = address;
        this.lastupdate=lastupdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMeritalstatus() {
        return meritalstatus;
    }

    public void setMeritalstatus(String meritalstatus) {
        this.meritalstatus = meritalstatus;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }
}

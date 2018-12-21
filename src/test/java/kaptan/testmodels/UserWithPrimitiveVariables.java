package kaptan.testmodels;

import kaptan.annotations.MustBeNonEmpty;
import kaptan.annotations.MustBeNonNull;

public class UserWithPrimitiveVariables {
    @MustBeNonNull
    private String username;
    @MustBeNonEmpty
    private String password;
    private int age;
    private long serialNo;
    private double height;
    private byte favoriteByte;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public double getHeight() {
        return height;
    }

    public byte getFavoriteByte() {
        return favoriteByte;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setFavoriteByte(byte favoriteByte) {
        this.favoriteByte = favoriteByte;
    }
}

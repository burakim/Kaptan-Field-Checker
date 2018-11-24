package testmodels;

import kaptan.annotations.*;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class UserWithRegexAndSizeAndIntervalConstraint {

    @MustBeNonNull
    @MustBeNonEmpty
    private Queue<String> waitingFindings;

    @EnforceIntervalConstraint(max = 2300,min = 1)
    private long idNumber;

    @EnforceIntervalConstraint(min = 0,max = 1)
    private byte favoriteByte;

    @EnforceIntervalConstraint(min = 100,max = 233)
    private double height;

    @MustBeNonNull
    @EnforceSizeConstraint(min = -1,max = 3)
    private LinkedList<String> interest;

    @MustBeNonEmpty
    @MustBeNonNull
    @EnforceRegexRule("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%_]).*$")
    private String name;

    public void addWaitingFindings(String name)
    {
        waitingFindings.add(name);
    }

    public void removeWaitingLastFinding()
    {
        waitingFindings.remove();
    }

    public void addInterest(String interestStr)
    {
        interest.add(interestStr);
    }

    public UserWithRegexAndSizeAndIntervalConstraint()
    {
        waitingFindings = new PriorityQueue<>();
        interest = new LinkedList<>();
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public byte getFavoriteByte() {
        return favoriteByte;
    }

    public void setFavoriteByte(byte favoriteByte) {
        this.favoriteByte = favoriteByte;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<String> getWaitingFindings() {
        return waitingFindings;
    }

    public void setWaitingFindings(Queue<String> waitingFindings) {
        this.waitingFindings = waitingFindings;
    }
    public LinkedList<String> getInterest() {
        return interest;
    }

    public void setInterest(LinkedList<String> interest) {
        this.interest = interest;
    }
}

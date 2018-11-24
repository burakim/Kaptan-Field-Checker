package testmodels;


import kaptan.annotations.EnforceIntervalConstraint;
import kaptan.annotations.EnforceSizeConstraint;

import java.util.ArrayList;

public class UserWithCollectionsAndPrimitives {

    @EnforceSizeConstraint(min = 2,max = 5)
    private ArrayList<String> names;

    @EnforceIntervalConstraint(min = 18,max = 60)
    int age;

    public UserWithCollectionsAndPrimitives()
    {
        names = new ArrayList<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void addName(String name)
    {
        names.add(name);
    }
    public void removeLastName()
    {
        names.remove(names.size()-1);
    }
}

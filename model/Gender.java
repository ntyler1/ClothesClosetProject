package model;

public class Gender {
    private String name;
    private int value;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Gender(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public Gender(int value){
        if(value == 1)
            this.name = "Female";
        else
            this.name = "Male";
        this.value = value;
    }
}
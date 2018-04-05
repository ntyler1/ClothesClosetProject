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

	public Gender(String name) {
		this.name = name;
		if(name.equals("Male"))
			this.value = 1;
		else
			this.value = 0;
	}

	public Gender(int value){
		if(value == 0)
			this.name = "Female";
		else
			this.name = "Male";
		this.value = value;
	}

}
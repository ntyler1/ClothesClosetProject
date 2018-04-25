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
                else if(name.equals("Female"))
			this.value = 0;
                else
                    this.value = 2;
	}

	public Gender(int value){
		if(value == 0)
			this.name = "Female";
                else if(value == 1)
			this.name = "Male";
                else 
                    this.name = "Unisex";
		this.value = value;
	}

}
package dto;

public class Book {
	private String code;
	private String name;
	
	public Book(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}
	
	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String toString(){
		return firstName + " " + lastName;
	}
}

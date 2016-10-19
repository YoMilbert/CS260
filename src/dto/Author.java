package dto;

public class Author {

	private int number;
	private String firstName;
	private String lastName;
	
	public Author(int number, String firstName, String lastName){
		this.number = number;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getNumber(){
		return number;
	}
	public void setNumber(int number){
		this.number = number;
	}
	
	public String getFirstName(){
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String toString(){
		return firstName.trim() + " " + lastName.trim();
	}
}

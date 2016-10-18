package dto;

public class Publisher {

	private int code;
	private String name;
		
	public Publisher(int code, String name){
		this.code = code;
		this.name = name;
	}
		
	public int getNumber(){
		return code;
	}
		
	public void setNumber(int code){
		this.code = code;
	}
	
	public String getFirstName(){
		return name;
	}

	public void setFirstName(String name){
		this.name = name;
	}
		
	public String toString(){
		return name + " " + code;
	}
		
}

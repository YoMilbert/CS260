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
		return name;
	}

	public void setLastName(String name){
		this.name = name;
	}
	
	public String toString(){
		return code + " " + name;
	}
}

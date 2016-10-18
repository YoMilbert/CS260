package dto;

public class Publisher {

	private String code;
	private String name;
		
	public Publisher(String code, String name){
		this.code = code;
		this.name = name;
	}
		
	public String getCode(){
		return code;
	}
		
	public void setCode(String code){
		this.code = code;
	}
	
	public String getFirstName(){
		return name;
	}

	public void setFirstName(String name){
		this.name = name;
	}
		
	public String toString(){
		return name;
	}
		
}

package dto;

public class Branch {

	private int onHand;
	private String name;
	
	public Branch(int onHand, String name){
		this.onHand = onHand;
		this.name = name;
	}
	
	public int getOnHand(){
		return onHand;
	}
	
	public void setOnHand(int onHand){
		this.onHand = onHand;
	}
	
	public String getBranchName(){
		return name;
	}

	public void setBranchName(String name){
		this.name = name;
	}
	
	public String toString(){
		return name.trim() + " - " + onHand;
	}
}

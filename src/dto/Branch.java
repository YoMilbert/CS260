package dto;

public class Branch {

	private int onHand;
	private String branchName;
	
	public Branch(int onHand, String branchName){
		this.onHand = onHand;
		this.branchName = branchName;
	}
	
	public int getOnHand(){
		return onHand;
	}
	public void setOnHand(int onHand){
		this.onHand = onHand;
	}
	
	public String getBranchName(){
		return branchName;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}
	
	public String toString(){
		return branchName + " " + onHand;
	}
}

package dto;

public class Publisher {

		private int publisher_Code;
		private String publisher_Name;
		
		public Publisher(int publisher_Code, String publisher_Name){
			this.publisher_Code = publisher_Code;
			this.publisher_Name = publisher_Name;
		}
		
		public int getNumber(){
			return publisher_Code;
		}
		public void setNumber(int publisher_Code){
			this.publisher_Code = publisher_Code;
		}
		
		public String getFirstName(){
			return publisher_Name;
		}

		public void setFirstName(String publisher_Name){
			this.publisher_Name = publisher_Name;
		}
		
		public String toString(){
			return publisher_Name + " " + publisher_Code;
		}
		
}

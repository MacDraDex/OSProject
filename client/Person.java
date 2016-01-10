//https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
import java.io.Serializable;

public class Person implements Serializable{
	//Empty String variables that stores username and password records
	String userName = "";
	String userPass = "";
	
	// loginProgress is set to false and it will change to true when data will be entered
	boolean  loginProgess= false;
	
	Person(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}
	//Constructor Person that stores a method resetObjectData that sets values to null and sets loginProgress to false 
	Person(){
		resetObjectData();
	}
	
	// This method wipes all user data from previous input when it's called.
	public void resetObjectData() {
		this.userName = "";
		this.userPass = "";
		this.loginProgess = false;
	}
	
	// GETTERS and SETTERS for Person USERNAME and PASSWORD
	public void setPersonName(String userName) {
		this.userName = userName;
	}
	
	public void setPersonPassword(String userPass) {
		this.userPass = userPass;
	}
	
	public void setLoginProgress(boolean loginProgess) {
		this.loginProgess = loginProgess;
	}
	
	public String getPersonName() {
		return userName;
	}
	
	public String getPersonPassword() {
		return userPass;
	}
	
	public boolean getLoginState() {
		return loginProgess;
	}
}// End of class Person


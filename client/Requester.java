import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Requester{
	// Instance Variables
	Socket requestSocket;
	
	// https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html
	ObjectOutputStream out;
 	ObjectInputStream in;
 	// Variables that holds data about ip address,login,password and message that 
 	String username, password, ipaddress;
 	String message="";
 	
 	// Login State is set to a false ,so when a user inputs right credentials it will quit a do-while loop and proceed to another step.
 	boolean loginState = false;
 	
 	Scanner stdin;
 	
	/*
	 * Empty constructor
	 * Requester(){}
	 */
	
	public void run()
	{
		stdin = new Scanner(System.in);
		try{
			
			/*=============================================================================
			 * Object of Person that stores username and password data.
			 * Class Person contains getters and setters in order 
			 *=============================================================================*/
			Person newLogin = new Person();
			Person receivedLogin = new Person();
			//=============================================================================
			//1. creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to "+ ipaddress +" in port 2004");
			//=============================================================================
			//2. get Input and Output streams
			//Set up output stream
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			//Set up input stream
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("==========================================");
			System.out.println("\t WELCOME TO THE DATABASE");
			System.out.println("==========================================");
			System.out.println("IN ORDER TO GET A ACCESS TO THE DATABASE\n YOU MUST ENTER USERNAME AND PASSWORD\n THAT MATCHES A RECORD ON A SERVER\ne.g./i.e. username= martin, password=hynes");
			System.out.println("==========================================");
			//=============================================================================
			//3: Communicating with the server
			do {
				
				receivedLogin.resetObjectData();
				newLogin.resetObjectData();
				try
				{	
						message = (String)in.readObject();
						System.out.println(message);						
						
						/*
						 *  Asks user to enter username data which is sent later to object
						 *  same process for password, data is verified and compared on the client - server side 
						 */
						
						System.out.print("Enter username> : " );
						username = stdin.next();
						newLogin.setPersonName(username);
						System.out.println("Username entered by a user> " + username);
						
						System.out.print("Enter password> : ");
						password = stdin.next();
						newLogin.setPersonPassword(password);
						System.out.println("Password entered by a user> " + password);
						
						// Displays data entered by a user on the screen
						System.out.println("Details entered> " + username+ " " + password);
						
						
						sendPerObject(newLogin);
						
						message = (String)in.readObject();
						System.out.println("==========================================");
						System.out.println(message);
				
						receivedLogin = (Person)in.readObject();
						
						
						if(receivedLogin.getLoginState() == true) {
							loginState = true;
							message = (String)in.readObject();
							System.out.println(message);
						} else {
							loginState = false;
						}	
				}
				catch(ClassNotFoundException classNot)
				{
					System.err.println("data received in unknown format");
				}
			} while(loginState == false);
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	
	// Method that checks the message from the client and sends a respond to a server
	public void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public void sendPerObject(Person personObject)
	{
		try{
			out.writeObject(personObject);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}// End of class Requester

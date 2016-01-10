import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServer {
   
	static List<Person> listOfUsers = new ArrayList<Person>();
	
	public static void main(String[] args) throws Exception {
		
		ServerSocket m_ServerSocket = new ServerSocket(2004,10);
	    int id = 0;
        
          /* Elements stored in (List<Person> listOfUsers) . 
         * Each element in the list stores a username and  
         * password for each user.*/
        
	    Person loginCred1 = new Person("maciek", "1234");
	    listOfUsers.add(loginCred1);
		
		Person loginCred2 = new Person("jack", "gmit");
		listOfUsers.add(loginCred2);
		
		Person loginCred3 = new Person("john", "healy");
		listOfUsers.add(loginCred3);
		
		Person loginCred4 = new Person("jack", "gmit");
		listOfUsers.add(loginCred4);
		
		Person loginCred5 = new Person("john", "healy");
		listOfUsers.add(loginCred5);
		
		Person loginCred6 = new Person("martin", "hynes");
		listOfUsers.add(loginCred6);
        
        //Socket while
	    while (true) {
	      Socket clientSocket = m_ServerSocket.accept();
	      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
	      cliThread.start();
	    }
		
	}
}

// ClientServerThread
class ClientServiceThread extends Thread {
    
  //VARIABLES    
  Socket clientSocket;
  String message;
  int clientID = -1;
  
  //VARIABLES OF TYPE BOOLEAN
  boolean userFound = false;
  boolean life = true;
  boolean loginState = false;
 
  
  ObjectOutputStream out;
  ObjectInputStream in;
  
    
  EchoServer personData = new EchoServer();
  
  ClientServiceThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  public void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			out.reset();
			System.out.println("client> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
  
  public void sendObject(Person personObject) {
	  try{
			out.writeObject(personObject);
			out.flush();
			out.reset();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
  }
  
  public void run() {
    System.out.println("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName());
    try 
    {
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.flush();
		out.reset();
		
		in = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
		
		do{
			sendMessage("Connection successful");
			try
			{
				Person receivedUserObject = new Person();
				Person sendPersonObject = new Person();
				
				userFound = false;
				receivedUserObject = (Person)in.readObject();				
				
				// ==============================================
				// SEARCHES AND VALIDATES LOGIN DETAILS FROM THE LIST
				for(Person personObject : personData.listOfUsers) {
					if(personObject.getPersonName().equals(receivedUserObject.getPersonName())) {
						userFound = true;
						loginState = true;
					}
				}				
				/*
				 * this if-else compares and checks if person object matches the record attached to each user 
				 * in the list, IF matches THEN login successful IF not ELSE Unsuccessful
				 */
				if(userFound == true) {
					sendMessage("Login Successfull!  \n ACCESS GRANTED");
					sendPersonObject.setLoginProgress(true);
					sendObject(sendPersonObject);
				} else {
					sendMessage("Login Unsuccessful!  \n ACCESS DENIED");
					sendPersonObject.setLoginProgress(false);
					sendObject(sendPersonObject);
				}

				receivedUserObject.resetObjectData();
				sendPersonObject.resetObjectData();
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}
    	} while(life);
      
		System.out.println("Ending Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

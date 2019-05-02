package client;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;


public class Client {

     String outMessage="";
     boolean flag=true;
     Socket clientSocket;
	 DataOutputStream MSGToServer;
	 String UserName;
	 BufferedReader b;
	 

	public Client(String ip,int port) throws Exception{
		
	clientSocket= new Socket(ip,port);
	MSGToServer=new DataOutputStream(clientSocket.getOutputStream());
    b=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void Join(String name){
		
		   outMessage="join "+name;
		   UserName=name;
	    
		try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}		
		
		
	}
	
	public  void GetMemberList(int x){
		
		outMessage="get "+ x; 
		
	    try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}			
		
	
	}
	
	public  void Chat(String Source,String Destination,int TTL,String Message){
		
		outMessage="chat"+" "+Source+" "+Destination+" "+TTL+" "+Message;
		
	    try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}			
			
	}
	
	public void groupChat(String Source,int TTL,String Message){
		
		outMessage="groupChat"+" "+Source+" "+TTL+" "+Message;
		
	    try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}					
	}	
	
	
	
	public void Quit(){
		
		outMessage="QUIT";
		
	      try {
			 MSGToServer.writeBytes(outMessage + "\n");
		  } catch (IOException e) {
		  }

		
	}


}

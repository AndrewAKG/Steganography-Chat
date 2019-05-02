package client;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;


public class OldClient implements Runnable{

    static String outMessage="";
    static boolean flag=true;
    static Socket clientSocket=null;
    static DataOutputStream MSGToServer;
    static String UserName;
	
	
	
	public static void main(String[]args) throws Exception{
		

		    
		    String   HN=InetAddress.getLocalHost().getHostName();
			//HN="172.20.10.2";   //write here the ip adress of the server pc    
			clientSocket= new Socket(HN,6010);
			BufferedReader MSGFromUser = new BufferedReader( new InputStreamReader(System.in));
		    MSGToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			new Thread(new OldClient()).start();
			
			
		    while (flag){
			 
		    outMessage = MSGFromUser.readLine();

		    String words[]=outMessage.split(" ");

		    if(outMessage.equals("QUIT") || outMessage.equals("BYE")){
				  Quit();
		    }
		    if(words[0].equals("get")){
				  GetMemberList();
		    }
		    if(words.length==2 && words[0].equals("join")){
				  Join(words[1]);
		    }
		    if(words.length>2 && words[0].equals("chat")){
				  Chat(UserName,words[1],4,outMessage.substring(6+words[1].length()));
		    }


		  }

		  clientSocket.close();
	}
	
	public static void Join(String name){
		
	    if(UserName!=null){
	       System.out.println("You are already connceted");
	       return;
	    }
		   UserName=name;
	    
		try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}		
		
		
	}
	
	public static void GetMemberList(){
		
		if(UserName==null){
			System.out.println("You need to connect to the Server");
		    return;
		}
	    try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}			
		
	
	}
	
	public static void Chat(String Source,String Destination,int TTL,String Message){
		
		if(UserName==null){
			System.out.println("You need to connect to the Server");
		    return;
		}
		
		outMessage="chat"+" "+Source+" "+Destination+" "+TTL+" "+Message;
		
	    try {
			MSGToServer.writeBytes(outMessage + "\n");
		} catch (IOException e) {
		}			
		
		
	}
	public static void Quit(){
		
		if(UserName==null) ;
		else{
		
	      try {
			 MSGToServer.writeBytes(outMessage + "\n");
		  } catch (IOException e) {
		  }
		}
	   System.exit(0);
		
	}

	@Override
	public void run() {
       
		BufferedReader MSGFromServer;
		
		
		

		try {
			MSGFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while(flag){
			String line= MSGFromServer.readLine() ;	
			System.out.println(line);
			
			  if(line.equals("userName already exists"))
			      UserName=null;
			}
		} catch (IOException e) {

		}

		
	}
}
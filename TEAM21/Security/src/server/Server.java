package server;

import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

public class Server implements Runnable  {
	
    static ArrayList<String> UserNames;
    static ArrayList<Socket> UserSockets ;
    static boolean flag=true;
    static ServerSocket serverSocket ;
    static Socket masterSocket;
    static DataOutputStream masterWrite;
    static BufferedReader masterRead;
    Socket CurrentSocket;
    BufferedReader b;
    String clMSG;
    String UserName;
    boolean opened;
    
       public Server(Socket s){
    	CurrentSocket=s;
    	try {
			b=new BufferedReader(new InputStreamReader(CurrentSocket.getInputStream()));
		} catch (IOException e) {
		}
        }
	
	public static void main(String argv[]) throws Exception{
	
    UserNames = new ArrayList<String>();
    UserSockets=new ArrayList<Socket>();
    try{
    serverSocket= new ServerSocket(6004);
    String   HN=InetAddress.getLocalHost().getHostName();
	//String HN="192.168.43.225";   //write here the ip adress of the server pc    
	masterSocket= new Socket(HN,6000);
	masterWrite=new DataOutputStream(masterSocket.getOutputStream());
	//masterRead= new BufferedReader(new InputStreamReader( masterSocket.getInputStream()));
	new Thread(new Server(masterSocket)).start();  
	}
    catch(Exception e){
     flag=false;
     System.out.println("please restart the program or delete the adress used");
    }
         while(flag) {
             Socket connectionSocket = serverSocket.accept();
      		 new Thread(new Server(connectionSocket)).start();      
         }
          }
	
	public void Quit(){
		
		UserNames.remove(UserName);
		UserSockets.remove(CurrentSocket);
		try {
			masterWrite.writeBytes("quit "+UserName+"\n");
		} catch (IOException e) {
		}
		opened=false;
	}
	
	public void JoinResponse(){
		
		String name=clMSG.substring(5);
        try {
			masterWrite.writeBytes(clMSG+"\n");
		} catch (IOException e) {
		} 		
        
		UserNames.add(name);
		UserSockets.add(CurrentSocket);
		UserName=name;
		}
	
	public void Route(int t,String UserMessage,String Destination){
 
	    try {
             if(UserNames.contains(Destination)){
            	 int i=UserNames.indexOf(Destination);
            	 Socket s=UserSockets.get(i);
            	 DataOutputStream d=new DataOutputStream(s.getOutputStream());
            	  d.writeBytes("from "+UserName+" "+UserMessage+"\n");
             }
             else{
               	 if(t>0)
            	   masterWrite.writeBytes("chat "+UserName+" "+Destination+" "+(t-1)+" "+UserMessage+"\n");
            	 else{
              	   masterWrite.writeBytes("chat server "+UserName+" "+2+" "+"user not found"+"\n"); 
            	 }      	 
        }
		} catch (Exception e) {

		}
	}
	
	public void groupRoute(int t,String UserMessage){
		 
	    try {
	    	
	    	for (int i=0;i<UserNames.size();i++){
	    		
	    		if(!UserName.equals(UserNames.get(i))){
	    			
	            	 Socket s=UserSockets.get(i);
	            	 DataOutputStream d=new DataOutputStream(s.getOutputStream());
	            	  d.writeBytes("fromA "+UserName+" "+UserMessage+"\n");	    			
	    		}
	    	}
	    	    	 
		} catch (Exception e) {

		}
	}

	public void run() {

		try {	        
	         opened=true;
	           while(opened){
	        	clMSG=b.readLine();
	        	evaluate();
	        }
		} catch (IOException e) {
		  Quit();
		}              
	}
	public void evaluate(){
        
        try {
                String words[]=clMSG.split(" ");
		 
         if(words[0].equals("quit")){
			Quit();
		    return ;	
		}
			  
		if(words[0].equals("join")){
        	JoinResponse();
            return ;
		}
        if(words[0].equals("get")){
        	masterWrite.writeBytes("get "+UserName+" "+words[1]+"\n");
        	
            return ;
        }
        
        if(words[0].equals("chat")){
            
        	StringTokenizer st=new StringTokenizer(clMSG);	
          	st.nextToken(); //chat
          	UserName=st.nextToken(); //sender
        	String UN=st.nextToken(); //receiver
        	int ttl=Integer.parseInt(st.nextToken()); //ttl
        	String msg=st.nextToken(""); // message
            Route(ttl,msg,UN);
            return;
        }
        
        if(words[0].equals("groupChat")){
            
        	StringTokenizer st=new StringTokenizer(clMSG);	
          	st.nextToken(); //groupChat
          	UserName=st.nextToken(); //sender
        	int ttl=Integer.parseInt(st.nextToken()); //ttl
        	String msg=st.nextToken(""); // message
            groupRoute(ttl,msg);
            return;
        }        
        
        if(words[0].equals("UserNames")){
           int i=UserNames.indexOf(words[1]);   	
           Socket s=UserSockets.get(i);	
      	   DataOutputStream d=new DataOutputStream(s.getOutputStream());
      	   StringTokenizer st=new StringTokenizer(clMSG);
      	   st.nextToken();
      	   st.nextToken();
      	   String g=st.nextToken("");
           d.writeBytes("usernames"+g+"\n");
           return;
        }
        if(words[0].equals("C")){
            int i=UserNames.indexOf(words[1]);   	
            Socket s=UserSockets.get(i);	
       	    DataOutputStream d=new DataOutputStream(s.getOutputStream());
        	d.writeBytes("connected"+"\n");
        	return;
        }
        
        if(words[0].equals("D")){
            int k=0;
        	for(int i=0;i<UserNames.size();i++){
        		if(UserNames.get(i).equals(words[1]))
        		     k=i;
        	}  	
            Socket s=UserSockets.get(k);	
       	    DataOutputStream d=new DataOutputStream(s.getOutputStream());
       	    UserNames.remove(words[1]);
       	    UserSockets.remove(s);
        	d.writeBytes("userName already exists"+"\n");
        	return;
        }
         } catch (Exception e) {
		 }
       }
	}
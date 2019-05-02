package server;

import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;


public class MasterServer implements Runnable  {
	
	
	static ArrayList<ArrayList<String>> UserNames;
	static ArrayList<Socket> ServerSockets ;
    static boolean flag=true;
    static ServerSocket serverSocket ;
    Socket CurrentSocket;
    BufferedReader b;
    String clMSG;
    static String UserName;
    boolean opened;
    
    
    public MasterServer(Socket s){
    	CurrentSocket=s;
		ServerSockets.add(CurrentSocket);
		ArrayList <String>a=new ArrayList<String>();
		UserNames.add(a);
    	try {
			b=new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
		}
    }
	

	public static void main(String argv[]) throws Exception{
	
	UserNames=new ArrayList<ArrayList<String>>();
    ServerSockets=new ArrayList<Socket>();
    
    try{
    serverSocket= new ServerSocket(6000);
    }
    catch(Exception e){
     flag=false;
     System.out.println("please restart the program or delete the adress used");
    }
         while(flag) {
             Socket connectionSocket = serverSocket.accept();
      		 new Thread(new MasterServer(connectionSocket)).start();      
         }
        
	  }
	
	public void Quit(){
		
     for(int i=0;i<UserNames.size();i++){
    	 
    	  UserNames.get(i).remove(UserName);
    	}
		
	}
	
	public String JoinClientResponse(){
		
		
		String name=clMSG.substring(5);
		UserName=name;
		
	     for(int i=0;i<UserNames.size();i++){
	    	 
	    	  if(UserNames.get(i).contains(name))
	    		  return "D "+UserName;
	      }
		
		int i=ServerSockets.indexOf(CurrentSocket);
        UserNames.get(i).add(name);
		
		return "C "+UserName;
	}
	
	public String MemberListResponse(int x){
		
	String s="UserNames "+UserName+" ";	
		
       
	
	if(x==5){
	for(int i=0;i<UserNames.size();i++){
    	   
    	   for(int j=0;j<UserNames.get(i).size();j++){
    	         String name=UserNames.get(i).get(j);
    	        if(!UserName.equals(name))
    	           s=s+name+" ";
    	   }
     }
	}
	else{
		
		x=x-1;
		
 	   for(int j=0;j<UserNames.get(x).size();j++){
	         String name=UserNames.get(x).get(j);
	        if(!UserName.equals(name))
 	           s=s+name+" ";	
		}
 	   
	}
	    s=s+"\n";
		return s;
	}
	
	public void Route(int ttl,String UserMessage,String Destination){
 
	boolean flag1=true;
	  if(ttl<=0) 
		flag1=false;
		
		
	  try {
         int k=-1;
         boolean flag2=false;
         
         for (int i=0;i<UserNames.size()&&flag1;i++){        	 
        	 for(int j=0;j<UserNames.get(i).size();j++){
        		 String name=UserNames.get(i).get(j);
        		   if(name.equals(Destination)){
        		       k=i;
        		       flag2=true;
        		       break;
        		   }
        	 }
        	 if(flag2)
        		 break;
         }
    	 
        if(k==-1 || !flag1){
            DataOutputStream MSGToClient =new DataOutputStream(CurrentSocket.getOutputStream());
            String svMSG="chat server "+UserName+" "+2+" User not found"+"\n";
            MSGToClient.writeBytes(svMSG);
        	return;   	
        }
    	 
    	Socket s=ServerSockets.get(k);
        DataOutputStream MSGToClient =new DataOutputStream(s.getOutputStream());
        String svMSG="chat "+UserName+" "+Destination+" "+(ttl-1)+" "+UserMessage+"\n";
        MSGToClient.writeBytes(svMSG);
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
		}              
	
		
	}
	public void evaluate(){
        
		String svMSG="";
		DataOutputStream  MSGToClient ;
		
        try {
			  MSGToClient =new DataOutputStream(CurrentSocket.getOutputStream());
			  String words[]=clMSG.split(" ");
			  
		
		if(words[0].equals("quit")){
			UserName=words[1];
			Quit();
		    return ;	
		}
			  
		if(words[0].equals("join")){

			svMSG=JoinClientResponse();
            MSGToClient.writeBytes(svMSG+"\n");
      
            return ;
		}
		
		if(words[0].equals("get")){
        	UserName=words[1];
			svMSG=MemberListResponse(Integer.parseInt(words[2]));
		    MSGToClient.writeBytes(svMSG);	
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
        }
		
        } catch (Exception e) {
        	e.printStackTrace();
		}
		
	}


	
}

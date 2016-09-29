package com.network.proxy;

import java.net.* ;
import java.io.* ;
import java.lang.* ;
import java.util.* ;

class ProxyServer {
    public static void main(String args[]) throws IOException{
        
        int localport = -1;
        int remoteport = -1;
        String remotehost = null;
        boolean error = false;
        
        int i = 0;
        Integer parselocalport = new Integer(args[0]);
        Integer parseremoteport = new Integer(args[2]);
        Socket inMsg, outMsg = null;
        ServerSocket Server = null;
		InetAddress localHost = null;
		
        try
        {
			localHost = InetAddress.getLocalHost();
			localport = parselocalport.parseInt(args[0]);
            remotehost = args[1];
            remoteport = parseremoteport.parseInt(args[2]);

        }
        
        catch(Exception e)
        {
            System.err.println("Error: " + e.getMessage() + "\n");
            error = true;
        }
	
		System.out.println("STARTED PROXY ON " + localHost.getHostAddress() +" PORT " + localport + " --> FOR SERVER " + remotehost + " PORT " + remoteport);
        
        if(localport <= 0){
            System.err.println("ERROR : INVALID LOCAL PORT " + "\n");
            error = true;
        }
        if(remoteport <=0){
            System.err.println("ERROR : INVALID REMOTE PORT " + "\n");
            error = true;
        }
        if(remotehost == null){
            System.err.println("ERROR : Invalid Remote HOST " + "\n");
            error = true;
        }
               
        if(error)
            System.exit(-1);
        
        try{
            Server = new ServerSocket(localport);
        }
	catch(IOException e) {
			e.printStackTrace();
        }
        
        while(true)
	    {
		try{
		    inMsg = Server.accept();
		    outMsg = new Socket(remotehost, remoteport); 

		    DataProcessorThread inTraffic = new DataProcessorThread(inMsg, outMsg);
		    inTraffic.start();
		    
		    DataProcessorThread outTraffic = new DataProcessorThread(outMsg, inMsg);
		    outTraffic.start();
		} 
		catch (UnknownHostException e) {
		    //Test and make connection to remote host
		    System.err.println("ERROR : UNKNOWN HOST " + remotehost);
		    System.exit(-1);
		} 
		catch(IOException e){
		    System.exit(-2);//continue;
		}

	    }
    }
}
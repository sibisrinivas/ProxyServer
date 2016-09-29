package com.network.proxy;

import java.net.* ;
import java.io.* ;
import java.lang.* ;
import java.util.* ;

class DataProcessorThread extends Thread {
    Socket incoming, outgoing;
    
    DataProcessorThread(Socket in, Socket out){
        incoming = in;
        outgoing = out;
    }
    
    public void run(){
        byte[] buffer = new byte[60];
      int numberRead = 0;
      OutputStream ToClient;
      InputStream FromClient;
      
      try{
          ToClient = outgoing.getOutputStream();      
          FromClient = incoming.getInputStream();
         while( true){
           numberRead = FromClient.read(buffer, 0, 50);
        
	    if(numberRead == -1){
	      incoming.close();
	      outgoing.close();
	    }
	   
	   ToClient.write(buffer, 0, numberRead);
	 }
 
      }
      catch(IOException e) {}
      catch(ArrayIndexOutOfBoundsException e) {}
      
    }
    
}

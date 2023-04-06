import java.net.*;
import java.io.*;

public class SocketClient {
    

    public static void main(String[] args) throws Exception {
        
        
        //Create a socket
        Socket socket = new Socket("localhost",50000);
        
        //Initialise input and output streams associated with the socket
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  

        
        //Connect ds-server
        
        //Send HELO
        String message ="HELO\n";
        out.write(message.getBytes());
        
        // Step 5: Receive OK
        String response = reader.readLine();
        System.out.println(response);
        

        //send AUTH and receive ok
        if(response.equals("OK")) {
            //Send AUTH username
            String username = "SANGGON";
            message = "AUTH " + username + "\n";
            out.write(message.getBytes());
            
            //Receive OK
            response = reader.readLine();
            System.out.println(response);
            
            //String[] data = null;
            if(response.equals("OK")) {
                //While the last message from ds-server is not NONE do // jobs 1 - n
                while(!response.equals("NONE")) {
                    //Send REDY
                    message ="REDY\n";
                    out.write(message.getBytes());
                    
                    
                    //Receive a message one of the following: JOBN, JCPL and NONE
                    response = reader.readLine();
                    System.out.println(response);
                    
                    //Send a GETS message, e.g., GETS All
                    if(response.startsWith("JOBN")) {
                        String[] jobs = response.split(" ");
                        String largestServerType = " ";
                        int largestServerCount = 0;

                        message ="GETS All";
                        out.write(message.getBytes());
                        
                        
                        //Receive DATA nRecs recSize 
                        response = reader.readLine();
                        System.out.println(response);
                        
                        String[] data = response.split(" ");
                        int nRecs = Integer.parseInt(data[1]);
                        int recSize =Integer.parseInt(data[2]);
                        
                        //Send OK
                        message ="OK";
                        out.write(message.getBytes());
                        
                        //For i = 0; i < nRecs; ++i do
                        for(int i = 0; i < nRecs; i++) {
                            //Receive each record
                            String record = reader.readLine();
                            String[] recordingData = record.split("");
                            String serverType = recordingData[4];
                            int serverCounter = Integer.parseInt(recordingData[1]);
                            if(serverCounter > largestServerCount){
                                largestServerCount = serverCounter;
                                largestServerType = serverType;
                            }
                            
                            //Keep track of the largest server type and the number of servers of that type
                            //code to keep track of the largest server type and number of servers of that type
                        }
                        
                        //Send OK
                        message ="OK";
                        out.write(message.getBytes());
                        
                        
                        //Receive
                        response = reader.readLine();
                        System.out.println(response);
                    }
                    
                    //If the message received  JOBN then
                    if(response.equals("JOBN")) {
                        message ="OK";
                        out.write(message.getBytes());
                        /* 
                        String[] recordingData = record.split("");
                        message =("SCHD"+ recordingData[2]+largestServerType+"0");
                        out.write(message.getBytes());
                        */
                      

                    //send QUIT
 }                  message = "QUIT";
                    out.write(message.getBytes());
                    //receive QUIT
                    response = reader.readLine();
                    System.out.println(response); 
                }
            
            socket.close(); //close the socket and streams
            in.close();
            out.close();
        } 
    }
}
}
import java.net.*;
import java.io.*;


public class SocketClient {
    

    public static void main(String[] args) throws Exception {
        
        
        //Create a socket
        Socket socket = new Socket("localhost",50000);
        
        //Initialise input and output streams associated with the socket
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  

        
        //Connect ds-server
        
        //Send HELO
        String message ="HELO\n";
        out.write(message.getBytes());
        
        // Receive OK
        String response = reader.readLine();
        System.out.println(response);
        

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

                    while(!response.equals("NONE")){
                    
                    //if the message received is JOBN
                    if(response.startsWith("JOBN")) {

                        String[] jobs = response.split(" "); 
                        String largestServerType = " ";
                        int largestServerCount = 0;

                         //Send GETS message, e.g..GETS ALL
                         message ="GETS All\n";
                         out.write(message.getBytes()); 

                         //Receive DATA nRecs recSize 
                        response = reader.readLine();
                        System.out.println(response); 

                        String[] data = response.split(" ");
                        int nRecs = Integer.parseInt(data[1]);
                        int recSize =Integer.parseInt(data[2]);

                        //Send OK
                        message ="OK\n";
                        out.write(message.getBytes());
                        
                        //For i = 0; i < nRecs; ++i do
                        //receive each record and keep track of the largest server type
                        for(int i = 0; i < nRecs; i++) {

                        response = reader.readLine();
                        System.out.println(response); 
                            
                        
                        //String record = reader.readLine(); //no need this line
                        String[] serverDetails= response.split(" "); //added a new line
                        String serverType = serverDetails[0]; // swapped with 4 with 0
                        int serverCounter = Integer.parseInt(serverDetails[1]);
                        if(serverCounter > largestServerCount){
                            largestServerCount = serverCounter;
                            largestServerType = serverType;
                    
                            }
                        }
                            //Send OK
                            message ="OK\n";
                            out.write(message.getBytes());
                            //schedule the job
                            message ="SCHD"+jobs[2]+ " " + largestServerType + " 0\n";
                            out.write(message.getBytes());
                                         
                        }
                        
                        else if(response.equals("JCPL")) {
                        //Send REDY
                        message ="REDY";
                        out.write(message.getBytes());
                        }

                         //receive message(JOBN, JCPL, NONE)
                         response = reader.readLine();
                         System.out.println(response); 

                    }

                         //send QUIT
                        message = "QUIT\n";
                        out.write(message.getBytes());

                        //receive QUIT
                        response = reader.readLine();
                        System.out.println(response); 

                        }

                    }
             
                }
            
            socket.close(); //close the socket and streams
            in.close();
            out.close();
        } 
    
}




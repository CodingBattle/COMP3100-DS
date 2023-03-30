import java.net.*;
import java.io.*;

public class SocketClient {
    
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 50000;

    public static void main(String[] args) throws IOException {
        
        
        // Step 1: Create a socket
        Socket socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
        
        // Step 2: Initialise input and output streams associated with the socket
       
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        
        // Step 3: Connect ds-server
        
        // Step 4: Send HELO
        String message ="HELO\n";
        out.write(message.getBytes());
        
        // Step 5: Receive OK
        String response = reader.readLine();
S       System.out.println(response);
        

        //send AUTH and receive ok
        if(response.equals("OK")) {
            // Step 6: Send AUTH username
            String username = "SANGGON";
            message = "AUTH " + username + "\n";
            out.write(message.getBytes());
            
            // Step 7: Receive OK
            response = reader.readLine();
            System.out.println(response);
            
            if(response.equals("OK")) {
                // Step 8: While the last message from ds-server is not NONE do // jobs 1 - n
                while(!response.equals("NONE")) {
                    // Step 9: Send REDY
                    message ="REDY\n";
                    out.write(message.getBytes());
                    
                    
                    // Step 10: Receive a message // typically one of the following: JOBN, JCPL and NONE
                    response = reader.readLine();
                    System.out.println(response);
                    
                    // Step 11: Send a GETS message, e.g., GETS All
                    
                    if(response.equals("JOBN")) {
                        message ="GETS All";
                        out.write(message.getBytes());
                        
                        
                        // Step 12: Receive DATA nRecs recSize // e.g., DATA 5 124
                        response = reader.readLine();
                        System.out.println(response);
                        
                        String[] data = response.split("\\s+");
                        int nRecs = Integer.parseInt(data[1]);
                        
                        // Step 13: Send OK
                        message ="OK";
                        out.write(message.getBytes());
                        
                        // Step 14: For i = 0; i < nRecs; ++i do
                        for(int i = 0; i < nRecs; i++) {
                            // Step 15: Receive each record
                            String record = bufferedReader.readLine();
                            String[] recordData = record.split("\\s+");
                            String serverType = recordData[4];
                            
                            
                            // Step 16: Keep track of the largest server type and the number of servers of that type
                            // code to keep track of the largest server type and number of servers of that type
                        }
                        
                        // Step 18: Send OK
                        message ="OK";
                        out.write("OK");
                        
                        
                        // Step 19: Receive .
                        response = reader.readLine();
                        System.out.println(response);
                    }
                    
                    // Step 20: If the message received at Step 10 is JOBN then
                    if(response.equals("JOBN")) {
                        // Step 21: Schedule a job // SCHD
                        // code to schedule the job

 }                  message = "QUIT";
                    out.write(message.getBytes());; // Step 24: send QUIT
                    rresponse = reader.readLine();
                    System.out.println(response); // Step 25: receive QUIT
                }
            }
            socket.close(); // Step 26: close the socket
            //inputStream.close();
            //outputStream.close();
        } 
    }
}
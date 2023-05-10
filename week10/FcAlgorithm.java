import java.net.*;
import java.io.*;


public class FcAlgorithm {

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


        String largestServerType = " ";
        int largestServerCount = 0;

        while (true) {
            //Send REDY
            message ="REDY\n";
            out.write(message.getBytes());

            //Receive OK
            response = reader.readLine();
            System.out.println(response);

            if (response.equals("NONE")) {
                break;
            }

            if (response.startsWith("JOBN")) {
                String[] jobParams = response.split(" ");

                // Send a GETS Capable message to find the first server that can handle the job
                message ="GETS Capable" + " " + jobParams[4] + " " + jobParams[5] + " " + jobParams[6] + "\n";
                out.write(message.getBytes());

               
                response = reader.readLine();
                System.out.println(response);

                if (!response.startsWith("DATA")) {
                    response = reader.readLine();
                    System.out.println("Invalid response:" + response);
                    
                }

                String[] dataParams = response.split(" ");
                int nRecs = Integer.parseInt(dataParams[1]);
                for (int i = 0; i < nRecs; i++) {
                    response = reader.readLine();
                    System.out.println(response);
                    String[] serverParams = response.split(" ");
                    if (serverParams[0].equals(".")) {
                        break;
                    }
                    if (Integer.parseInt(serverParams[2]) > largestServerCount) {
                        largestServerType = serverParams[1];
                        largestServerCount = Integer.parseInt(serverParams[2]);
                    }
                }
                //Send OK
                message ="OK\n";
                out.write(message.getBytes());
                

                // Schedule the job to the first server in the list
                message = "SCHD" + jobParams[2] + largestServerType + "0";
                out.write(message.getBytes());
              
                if (!response.equals("OK")) {
                    response = reader.readLine();
                    System.out.println("Invalid response:" + response);
                }
            }
        }
         
        //Send QUIT
        message ="QUIT\n";
        out.write(message.getBytes());
      
        if (!response.equals("QUIT")) {
            response = reader.readLine();
            System.out.println("Invalid response:" + response);
        }

        socket.close();
    }
}
}

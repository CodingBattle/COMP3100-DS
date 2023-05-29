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
        out.flush();
        
        // Receive OK
        String response = reader.readLine();
        System.out.println(response);
 


        if(response.equals("OK")) {
            //Send AUTH username
            String username = "SANGGON";
            message = "AUTH " + username + "\n";
            out.write(message.getBytes());
            out.flush();
            
            //Receive OK
            response = reader.readLine();
            System.out.println(response);


        String largestServerType = " ";
        int largestServerCount = 0;

        while (true) {
            //Send REDY
            message ="REDY\n";
            out.write(message.getBytes());
            out.flush();

            //Receive OK
            response = reader.readLine();
            System.out.println(response);
             

            if (response.startsWith("JOBN")) {
                String[] jobParams = null;
                jobParams = response.split(" ");

                //Send a GETS Capable message to find the first server that can handle the job
                message ="GETS Capable" + " " + jobParams[4] + " " + jobParams[5] + " " + jobParams[6] + "\n";
                out.write(message.getBytes());
                out.flush();
               
                response = reader.readLine();
                System.out.println(response);

                String[] dataParams = null;
                dataParams = response.split(" ");
                int nRecs = Integer.parseInt(dataParams[1]);

                message ="OK\n";
                out.write(message.getBytes());
                out.flush();
              
                for (int i = 0; i < nRecs; i++) {
                    response = reader.readLine();
                    String[] serverParams = null;
                    serverParams = response.split(" ");
                   
                    int core = Integer.parseInt(serverParams[4]);
                    if (core > largestServerCount) {
                        largestServerType = serverParams[1];
                        largestServerCount = core;
                    }
                }
               
                //Send OK
                message ="OK\n";
                out.write(message.getBytes());
                out.flush();
              
                //Schedule the job to the first server in the list
                message = "SCHD" + " " +jobParams[2] + " " +largestServerType + " " +"0\n";
                out.write(message.getBytes());
                out.flush();
                       
            }

                //Send QUIT
                message ="QUIT\n";
                out.write(message.getBytes());
                out.flush();
        }

      }
                socket.close();
        
    }
}

import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  

Socket socket = new Socket("localhost",50000);  
OutputStream out = socket.getOutputStream();
InputStream in = socket.getInputStream();
BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
//DataInputStream din=new DataInputStream(s.getInputStream());  
//DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
//BufferedReader in= new BufferedReader(new InputStreamReader(s.getInputStream()));  

//send HELO and receive ok
String message = "HELO\n";
out.write(message.getBytes());
String response = reader.readLine();
System.out.println(response);

//send AUTH and receive ok
String username = "SANGGON"; // or use System.getProperty("user.name")
message = "AUTH " + username + "\n";
out.write(message.getBytes());
response = reader.readLine();
System.out.println(response);

//send REDY and receive job information
message = "REDY\n";
out.write(message.getBytes());
response = reader.readLine();
System.out.println(response);

// parse job information
String[] parts = response.split(" ");
int jobID = Integer.parseInt(parts[2]);
int numCPUs = Integer.parseInt(parts[4]);

// schedule job and receive confirmation
message = "SCHD " + jobID + " 0\n";
out.write(message.getBytes());
response = reader.readLine();
System.out.println(response);

// repeat for remaining jobs
message = "SCHD " + numCPUs + " 0\n";
out.write(message.getBytes());
response = reader.readLine();
System.out.println(response);

// close the socket
socket.close();  

// Send the QUIT command to the server to terminate the simulation
out.write("QUIT\n".getBytes());
out.flush();
        
// Close the socket and streams
socket.close();
in.close();
out.close();

}}  
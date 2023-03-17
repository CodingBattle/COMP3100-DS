import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("localhost",50000);  
//DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
  
String str="HELO",str2="OK";
String username = System.getProperty("SANGGON");
while(!str.equals(("HELO\n").getBytes())){  
str=br.readLine();  
dout.write(str);  
dout.flush();  
str2=din.readLine();  
System.out.println("Server says: "+ str2);  
}  
  
dout.close();  
s.close();  
}}  
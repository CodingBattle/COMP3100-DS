import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("localhost",50000);  
DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));  
  
String str="",str2="";  
String username=null;
try { 
    username = System.getProperty("user.name");
    if(username==null){
        System.out.println("System.getProperty(\"user.name\") return: NULL");
    } else {
        System.out.println("System.getProperty(\"user.name\") return:" + username);
    }
    } catch(Exception e){
        //error
        System.out.println(e.toString());
    }
    
//logger.info("User Name: " + username);

//create an object of SmtpClient
SmtpClient client = new SmtpClient("smtp.gmail.com");
//Set username,password,port and sercurity options
client=setUsername("your.email@gmail.com");
client=setPassword("your.password");
client.setPort(50000);
//send emails
client.send(new MailMessage("sender@domain.com", "receiver@domain.com", "Sending Email via proxy", "Test email"));


while(!str.equals("stop")){  
str=br.readLine();  
dout.write(("HELO\n").getBytes());
dout.flush();  
str2=in.readLine();  
System.out.println("Server says: "+str2);  
}  
  
dout.close();  
s.close();  
}}  
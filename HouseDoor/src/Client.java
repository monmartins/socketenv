import java.io.*; 
import java.net.*; 
  
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
    	Boolean first = true;
        try
        { 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("127.0.0.1"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 5000); 
      
            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true)  
            { 
 
                if(first) {
                    dos.writeUTF("new-door");
                    first=false;
                }

                String received = dis.readUTF(); 
                System.out.println(received); 
                if(received.contains("1")) {
                	System.out.println("Door is open");
                }
                if(received.contains("0")) {
                	System.out.println("Door is closed");
                }
                if(received.equals("Exit")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
            } 
              
            // closing resources 
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 
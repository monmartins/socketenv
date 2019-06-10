import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

public class CServer  extends Thread implements MethodsServer{
	ServerSocket serverSocket;
	public static Map<String, ArrayList<Socket>> mapSockets;
	public int count;
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is on");
			this.mapSockets=new HashMap<String,ArrayList<Socket>>(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endServerConnection(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void server() {
		InputStream input = null;
		Boolean outWhile = false;
		int read_c;
		System.out.println("Server is listening");
       while (true)  
        { 
            Socket socket = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
    			socket = serverSocket.accept();
                  
                System.out.println("A new client is connected : " + socket); 
                System.out.println("A new client is connected : " + socket.getPort());
                System.out.println("A new client is connected : " + socket.getRemoteSocketAddress());

                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(socket.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                ClientHandler t = new ClientHandler(socket, dis, dos); 
                if(!socket.getRemoteSocketAddress().toString().contains("127")) {
                    t.OutOrIn = true; //Out
                    
                    // Invoking the start() method 
                    t.start(); 
                	
                }
                if(socket.getRemoteSocketAddress().toString().contains("127")) {
                    t.OutOrIn = false; //In
                    
                    // Invoking the start() method 
                    t.start(); 
                	
                }
                  
            } 
            catch (Exception e){ 
                try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
                e.printStackTrace(); 
            } 
        } 
	       
//		try {
//			socket = serverSocket.accept();
//			System.out.println("Client connected");
//
//			String msg="";
//			input = socket.getInputStream();
//			System.out.println(input);
//			while(!outWhile) {
//				read_c = input.read();
//				msg=msg+(char)read_c;
//				if(msg.contains("END")) {
//					System.out.println(msg);
//					msg="";
//				}
//				if(msg.contains("OUT-SERVER")) {
//					endServerConnection();
//					msg="";
//					outWhile=true;
//				}
//			}
//			System.out.println("Out");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}  
		
	}
	public void sendServer(Socket socket,String msg) {
		OutputStream output;
		try {
			output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   // reads a line of text
		
	}
	
	
	@Override
	public ArrayList<String> receiveString(String value) {
		// TODO Auto-generated method stub
		value = value.split("END")[0];
		ArrayList<String> msg=(ArrayList<String>) Arrays.asList(value.split(":"));
		return null;
		
	}
	@Override
	public String sendString(String option, String value) {
		// TODO Auto-generated method stub
		String msg=option+":"+value;
		return msg;
		
	}
	
	
}

//ClientHandler class 
class ClientHandler extends Thread  
{ 
 DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
 DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
 final DataInputStream dis; 
 final DataOutputStream dos; 
 final Socket s; 
 public Boolean OutOrIn = false;
   

 // Constructor 
 public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
 { 
     this.s = s; 
     this.dis = dis; 
     this.dos = dos; 
 } 
 
 public void sendAll(String option,String value) {
	 
     Iterator<Socket> socketAsIterator = CServer.mapSockets.get(option).iterator();
     while (socketAsIterator.hasNext()){
         DataOutputStream dos;
		try {
			if(OutOrIn) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketAsIterator.next().getOutputStream()));
                out.write(value);
                out.flush();
			}
			if(!OutOrIn) {
				dos = new DataOutputStream(socketAsIterator.next().getOutputStream());
				dos.writeUTF(value); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
     }
 }

 @Override
 public void run()  
 { 
     String received=null; 
     String receivedArd=null; 
     String toreturn; 
     while (true)  
     { 
         try { 

             // Ask user what he wants 
             dos.writeUTF("Welcome SmartHome"); 
             if(OutOrIn) {

                 BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
                 received = in.readLine();
             }if(!OutOrIn) {

                 received = dis.readUTF(); 
             }
             System.out.println("Client response: " + received);
             System.out.println("Client response: " + receivedArd);
             // receive the answer from client

             
             if(received.contains("new-")){
            	 try {
            		 
	            	 if(CServer.mapSockets.get(received.split("new-")[1]).isEmpty()) {
	            		 ArrayList<Socket> s = new ArrayList<>();
	            		 s.add(this.s);
	            		 CServer.mapSockets.get(received.split("new-")[1]).add(this.s);
	            	 }else {
	            		 CServer.mapSockets.get(received.split("new-")[1]).add(this.s);
	            	 }
	                 Iterator<Socket> socketAsIterator = CServer.mapSockets.get(received.split("new-")[1]).iterator();
	                 while (socketAsIterator.hasNext()){
	                        System.out.println(socketAsIterator.next());
	                         
	                 }
            	 }catch(Exception e) {
//            		 e.printStackTrace();
            		 ArrayList<Socket> list_socket = new ArrayList<>();
            		 list_socket.add(this.s);
            		 CServer.mapSockets.put(received.split("new-")[1], list_socket);
	                 Iterator<Socket> socketAsIterator = CServer.mapSockets.get(received.split("new-")[1]).iterator();
	                 while (socketAsIterator.hasNext()){
	                        System.out.println(socketAsIterator.next());
	                         
	                 }
            	 }
             }
             if(received.contains("control")) {
            	 received=received.split("control")[1];
            	 this.sendAll(received.split(":")[0], received.split(":")[1]);
            	 
             }
//             System.out.println(received);
             if(received.equals("Exit")){  
                 System.out.println("Client " + this.s + " sends exit..."); 
                 System.out.println("Closing this connection."); 
                 this.s.close(); 
                 System.out.println("Connection closed"); 
                 break; 
             } 
               
         } catch (IOException e) { 
             e.printStackTrace(); 
             System.out.println("Client " + this.s + " sends exit..."); 
             System.out.println("Closing this connection."); 
             try {
				this.s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
             System.out.println("Connection closed"); 
             break; 
         } 
     } 
       
     try
     { 
         // closing resources 
         this.dis.close(); 
         this.dos.close(); 
           
     }catch(IOException e){ 
         e.printStackTrace(); 
     } 
 } 

} 

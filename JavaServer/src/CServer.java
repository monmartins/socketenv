import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.ArrayList;

public class CServer implements MethodsServer{
	ServerSocket serverSocket;
	public Socket socket;
	public int count;
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is on");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endServerConnection() {
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
		try {
			socket = serverSocket.accept();
			System.out.println("Client connected");

			String msg="";
			input = socket.getInputStream();
			System.out.println(input);
			while(!outWhile) {
				read_c = input.read();
				msg=msg+(char)read_c;
				if(msg.contains("END")) {
					System.out.println(msg);
					msg="";
				}
				if(msg.contains("OUT-SERVER")) {
					endServerConnection();
					msg="";
					outWhile=true;
				}
			}
			System.out.println("Out");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
	}
	public void sendServer(String msg) {
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

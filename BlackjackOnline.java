/**
 * BlackjackOnline
 * QWOP Bois
 * 6/13/2019
 * CPSC 470
 * 
 * BlackjackOnline is a server that hosts an online game of Blackjack.
 * A client program running separately will also be required to interface with it.
 * This server can also function as a very basic general-purpose socket server.
 * 
 */

package project2;


import java.net.*; 
import java.io.*; 

public class BlackjackOnline 
{ 
	//initialize socket and input/output parameters
	private Socket          socket   = null; 
	private ServerSocket    server   = null; 
	private BufferedReader in       =  null; 
	private PrintWriter out     = null; 

	// empty constructor using default 5000 port
	public BlackjackOnline() 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(5000); 
			System.out.println("Server started"); 
			System.out.println("Waiting for a client ..."); 

			// blocking statement; stops here until valid connection goes to socket
			socket = server.accept(); 
			// once proper connection has been made, our input and output methods are initialized
			System.out.println("Client accepted"); 
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
		
		//  at this point, the user will be in charge of writing, reading, and eventually closing the server upon completion of the code.
		
	} 

	/**
	 * Basic writing method using the previously-created socket information
	 * @param message - Message to send from the socket.
	 */
	public void write(String message) {
		out.println(message);
	}

	/**
	 * Basic read-in input from the connected client
	 * @return A String containing the message given by the client
	 */
	public String read() {
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "IOException";
	}

	/**
	 * Required closing method for the socket and read/write objects once the server is finished. Must be done at end of server cycle otherwise errors may occur.
	 */
	public void close() {
		
		System.out.println("Closing connection.");
		try {
			socket.close();
			in.close(); 
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
} 

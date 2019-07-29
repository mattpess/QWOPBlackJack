/**
 * BlackjackOnline
 * QWOP Bois
 * 6/13/2019
 * CPSC 470
 * 
 * BlackjackClient is a client that functions solely with BlackjackMain and BlackjackOnline
 * It does not have general read/write methods like BlackjackOnline does, and runs through a rigid, expected chain of events.
 * In the end it will loop through reads until a specific string is sent to  it, at which point it will enter its closing sequence.
 * 
 */

package project2;

import java.net.*;
import java.util.Scanner;
import java.io.*; 

public class BlackjackClient 
{ 
	// initialize socket and input/output parameters for the client
	private Socket socket            = null; 
	private BufferedReader in       =  null; 
	private PrintWriter out     = null; 

	/**
	 * The constructor does all the necessary functions of the client, including speaking with the server and disconnecting.
	 * @param address - The current address of the server
	 * @param port - The current port of the server
	 */
	public BlackjackClient(String address, int port) 
	{ 
		// Attempt to contact the server at the given port and address
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// if successful, create read and write objects for that specific server

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true); 
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		// Scanner will be used for user input to be sent to the server at the beginning of the program
		
		Scanner scanner = new Scanner(System.in);

		// First asks for number of players
		
		try {
			
			System.out.println(in.readLine());
		
		// User responds
		
		String response = scanner.nextLine();
		
		out.println(response);
		
		// The player count sent is kept so we can make sure to only send player names equal to the count provided to the server.
		
		int playerCount = Integer.parseInt(response);
		
		// Until the player count is filled, the server will continue asking for more player names
		while(playerCount > 0 ) {
			System.out.println(in.readLine());
			response = scanner.nextLine();
			out.println(response);
			playerCount--;
		}
		
		// Main loop; will keep looping through the output strings sent until the game end line is sent
		boolean gameOn = true;
		while(gameOn) {
			String line = in.readLine();
			System.out.println(line);
			if(line.equals("Thanks for playing!")) {
				gameOn = false;
			}
		} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// close the connection 
		try
		{ 
			System.out.println("Connection closing too!");
			in.close(); 
			out.close(); 
			socket.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
		scanner.close();
	} 

	public static void main(String args[]) 
	{ 
		// 127.0.0.1 is localhost
		BlackjackClient client = new BlackjackClient("127.0.0.1", 5000); 
	} 
}
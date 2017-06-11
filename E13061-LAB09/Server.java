
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server implements Runnable {
	
	// Hashmap to store the details in the csv file
	public static HashMap <String,String> hmap = new HashMap<>();

	private static String row;
	private static final String COMMA = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	static String line;
	static String Name;
	static String Symbol; 
	static String price;
	public static final int BASE_PORT = 2000;   
	
	// creating socket objects 
    private ServerSocket serverSocket; 
	private static int socketNumber; 
	private Socket connectionSocket; 
	
    // Constructor
    public Server(int socket) throws IOException { 
		serverSocket = new ServerSocket(socket); 
		socketNumber = socket; 
	// create a new server socket 
    }

	// Constructor
	public Server(Socket socket) { 
		this.connectionSocket = socket; 
    }
	
	// creating handle sockets, each in new thread
    public void server_loop() throws IOException { 
	while(true) { 
	    Socket socket = serverSocket.accept(); 	    
	    Thread worker = new Thread(new Server(socket)); 
	    worker.start(); 	    
	}
    }


	public void run() {
    
		try { 
		
			int count1=0;
			//String []PriceArray= new String[8];  // Array to store prices of that specific 8 symbols
			
			BufferedReader in = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectionSocket.getOutputStream()));
			//out.print("press enter to continue");
			
		synchronized(this){	
		
			for(line = "null"; !line.equals("quit"); count1++) { 
			
				// Getting the name of the client
				if (count1==0){
					out.print("Enter the Name of the client: ");
					out.flush();
					line = in.readLine();
					Name = line ;
					
				//	getting the symbols from the client (One can bid for more than one symbol)		
				}else if (  (count1%2) == 1) {
					 out.print("Enter the Symbol: ");
					 out.flush();
					 line = in.readLine();
					Symbol = line;
					
					// Checking whether the symbol is in the hashmap or not
					
					if (hmap.containsKey(Symbol)) {
						out.println("Current details are " + hmap.get(Symbol) ); // displaying the price of the corresponding Symbol
						out.flush();
					} else {
						out.println("-1");      // If the entered symbol is invalid display 1
						out.flush();
						count1--;
					}
					
				} else {
					
					// getting the bid from the client and replace that in the hashmap
					out.print("Enter your bid: ");
					out.flush();
					line = in.readLine();
					price = line;
					
					String com_name = hmap.get(Symbol).split("/")[0];
					hmap.replace(Symbol, com_name + "/" + Name + "/" + price+"/" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
					out.println("Bid was placed! If you want you can bid more! If you want to stop type quit! ");
					 out.flush();

				}
			}
			
		}
		} catch (IOException e) { 
				System.out.println(e); 
		} 
			
		try { 	    
			this.connectionSocket.close(); 
		} catch(IOException e) {
				
		}
		
	}
	
	public static String getvalue(String key) {
        if (hmap.containsKey(key)) {
            return hmap.get(key).split("/")[2];
        } else return "-1";
    }
	
    public static void main(String[] args) throws IOException{
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("stocks.csv"));   			// Opening the file
			
			// Reading the file
            while ((row = br.readLine()) != null) {
				
                String[] item_array = row.split(",");     // use comma as separator
				hmap.put(item_array[0], item_array[1] + "/null/" + item_array[2]+"/0:00:00");     // storing data in the csv file to the hashmap as the key is the Symbol of the Security Name
                //hmap.put(item_array[0], item_array[2]);
			}
			
		} catch (Exception e){
			System.out.println(e.toString());
		}
		
		Server server = new Server(BASE_PORT);
		GUI mytable = new GUI();
		server.server_loop(); 
		
    }
}

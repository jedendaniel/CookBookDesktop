/**
 * CookBook
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
package pl.polsl.java.lab.Daniel.Kozlik;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 * The main class of the UDP server
 * Handles CookBookModel.
 * Communicates with clients.
 * @author Daniel Koźlik
 * @version 1.0
 */
public class ChatterServer {

    /** accessing port */
    static final int PORT = 1711;
    /** buffer for input data */
    private byte[] buf = new byte[1024];
    /** data frame */
    private DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
    /** communication socket */
    private DatagramSocket socket;
    /** the model */
    private CookBookModel theModel;

    /** 
     * The UDP server constructor. 
     * Create CookBookModel and ensure its handle.
     * Recieves messages from client and sends response.
     */
    public ChatterServer(String name) {
        theModel = new CookBookModel(name);
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("Server started");
            while (true) {
                
                socket.receive(datagramPacket);
                String[] rcvdMessage = translate(datagramPacket);
                String[] outMessage = new String[1];
                
                int operation = Integer.parseInt(rcvdMessage[0]);
                switch(operation){
                    case 1: 
                        Recipe recipe = new Recipe();
                        recipe.setName(rcvdMessage[1]);
                        for(int i = 2; i < rcvdMessage.length; i++){
                            recipe.addIngredient(new Ingredient(rcvdMessage[i]));
                        }
                        theModel.addRecipe(recipe);
                        outMessage[0] = "OK";
                        break;
                    case 2:
                        try{
                            recipe = theModel.getRecipe(rcvdMessage[1]);
                            outMessage = new String[recipe.getIngredients().size()+1];
                            outMessage[0] = "OK";
                            int i = 1;
                            for(Ingredient ingredient : recipe.getIngredients()){
                                outMessage[i] = ingredient.getName();
                                i++;
                            }
                            
                        } catch(NullPointerException ex){
                            outMessage[0] = "ERROR";
                        }
                        break;
                    case 3:
                        {
                            try {
                                String[] tmp = new String[rcvdMessage.length-1];
                                for(int i = 1; i < rcvdMessage.length; i++){
                                    tmp[i-1] = rcvdMessage[i];
                                }
                                List<Recipe> list = theModel.getRecipes(tmp);
                                outMessage = new String[2*list.size()+1];
                                outMessage[0] = "OK";
                                int i = 1;
                                for(Recipe r : list){
                                    outMessage[i] = r.getName();
                                    outMessage[i+1] = Integer.toString(r.getMatches());
                                    i += 2;
                                }
                            } catch (EmptyListException ex) {
                                outMessage[0] = "EmptyListException";
                            }
                        }
                        break;
                    case 4:
                        if(theModel.deleteRecipe(rcvdMessage[1]))
                            outMessage[0] = "OK";
                        else{
                            outMessage[0] = "ERROR";
                        }
                        break;
                    default: 
                        if(rcvdMessage[0].equals("HELP")){
                        outMessage = new String[4];
                        outMessage[0] = "New Recipe: Operation 1-Recipe-Ingredients";
                        outMessage[1] = "Search by name: Operation 2-Racipe";
                        outMessage[2] = "Search by ingredient: Operation 3-Ingredients";
                        outMessage[3] = "Delete: Operation 4:Recipe";
                        break;
                    }
                        
                }
                socket.send(encrypt(outMessage));
            }
        } catch (SocketException e) {
            System.err.println("Connection is not available!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error during connection!");
            e.printStackTrace();
        }
    }
    /**
     * Gets DatagramPacket's data and translate it to String array.
     * @param dp translateing DatagramPacket.
     * @return translated String array.
     */
    String[] translate(DatagramPacket dp){
        String rcvd = new String(dp.getData(), 0, dp.getLength());
        return rcvd.split(",");
    }
    
    /**
     * Pack String array into DatagramPacket.
     * @param outMessage translateing String array.
     * @return packed DatagramPackiet.
     */
    DatagramPacket encrypt(String[] outMessage){
        String outString = Stream.of(outMessage).collect(Collectors.joining(","));
        buf = outString.getBytes();
        return new DatagramPacket(buf, buf.length, datagramPacket.getAddress(), datagramPacket.getPort());
    }

    /** 
     * The main application method 
     */
    public static void main(String[] args) {
        if(args.length == 0){
            new ChatterServer("CookBook");
        } else{
            new ChatterServer(args[0]);
        }
    }
}

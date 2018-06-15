/**
 * CookBook
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
package pl.polsl.java.lab.Daniel.Kozlik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 * The main UDP client class 
 * Handles the CookBookView.
 * Communicates with server.
 * 
 * @author Daniel Koźlik 
 * @version 1.0
 */
public class ChatterClient extends Thread {

    /** communication socket */
    private DatagramSocket datagramSocket;
    /** server port number  */
    static final int PORT = 1711;
    /** server address */
    private InetAddress hostAddress;
    /** buffer for input data */
    private byte[] buf = new byte[1024];
    /** data frame */
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    /** the view */
    CookBookView theView;

    /** 
     * The constructor of the UDP client object
     */
    public ChatterClient() {
        try {
            datagramSocket = new DatagramSocket();
            hostAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.err.println("Unknown server!");
            System.exit(1);
        } catch (SocketException e) {
            System.err.println("Connection is not available!");
            System.exit(1);
        }
        System.out.println("Client started");
        this.theView = new CookBookView(new CustomListener());
    }

    private class CustomListener implements ActionListener {

        public CustomListener() {
        }

        /**
         * Take adequate to command action.
         * @param e 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
                
            case "newRecipeSave":
                String cutRecipeName = theView.getNewRecipeName().trim();
                if((cutRecipeName.isEmpty()) || (theView.getNewRecipeIngredients().length == 0)){
                    theView.showMessage("Empty field", "Please, complete all fields");
                }
                else{
                    try {
                        int size = theView.getNewRecipeIngredients().length;
                        String[] outMessage = new String[size+2];
                        outMessage[0] = "1";
                        outMessage[1] = theView.getNewRecipeName();
                        for(int i = 0; i < size; i++){
                            outMessage[i+2] = theView.getNewRecipeIngredients()[i];
                        }
                        datagramSocket.send(encrypt(outMessage));
                        datagramSocket.receive(dp);
                        String[] rcvdMessage = translate(dp);
                        if(rcvdMessage[0].equals("OK")){
                            theView.showMessage("Info", "Recipe has been saved.");
                        }
                        else {
                            theView.showMessage("Error", "Couldnt save recipe!"); 
                        }
                    } catch (IOException ex) {
                        System.err.println("Error during communication!");
                        System.exit(1);
                    }
                }
                break;
                
            case "searchByName":
                try {
                    String[] outMessage = new String[2];
                    outMessage[0] = "2";
                    outMessage[1] = theView.getSearchRecipeName();
                    datagramSocket.send(encrypt(outMessage));
                    datagramSocket.receive(dp);
                    String[] rcvdMessage = translate(dp);
                    if(rcvdMessage[0].equals("OK")){
                        String list[] = new String[rcvdMessage.length-1];
                        for(int i = 0; i < rcvdMessage.length-1; i++){
                            list[i] = rcvdMessage[i+1];
                        }
                        theView.showIngredients(list);
                    }
                    else if (rcvdMessage[0].equals("ERROR")){ 
                        theView.showMessage("Error", "Recipe doesn't exist!");
                    }  
                } catch (IOException ex) {
                    System.err.println("Error during communication!");
                    System.exit(1);
                }
                break;
                
            case "searchByIngredient":
                try {
                    int size = theView.getSearchIngredients().size() + 1;
                    String[] outMessage = new String[size];
                    outMessage[0] = "3";
                    theView.clearTable();
                    int i = 1;
                    for(String s : theView.getSearchIngredients()){
                        outMessage[i] = s;
                        i++;
                    }
                    datagramSocket.send(encrypt(outMessage));
                    datagramSocket.receive(dp);
                    String[] rcvdMessage = translate(dp);
                    if(rcvdMessage[0].equals("EmptyListException")){
                        theView.showMessage("Error", "Couldn't find any matches");
                    }
                    else{
                        for(i = 1; i < rcvdMessage.length; i+=2){
                            theView.showRecipe(rcvdMessage[i], Integer.parseInt(rcvdMessage[i+1]));
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("Error during communication!");
                    System.exit(1);
                }
                break;
                
            case "deleteRecipe":
                try {
                    String[] outMessage = new String[2];
                    outMessage[0] = "4";
                    outMessage[1] = theView.getSearchRecipeName();
                    datagramSocket.send(encrypt(outMessage));
                    datagramSocket.receive(dp);
                    String[] rcvdMessage = translate(dp);
                    if (rcvdMessage[0].equals("OK")){
                        theView.showMessage("Info", "Recipe has been deleted");
                    }
                    else{
                        theView.showMessage("Error", "Recipe doesn't exist!");
                    }
                } catch (IOException ex) {
                    System.err.println("Error during communication!");
                    System.exit(1);
                }
                break;
            }
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
        return new DatagramPacket(buf, buf.length, hostAddress, PORT);
    }

    /** 
     * The main application method 
     */
    public static void main(String[] args) {
        new ChatterClient().start();
    }
}

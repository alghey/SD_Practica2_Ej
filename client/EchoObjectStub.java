
 // conectarse y construir socket
        //escribir y leer en socket
//desconectarse 
package client;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.EchoInterface;
public class EchoObjectStub implements EchoInterface{
    private Socket echoSocket = null;
    private PrintWriter os = null;
    private BufferedReader is = null;
    private String host = "localhost";
    private int port=7;
    private String output = "Error";
    private boolean compra=false;
    private boolean connected = false;
    public void setHostAndPort(String host, int port) {
        this.host= host; this.port =port;
    }
    public String echo(String input)throws java.rmi.RemoteException 
    {
       
        try {
            connect();
            } catch (IOException ex) 
                {
                Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
                }
        if (echoSocket != null && os != null && is != null) 
            {
            try {
                os.println(input);// escribe en el socket
                os.flush();// limpia el canal de comunicacion del socket
                output= is.readLine(); // lee del socket
                } catch (IOException e) 
                {
                System.err.println("I/O Fallo al leer /escribir socket");
                throw new java.rmi.RemoteException("I/O failed in reading/writing socket");
                }
           }
           try {
               disconnect();
               } catch (IOException ex) 
                  {
                  Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
                  }
    return output;
    }

    public Boolean pago_cliente(String Tarjeta,String cvv,String pago)throws java.rmi.RemoteException{
       
            try {
            connect();
            } catch (IOException ex){
                Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        if (echoSocket != null && os != null && is != null){
            try {
                String datos=Tarjeta+","+cvv+","+pago;
                os.println(datos);
                os.flush();
                compra= Boolean.parseBoolean(is.readLine());
                } catch (IOException e){
                System.err.println("I/O Fallo al leer /escribir socket");
                throw new java.rmi.RemoteException("I/O failed in reading/writing socket");
                }
           }
           try {
               disconnect();
               } catch (IOException ex){
                  Logger.getLogger(EchoObjectStub.class.getName()).log(Level.SEVERE, null, ex);
                  }
        return compra;
    }




    private synchronized void connect() throws
    java.rmi.RemoteException, IOException {
    //EJERCICIO: Implemente el m??todo connect, que aqui ya esta implementado
        echoSocket = new Socket(host, port);
        os=new PrintWriter(echoSocket.getOutputStream());
        is=new BufferedReader( new InputStreamReader(echoSocket.getInputStream()));
    }
    private synchronized void disconnect() throws IOException{
    //EJERCICIO: Implemente el m??todo disconnect
        os.close();
        is.close();
        echoSocket.close();
    }
}

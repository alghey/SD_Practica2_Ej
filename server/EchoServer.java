
package server;
import java.net.*;
import java.io.*;
public class EchoServer {
	// Objeto remoto ( es el Stub del servidor, conocido como Skeleton)
    private static EchoObjectSkeleton eo = new EchoObjectSkeleton();
    private static String myURL="localhost";
    private static ServerSocket serverSocket =null;
    private static Socket clientSocket = null;
    private static BufferedReader is = null;
    private static PrintWriter os = null;
    private static String inputline = new String();

    public static String [][]datos_banco={{"4766785947910313","9876543219876543"},{"897","352"},{"500","200"}};
    
    public static void main(String[] args) {
        try {
            myURL=InetAddress.getLocalHost().getHostName(); 
        } catch (UnknownHostException e) {
            System.out.println("Host Desconocido  :" + e.toString());
            System.exit(1);
        }
        try {
            serverSocket = new ServerSocket(1007);
        } catch (IOException e) {
            System.out.println(myURL + ": no puedo escuchar en el puerto: 1007, " +e.toString());
            System.exit(1);
        }
        System.out.println(myURL + "- - - El servidor de Amazon comenzo a escuchar peticiones en el puerto 1007 - - -");

        try {
            boolean listening = true;
            while(listening){
                clientSocket = serverSocket.accept();
                is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream());
                while ((inputline = is.readLine()) != null){
                    System.out.println("====== ATENDIENDO CLIENTE ======\n");
                    os.println(eo.pago_server(operacion_banco(inputline)));
                    os.flush();
                }
                os.close();
                is.close();
                clientSocket.close();
                System.out.println("====== PETICION TERMINADA ======\n");
                System.out.println("\n\n");
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error enviando/recibiendo" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Boolean operacion_banco(String datos_cliente){
        String Tarjeta,cvv,saldo;
        String[] datos_individuales=datos_cliente.split(",");
        boolean pago_aceptado=false;

        int i=0,k=0;

        for(i = 0; i<datos_banco[0].length; i++){
           if(datos_banco[0][i].equals(datos_individuales[0])){
               k=i;
               pago_aceptado=true;
               System.out.println("Datos correctos");
               break;
           }
        }
        if(pago_aceptado==true){
                if(datos_banco[1][k].equals(datos_individuales[1])){
                    pago_aceptado=true;
                    System.out.println(" El cvv es correcto");
                }else{
                    pago_aceptado=false;
                    System.out.println("El cvv es incorrecto");
                }
        }
        if(pago_aceptado==true){

                if(Integer.parseInt(datos_banco[2][k])>=Integer.parseInt(datos_individuales[2])){
                    int aux=Integer.parseInt(datos_banco[2][k]);
                    aux=aux-Integer.parseInt(datos_individuales[2]);
                    datos_banco[2][k]=String.valueOf(aux);
                    pago_aceptado=true;
                }else{
                    pago_aceptado=false;
                }
        }

        return pago_aceptado;
    }
}
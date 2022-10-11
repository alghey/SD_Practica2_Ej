
package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

public class EchoClient {
    private static EchoObjectStub ss;
    
    
    public static void main(String[] args){


        String Tarjeta,cvv,pago;
        
        if (args.length<2) {
            System.out.println("Para ejecutar , hazlo en este formato: Echo <nombre o IP del Equipo> <numero de puerto>");
            System.exit(1);
        }



        ss = new EchoObjectStub();
        ss.setHostAndPort(args[0],Integer.parseInt(args[1]));
        String input,output;
        try {

            while(true){
                Tarjeta="";
                cvv="";
                //preparo "apuntador" que es el lector de flujo para el teclado
                BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));

                System.out.println("¡BIENVENIDO A AMAZON! \n\n");

                while(true){
                    System.out.println("-> Para realizar una compra: \n Ingrese el numero de su tarjeta: ");
                    Tarjeta=in.readLine();
                    if(Tarjeta.length()<16){
                        Tarjeta="";
                        System.out.println("Formato de tarjeta incorrecto, por favor intente de nuevo\n");
                    }else{break;}
        
                }
                while(true){
                    System.out.println("-> Ingrese su CVV: ");
                    cvv=in.readLine();
                    if(cvv.length()<3||cvv.length()>3){
                        cvv="";
                        System.out.println("Formato de cvv incorrecto, por favor intente de nuevo\n");
                    }
                    else{break;}
                }

                while(true){
                    System.out.println("-> Ingrese el monto a pagar: ");
                    pago=in.readLine();
                    if(Integer.parseInt(pago)<=0){
                        pago="";
                        System.out.println("Formato de cantidad incorrecta, por favor intente de nuevo\n");
                    }
                    else{break;}
                }

                System.out.println("\n> > Su pago esta siendo procesado, por favor espere un momento..");

               // Invocar el stub con el metodo remoto echo e Imprimir .. 
                //por pantalla lo que regreso el metodo remoto echo
                if(ss.pago_cliente(Tarjeta,cvv,pago)==false){
                    System.out.println("PAGO RECHAZADO, por favor intente con otro metodo\n\n");
                }
                else{
                    System.out.println("¡PAGO REALIZADO CON EXITO!\n Su compra pronto estara en camino\n");
                }

            }
        } 
        //catch (UnknownHostException e) {
            //System.err.println("Don't know about host: "+ args[0]);
        //} 
        catch (IOException e) {
            System.err.println("Falla conexion de E/S con el host:"+args[0]);
        }
    }
}

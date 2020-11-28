/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes.des;

/**
 *
 * @author demon
 */

//librerias wiiiii que hagan todo por mi *w*

import java.io.FileOutputStream;
import javax.crypto.Cipher;
//para poder hacer el cifrado

import javax.crypto.spec.SecretKeySpec;
//para poder realizar las subllaves del algoritmo

import java.util.*;
import sun.misc.BASE64Encoder;




public class AES {

    /**
     * @param args the command line arguments
     */
    
        // TODO code application logic here
        /*
        Para poder operar con el algoritmo de cifrado simetrico aes
        debemos de tener una llave correspondiente a el tipo de tamaño
        segun sea
        
        128     16 caracteres lo que es lo mismo 128 bits
        192     24 caracteres lo que es lo mismo 192 bits
        256     32 caracteres lo que es lo mismo 256 bits
        
        el tamaño del mensaje no importa, sino lo que importa es el tamaño
        de la llave secreta
        */
        
        public String cifrar(String textoClaro, String textoClave){
        
       
        
        //ahora vamos a crear las subllaves a traves del uso de la clase
        //SecretKeySpec
        
        SecretKeySpec key = new SecretKeySpec(textoClave.getBytes(), "AES");
        
        //ahora el objeto de cifrado
        Cipher cifrado;
        
        try{
            
            //se debe de tener el tipo de instancia del cifrado que en este caso es AES
            cifrado = Cipher.getInstance("AES");
            
            //comenzar por el metodo de cifrado
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            
            
            
            
            //ahora necesitamos almacenar el mensaje en un bloque de bytes
            
            byte[] campoCifrado = cifrado.doFinal(textoClaro.getBytes());
            
            //vamos a visualizar el mensaje cifrado
            
            System.out.println("Mensaje cifrado: " + campoCifrado);
            
            
            //vamos a transformarlo en una cadena
            
            System.out.println("Mensaje en cadena: " + new String(campoCifrado));
            
            //para poder entender el mensaje cifrado en caracteres es necesario codificarlo
            
            String base64 = new BASE64Encoder().encode(campoCifrado);
            
            System.out.println("Mensaje cifrado entendible: " + base64);
             FileOutputStream out = new FileOutputStream(/*args[0]*/"AES.txt"+".cifrado");
             out.write(campoCifrado);
             out.close();
            return base64;
             }catch(Exception e){
                System.out.println("Error solo juguito contigo *w* uwu");
                System.out.println(e.getMessage());

                return "algo salio mal UnU";
            }
        }
        
            public String descifrar(String textoCifrado, String textoClave){
               byte[] cifrado = Base64.getDecoder().decode(textoCifrado);
            
                SecretKeySpec key = new SecretKeySpec(textoClave.getBytes(), "AES");
            try{
                //ahora vamos a decifrar
                
                Cipher descifrado;
                descifrado = Cipher.getInstance("AES");
                descifrado.init(Cipher.DECRYPT_MODE, key);

                //necesito un arreglo que se encargue de almacenar el mensaje cifrado
                //a descifrar

                byte[] mensajeDescifrado = descifrado.doFinal(cifrado);

                System.out.println("Mensaje descifrado: " + mensajeDescifrado);

                //dandole formato de cadena

                String mensaje_claro = new String(mensajeDescifrado);

                System.out.println("Mensaje descifrado en cadena: " + mensaje_claro);
                 FileOutputStream out = new FileOutputStream(/*args[0]*/"AES.txt"+".descifrado");
                 out.write(mensajeDescifrado);
                 out.close();
                return mensaje_claro;
            }catch(Exception e){
                System.out.println("Error solo juguito contigo *w* uwu");
                System.out.println(e.getMessage());
                return "Algo salio mal";
            }
            
          }
    }
    

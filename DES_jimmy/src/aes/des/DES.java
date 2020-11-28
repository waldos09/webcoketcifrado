package aes.des;

/**
 *
 * @author tonis
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.*;

import javax.crypto.interfaces.*;
//de aqui vamos a ocupar el tipo de algoritmo para generar la interfaz

import javax.crypto.spec.*;
//es un elemento que nos ayuda a generar la llave o las subllaves

import java.security.*;
import sun.misc.BASE64Encoder;
//esta libreria nos ayuda a definir el tipo de algoritmo simetrico o asimetrico a programar

public class DES {
    
    public DES(){
        
    }
    
    
    public String Cifrar(String contra, String mensaje) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException{
        /*
        
        Para poder utilizar el cifrado simetrico DES, debemos de cargar el tipo de cifrado
        a traves del uso de "provider" 
        
        Paso 1.- crear e inicilizar la clave o llave privada
        
        */
        
        System.out.println("1.- Generar Clave DES: ");
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        //la generacion de la llave se debe de hacer a partir de algoritmo DES la cual es una funcion 
        //que devuelve numeros pseudoaleatorios a una cadena especifica de datos y "la cifra"
        System.out.println("uwu");
        //el tamaño de la llave
        generadorDES.init(56);  //la llave debe de ser de 56 bits
        
        //vamos a instancear la clave para que se genere
        
        //SecretKey clave = generadorDES.generateKey();
        String llave = /*"acuamane"*/ contra;
        SecretKeySpec clave = new SecretKeySpec(llave.getBytes(),"DES");
        
        System.out.println("la clave es: "+clave);
        
        //como todo esta en bites o bytes dependiendo tenemos que crear un metodo para poderlos ver
        mostrarBytes(clave.getEncoded());
        
        System.out.println("Clave codificada"+clave.getEncoded());
        System.out.println();
        
        //Paso 2: crear el cifrador
        
        /*
        El tipo de cifrado que se va a crear: simetrico DES
        Decir el modo de ciframiento:  FLUJO Y BLOQUES:  por ECB (Electornic Code Book)
        Si va o no a ocupar relleno (padding), nos sirve para una vez que se formen los bloques
        de 64 bits para el cifrado DES, debemos entender que algunos bloques no van a quedar completamente
        cubiertos, es por eso que se necesita rellenar
        
        PKCS5Padding
        
        */
        
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //tenemos que crear el menu para la carga del archivo a cifrar
        
        System.out.println("2.- Cifrar con DES el fichero : "+ "DES.txt" + ", dejar el resultado en: "
        + "DES.txt" + " cifrado");
        
        //tenemos que cargar un archivo de texto el cual lo va  acifrar con des y una vez cifrado va a 
        //generar un archivo que lo va a guardar dentro del proyecto
        
        //la parte mas complicada el cifrado por pasos
        
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        
        //wiii que cansado ufff
        
        //entonces tenemos que transformar y leer el fichero en bytes
        
        byte[] buffer = new byte[1000];
        
        byte[] bufferCifrado;
        
        
        //vamos a generar el archivo
        
        FileInputStream in = new FileInputStream("DES.txt");
        FileOutputStream out = new FileOutputStream("DES.txt"+".cifrado");
        
        //tenemos que empezar por la lectura del archivo y converlo en bytes
        
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            bufferCifrado  = cifrador.update(buffer, 0, bytesleidos);
            out.write(bufferCifrado);
            bytesleidos = in.read(buffer, 0, bytesleidos);
        }
        
        //cuando termine de leer el archivo
        bufferCifrado = cifrador.doFinal();
        //escribir el archivo de salida
        out.write(bufferCifrado);
        
        in.close();
        out.close();
        String base64 = new BASE64Encoder().encode(bufferCifrado);
        return base64;
    }
    
    public String descifrar (String contra, String mensaje) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException{
        
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        //la generacion de la llave se debe de hacer a partir de algoritmo DES la cual es una funcion 
        //que devuelve numeros pseudoaleatorios a una cadena especifica de datos y "la cifra"
        System.out.println("uwu");
        //el tamaño de la llave
        generadorDES.init(56);  //la llave debe de ser de 56 bits
        
        //vamos a instancear la clave para que se genere
        
        //SecretKey clave = generadorDES.generateKey();
        String llave = /*"acuamane"*/ contra;
        SecretKeySpec clave = new SecretKeySpec(llave.getBytes(),"DES");
        
        //vamos a descifrar
        //tenemos que crear el menu para la carga del archivo a cifrar
        
        System.out.println("3.- Descifrar con DES el fichero : "+ "DES.txt" + ", dejar el resultado en: "
        + "DES.txt" + ".descifrado");
        
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //la parte mas complicada el cifrado por pasos
        
        cifrador.init(Cipher.DECRYPT_MODE, clave);
        
        //wiii que cansado ufff
        
        //entonces tenemos que transformar y leer el fichero en bytes
        
        
        byte[] buffer = new byte[1000];
        
        byte[] bufferPlano;
        
        
        //vamos a generar el archivo
        
        
        FileInputStream in = new FileInputStream(/*args[0]*/"DES.txt"+".cifrado");
        FileOutputStream out = new FileOutputStream(/*args[0]*/"DES.txt"+".descifrado");
        
        //tenemos que empezar por la lectura del archivo y converlo en bytes
        
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            bufferPlano  = cifrador.update(buffer, 0, bytesleidos);
            out.write(bufferPlano);
            bytesleidos = in.read(buffer, 0, bytesleidos);
        }
        
        //cuando termine de leer el archivo
        bufferPlano = cifrador.doFinal();
        //escribir el archivo de salida
        out.write(bufferPlano);
        
        in.close();
        out.close();
        String base64 = new BASE64Encoder().encode(bufferPlano);
        return base64;
    }
    
    
    
    public static void mostrarBytes(byte[] buffer) {
        //que este metodo nos va a convertir los archivos en bytes
        System.out.write(buffer, 0, buffer.length);
    }
    
    
    
}

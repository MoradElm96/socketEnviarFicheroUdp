package enviarFichero;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Servidor {

	public static void main(String[] args) throws IOException {
		
		try {
			
			int puertoSocket = 6000;
			
			
			//iniciamos el servidor 
			DatagramSocket socketServidor = new DatagramSocket(puertoSocket);
			byte[] recibido = new byte[1024];
			
			

			while (true) {
				
				System.out.println("Servidor escuchando... esperando al cliente ");
				DatagramPacket paqueteRecibido = new DatagramPacket(recibido, recibido.length);         	
				//recibimos el paquete del cliente
				socketServidor.receive(paqueteRecibido);
				
				//obtenemos informacion del cliente
				InetAddress direccion = paqueteRecibido.getAddress();
				int puerto = paqueteRecibido.getPort();
				
				String nombreArchivo = new String(paqueteRecibido.getData()).trim();
			    System.out.println("cliente ha pedido leer el archivo: " + nombreArchivo);
			    
			    
			    File fichero = new File(nombreArchivo);
			    
			    if(fichero.exists()) {
			    	//usamos el metodo para leer
			    	 byte [] enviar = leerFichero(nombreArchivo).getBytes();
			    	 
			    	 DatagramPacket respuesta = new DatagramPacket(enviar,  enviar.length, direccion, puerto);
					 socketServidor.send(respuesta);
					 socketServidor.close();
					 
			    }else {
			    
			    	 String resultado = "404: File not found";
			    	 byte [] enviar = resultado.getBytes();
			    	 DatagramPacket respuesta = new DatagramPacket(enviar,  enviar.length, direccion, puerto);
					 socketServidor.send(respuesta);
	 
			    }
			    
				
			}
			
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	
	
		
	
	}

	public static String leerFichero(String nombre) {
		String contenido="";
		try {
			File fichero = new File(nombre);
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			
			while(linea!=null) {
				contenido = contenido + linea + "\n";
			}
			
			br.close();
			fr.close();
			
	
			
			
		}catch(Exception e) {
			System.out.println("Error al leer el fichero");
		}
		return contenido;
	}

}

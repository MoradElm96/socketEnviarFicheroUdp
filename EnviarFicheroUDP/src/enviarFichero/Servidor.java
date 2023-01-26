package enviarFichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Servidor {

	public static void main(String[] args) throws IOException {
		
		try {
			
			int puertoSocket = 6000;
			
			
			//iniciamos el servidor 
			DatagramSocket socketServidor = new DatagramSocket(puertoSocket);
			byte[] recibido = new byte[1024];
			
			

		//	while (true) {
				
				System.out.println("Servidor escuchando... esperando al cliente ");
				DatagramPacket paqueteRecibido = new DatagramPacket(recibido, recibido.length);         	
				//recibimos el paquete del cliente
				socketServidor.receive(paqueteRecibido);
				
				//obtenemos informacion del cliente
				InetAddress direccion = paqueteRecibido.getAddress();
				int puerto = paqueteRecibido.getPort();
				
				String nombreArchivo = new String(paqueteRecibido.getData()).trim();
			    System.out.println("cliente ha pedido leer el archivo: " + nombreArchivo );
			    
			    
			    
			    //leemos
			    String contenidoFichero;
			    contenidoFichero = leerFichero(nombreArchivo);
			    
			    
			    if(contenidoFichero!=null) {
			    	 byte [] enviar = contenidoFichero.getBytes();
			    	 DatagramPacket respuesta = new DatagramPacket(enviar,  enviar.length, direccion, puerto);
					 socketServidor.send(respuesta);
					
					 
			    	
			    }else {
			    	contenidoFichero = "404: File not found";
			    	 byte [] enviar = contenidoFichero.getBytes();
			    	 DatagramPacket respuesta = new DatagramPacket(enviar,  enviar.length, direccion, puerto);
					 socketServidor.send(respuesta);
					
			    }
			   
			    
			 
				 socketServidor.close();
			//}
			
			
			
			
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	
	
		
	
	}

	public static String leerFichero(String nombre) {
		
		StringBuilder sb = new StringBuilder();
		try {
		
		
			File fichero = new File(nombre);
			
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			
			
			String linea = br.readLine();
			
			while(linea!=null) {
				
				sb.append(linea);
				//separamos por lineas el texto
				sb.append(System.lineSeparator());
				linea = br.readLine();
			}
			
			br.close();
			fr.close();
		
		
			
			
		}catch(Exception e) {
			sb.append("404: File not found");
			
		}
		
		return sb.toString();
	}

}

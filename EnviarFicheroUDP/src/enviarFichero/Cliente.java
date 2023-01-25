package enviarFichero;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);

		final String HOST = "localhost";
		int puerto_Servidor = 6000;

		// INICIAMOS SERVIDOR CLIENTE
		System.out.println("Cliente conectado...");
		InetAddress direcion_servidor = InetAddress.getByName(HOST);
		DatagramSocket socketCliente = new DatagramSocket();

		System.out.println("Introduce el nombre del fichero");
		String nombreArchivo = sc.nextLine();

		// ENVIAMOS EL NOMBRE DEL FICHERO AL SERVIDOR
		byte[] enviar = String.valueOf(nombreArchivo).getBytes();
		DatagramPacket paqueteEnviar = new DatagramPacket(enviar, enviar.length, direcion_servidor, puerto_Servidor);
		socketCliente.send(paqueteEnviar);

		// recibir respuesta servidor, es decir el contenido del archivo
		byte[] recibido = new byte[1024];
		DatagramPacket paqueteRecibido = new DatagramPacket(recibido, recibido.length);
		socketCliente.receive(paqueteRecibido);

		String contenido = new String(paqueteRecibido.getData());

		if (contenido.equals("404: File not found")) {
			System.out.println(contenido);

		} else {

			System.out.println("Contenido del fichero");
			System.out.println(contenido);

		}

		socketCliente.close();

	}

}

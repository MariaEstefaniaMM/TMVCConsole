package tmvc;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketClient {
    private Socket socket;
    public DataInputStream inputBuffer = null;
    public DataOutputStream outputBuffer = null;
    Scanner keyBoard = new Scanner(System.in);

    public void conect(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            System.out.println("Conectado a :" + socket.getInetAddress().getHostName());
        } catch (Exception e) {
            System.out.println("Excepción al levantar conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    public void openStream() {
        try {
            inputBuffer = new DataInputStream(socket.getInputStream());
            outputBuffer = new DataOutputStream(socket.getOutputStream());
            outputBuffer.flush();
        } catch (IOException e) {
            System.out.println("Error en la apertura de flujos");
        }
    }

    public void send(String s) {
        try {
            outputBuffer.writeUTF(s);
            outputBuffer.flush();
        } catch (IOException e) {
            System.out.println("IOException on enviar");
        }
    }

    public void closeConection() {
        try {
            outputBuffer.close();
            outputBuffer.close();
            socket.close();
            System.out.println("Conexión terminada");
        } catch (IOException e) {
            System.out.println("IOException on cerrarConexion()");
        }finally{
            System.exit(0);
        }
}
}

package tmvc;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Console extends JPanel {
    JTextArea textArea;
    static TrayClass t = new TrayClass();
    static JFrame frame;

    public Console() {
        this.textArea = new JTextArea();
        initializeUI();
    }

    private void initializeUI() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(2, 80, 80));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        Font font = new Font("Monospaced",Font.ITALIC, 14);
        textArea.setFont(font);
        textArea.setForeground(Color.white);
        textArea.append("Messages from socket log Service : ");
        excecuteConection("localhost",9999);

        this.setPreferredSize(new Dimension(600, 400));
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);




    }

    public void showFrame() {
        JPanel panel = new Console();
        panel.setOpaque(true);

        frame = new JFrame("Console");
        t.getConsole(frame);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);



    }

    public void excecuteConection(String ip, int puerto) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        Socket client = new Socket(ip, puerto);
                        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        System.out.println("created inputStream" + inputBuffer.toString());
                        writeOnConsole(inputBuffer);
                        client.close();
                        System.out.println("desconectado");
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }
        });
        hilo.start();

    }




    private void writeOnConsole(BufferedReader inputBuffer){
        try {
            String text = inputBuffer.readLine()+"\n";
            System.out.print("message from server outside do : "+ text);
                this.textArea.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Console console= new Console();
        console.showFrame();
//        console.excecuteConection("localhost", 9999);
    }
}

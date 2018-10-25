package tmvc;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Console extends JPanel {
    JTextArea textArea;
    static TrayClass t = new TrayClass();
    static JFrame frame;
    SocketClient client=new SocketClient();

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
                    client.conect(ip, puerto);
                    client.openStream();
                    writeOnConsole();
                } finally {
                    client.closeConection();
                }
            }
        });
        hilo.start();
    }

    private void writeOnConsole(){
        try {
            do {
                textArea.append(client.inputBuffer.readUTF()+"\n");
                System.out.print(client.inputBuffer.readUTF()+"\n");
            } while (t.finish);
        } catch (IOException e) {}

    }

    public static void main(String[] args) {
        Console console= new Console();
        console.showFrame();
        console.excecuteConection(ip, puerto);
    }
}

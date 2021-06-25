package com.kky.tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    private TextArea leftTextArea = new TextArea();
    private TextArea rightTextArea = new TextArea();
    private Server server = new Server();

    private ServerFrame(){
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        Panel panel = new Panel(new GridLayout(1,2));
        panel.add(leftTextArea);
        panel.add(rightTextArea);
        this.add(panel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }

    public void updateServerMsg(String str){
        this.leftTextArea.append(str+"\n");
    }

    public void updateClientMsg(String str){
        this.rightTextArea.append(str+"\n");
    }
}

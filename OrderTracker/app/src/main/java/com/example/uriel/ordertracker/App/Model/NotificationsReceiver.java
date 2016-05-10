package com.example.uriel.ordertracker.App.Model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by martin on 09/05/16.
 */
public class NotificationsReceiver {

    private DatagramSocket datagramSocket;

    public boolean createSocket(int port) {

        try {
            datagramSocket = new DatagramSocket(port);
            return true;
        }

        catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DatagramPacket receiveMessage(DatagramPacket datagramPacket) throws IOException {
        datagramSocket.receive(datagramPacket);
        return datagramPacket;
    }

    public void close() {
        this.datagramSocket.close();
    }

}

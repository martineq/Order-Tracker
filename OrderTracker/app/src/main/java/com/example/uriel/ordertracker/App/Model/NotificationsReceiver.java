package com.example.uriel.ordertracker.App.Model;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.*;

/**
 * Created by martin on 09/05/16.
 */
public class NotificationsReceiver {

    private class UDPStatus implements Command {

        private int port;

        UDPStatus(int port) {
            this.port = port;
        }

        private String generateJsonMessage() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", SessionInformation.getSessionUsername());
            jsonObject.addProperty("token", SessionInformation.getSessionToken());
            return jsonObject.toString();
        }

        @Override
        public void execute() {
            try {
                String jsonMessage = this.generateJsonMessage();

                DatagramPacket datagramPacket = new DatagramPacket(jsonMessage.getBytes(), jsonMessage.length(), InetAddress.getByName(Constants.BASE_URL), port);
                datagramSocket.send(datagramPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DatagramSocket datagramSocket;
    private Timer timer;

    public boolean createSocket(int port) {

        try {
            datagramSocket = new DatagramSocket(port);
            this.timer = new Timer(180000, new UDPStatus(port));
            this.timer.start();
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
        this.timer.stop();
        this.datagramSocket.close();
    }

}

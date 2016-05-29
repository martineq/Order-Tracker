package ordertracker.util

import ordertracker.constants.HttpProtocol
import ordertracker.constants.ServerDetails
import org.grails.validation.routines.InetAddressValidator

class Server {

    private static Server server = null

    public static String getURL() {
        return ServerInstance().getLocalSocket()
    }

    public static ServerInstance() {
        if (server == null) {
            server = new Server( Server.loadPublicIP(), Server.loadPublicPort() )
        }
        return server
    }

    private static def loadPublicIP() {
        def serverIP = new ConfigFileReader(ServerDetails.SERVER_IP_FILE.toString()).readNumbers(ServerDetails.SERVER_DEFAULT_IP.toString())
        if ( InetAddressValidator.getInstance().isValidInet4Address(serverIP) == false )
            serverIP = ServerDetails.SERVER_DEFAULT_IP.toString()
        return serverIP
    }

    private static def loadPublicPort() {
        return new ConfigFileReader(ServerDetails.SERVER_PORT_FILE.toString()).readNumbers(ServerDetails.SERVER_DEFAULT_PORT.toString())
    }

    private String serverIP
    private String serverPort

    Server(String serverIP, String serverPort) {
        this.serverIP = serverIP
        this.serverPort = serverPort
    }

    String getAddress() {
        return this.serverIP
    }

    String getPort() {
        return this.serverPort
    }

    String getLocalSocket(){
        return HttpProtocol.HTTP.toString()+'://'+this.serverIP+':'+this.serverPort
    }

}

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {
    public static final int SERVER_PORT =998;
    public static final int CLIENT_PORT =999;
    public static final int BUFFER_SIZE =1024;
    public static DatagramSocket DS;
    public static final byte BUFFER[] =new byte[BUFFER_SIZE];
    public static final Scanner scanner=new Scanner(System.in);

    public static void receive() throws IOException {
        DatagramPacket p=new DatagramPacket(BUFFER, BUFFER.length);
        DS.receive(p);
        System.out.print("other: ");
        System.out.println(new String(p.getData(),0,p.getLength()));
    }

    public static void send(int port) throws IOException {
        System.out.print("me: ");
        String text=scanner.nextLine();
        DS.send(new DatagramPacket(text.getBytes(),text.length(), InetAddress.getLoopbackAddress(),port));
    }

    public static void theServer() throws IOException {
        while (true){
            send(CLIENT_PORT);
            receive();
        }
    }

    public static void TheClient() throws IOException {
        while (true){
            receive();
            send(SERVER_PORT);
         }
    }

    public static void main(String[] args) throws IOException {
        if(args.length == 1) {
            DS = new DatagramSocket(SERVER_PORT);
            theServer();
        }else {
            DS = new DatagramSocket(CLIENT_PORT);
            TheClient();
        }
    }
}

package server;

import java.io.*;
import java.net.*;
import interfaces.Executable;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                System.out.println("Waiting for client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                String classFile = (String) in.readObject();
            //    classFile = classFile.replaceFirst("client", "server");

                byte[] b = (byte[]) in.readObject();
                FileOutputStream fos = new FileOutputStream(classFile);
                fos.write(b);

                Executable ex = (Executable) in.readObject();

                double startTime = System.nanoTime();
                Object output = ex.execute();
                double endTime = System.nanoTime();
                double completionTime = endTime - startTime;

                ResultImpl r = new ResultImpl(output, completionTime);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                out.writeObject(classFile);
                FileInputStream fis = new FileInputStream(classFile);
                byte[] bo = new byte[fis.available()];
                fis.read(bo);
                out.writeObject(bo);
                out.writeObject(r);

                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package client;

import java.io.*;
import java.net.*;
import interfaces.Result;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try {
            Socket clientSocket = new Socket(host, port);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            String classFile = "C:\\Users\\Home\\Desktop\\Uni\\КПП\\Laba5\\src\\client\\Client.java";
            out.writeObject(classFile);

            FileInputStream fis = new FileInputStream(classFile);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.writeObject(b);

            int num = 5; // Приклад значення для обчислення факторіала
            JobOne aJob = new JobOne(num);
            out.writeObject(aJob);

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            classFile = (String) in.readObject();

            b = (byte[]) in.readObject();
            FileOutputStream fos = new FileOutputStream(classFile);
            fos.write(b);

            Result r = (Result) in.readObject();
            System.out.println("result = " + r.output() + ", time taken = " + r.scoreTime() + "ns");

            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

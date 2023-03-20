package server;

import java.io.IOException;

public class testServerMethods {
    public static void main(String[] args) throws IOException {
        Server server = new Server(1);
        server.handleLoadCourses("Automne");
    }
}

package ui;

import java.io.IOException;
import network.WebPageRead;

public class Main {

    //starts the app
    public static void main(String[] args) throws IOException {
        UserInOut userInOut = new UserInOut();
        WebPageRead.read("https://www.students.cs.ubc.ca/~cs-210/2018w1/welcomemsg.html");
        userInOut.run();
    }
}

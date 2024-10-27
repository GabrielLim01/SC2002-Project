package Views;

import Controllers.*;
import Models.*;
import Utility.*;

import java.util.Scanner;

public class HMSApp {

    // Currently doing testing in TestEnvironment
    // Main driver code has been moved to Special for now

    public static void main(String[] args) {
        Special special = new Special();
        special.switchUserAccounts();
    }
}

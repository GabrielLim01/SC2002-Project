package Utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class DataProcessingTest {

    @Test
    void testCase1() {
        DataProcessing dataProcessing = new DataProcessing();
        Assertions.assertNotNull(dataProcessing);

        System.out.println("Hello world");
    }
}
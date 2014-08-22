package uk.co.jdpatrick.JEngine.tests;

import uk.co.jdpatrick.JEngine.JEngine;

/**
 * @author Jack Patrick
 */
public class TestsMain {

    public static void main(String[] args) {
        JEngine.start(new Test("JEngine tests"), 800, 600, 60);
    }
}

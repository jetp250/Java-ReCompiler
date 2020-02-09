package me.jetp250.recompiler;

import me.jetp250.recompiler.jclass.JavaClassHeader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No input files!");
            return;
        }
        System.out.println("Opening file: \"" + args[0] + "\"");

        try (DataInputStream stream = new DataInputStream(new FileInputStream(new File(args[0])))) {
            JavaClassHeader header = JavaClassHeader.fromBytes(stream);
            System.out.println(header.toString());
        }
    }

}

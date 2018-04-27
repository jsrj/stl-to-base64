package com.arjaycodes;

import com.arjaycodes.Base64.Encoder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Encoder fileEncoder = new Encoder("/Users/jsrj/Documents/repos/stl-to-base64/Sample_STLs/SFD_Linked.stl");

        //fileEncoder.getEncodedStringSegments();
        fileEncoder.writeEncodedStringToFile();
    }
}

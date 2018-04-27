package com.arjaycodes.Base64;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Encoder {
    private File  fileToEncode;
    private byte[]   fileBytes;
    private String encodedFile;

    public Encoder(String filePath) throws IOException {
        setFileToEncode(new File(filePath));
        InputStream fileStream = new FileInputStream(getFileToEncode());

        if (getFileToEncode().length() <= 0x7FFFFFFF) {
            setFileBytes(new byte[ (int) getFileToEncode().length() ]);

            int readOffset = 0;
            int bytesRead  = 0;
            while (
                    (readOffset < getFileBytes().length)
                            &&
                            (bytesRead=fileStream
                                    .read(
                                            getFileBytes(),
                                            readOffset,
                                            getFileBytes().length-readOffset
                                    )
                            ) >= 0) {
                readOffset += bytesRead;
            }

            if (readOffset < getFileBytes().length) {
                throw new IOException("Did not get all data from file.");
            }

            fileStream.close();
            AtomicReference<String> encodedString = new AtomicReference<>();

            setEncodedFile(Base64.getEncoder().encodeToString(getFileBytes()));
        }
    }

    public List<String> getEncodedStringSegments() {
        // Breaks encoded string down into smaller segments to avoid running into memory allocation issues

        List<String> segmentedString = new ArrayList<>();
        StringBuilder  stringSegment = new StringBuilder();
        int iterator = 0;
        for(char character : getEncodedFile().toCharArray()) {
            // Breaks each segment into 9999 characters each.
            if (iterator < 9999) {
                stringSegment.append(character);
                iterator++;
            } else {
                segmentedString.add(stringSegment.toString());
                iterator = 0;
            }
        }
        System.out.println(segmentedString.size());
        return segmentedString;
    }

    public void writeEncodedStringToFile() throws IOException {
        // For testing with external decoder software
        FileWriter writer = new FileWriter(new File("/Users/jsrj/Documents/repos/stl-to-base64/Sample_STLs/encoded.txt"));
        this.getEncodedStringSegments().forEach(segment -> {
            try {
                System.out.printf("Writing %s to file...\n", segment);
                writer.write(segment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    // Getters & Setters
    private File getFileToEncode() {
        return fileToEncode;
    }
    private void setFileToEncode(File fileToEncode) {
        this.fileToEncode = fileToEncode;
    }
    private byte[] getFileBytes() {
        return fileBytes;
    }
    private void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    public String getEncodedFile() {
        return encodedFile;
    }
    private void setEncodedFile(String encodedFile) {
        this.encodedFile = encodedFile;
    }
}

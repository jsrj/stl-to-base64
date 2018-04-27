package com.arjaycodes.Base64;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

public class Encode {
    private File  fileToEncode;
    private byte[]   fileBytes;
    private String encodedFile;

    public Encode(String filePath) throws IOException {
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

            System.out.println(Base64.getEncoder().encodeToString(getFileBytes()));
        }
    }

    // Getters & Setters
    public File getFileToEncode() {
        return fileToEncode;
    }
    public void setFileToEncode(File fileToEncode) {
        this.fileToEncode = fileToEncode;
    }
    public byte[] getFileBytes() {
        return fileBytes;
    }
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    public String getEncodedFile() {
        return encodedFile;
    }
    public void setEncodedFile(String encodedFile) {
        this.encodedFile = encodedFile;
    }
}

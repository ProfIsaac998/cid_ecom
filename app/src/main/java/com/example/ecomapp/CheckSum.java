package com.example.ecomapp;

public class CheckSum {
    public static void main(String args[]){

        String directory="D:\\Checksum text\\Checksum.txt";

        MessageDigest psswd = MessageDigest.getInstance("SHA1");

        FileInputStream fInput = new FileInputStream(directory);
        byte[] dataBytes=new byte[1000];

        int bRead=0;

        while((bRead = directory.read(dataBytes)) != -1){
            psswd.update(dataBytes,0,bRead);
        }

        byte dBytes=directory.digest();

        StringBuffer sb = new StringBuffer("");

        for (int i=0; i<dBytes.length; i++){
            sb.append(Integer.toString((dataBytes[i] & 0xff)+0x100,16)).substring(1));
        }

        System.out.println("Determining the file's checksum: " + sb.toString());

        fInput.close();

    }
}

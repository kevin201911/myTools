package com.my.tools.zookeeper.utils;

import java.io.*;

/**
 * @author wq
 * @date 2019/9/22
 */
public class ConversionUtil {
    /**
     * Object to bytes byte [ ].
     *
     * @param obj the obj
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public static byte[] objectToBytes(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);

        byte[] bytes = baos.toByteArray();

        baos.close();
        oos.close();

        return bytes;
    }

    /**
     * Bytes to object object.
     *
     * @param bytes the bytes
     * @return the object
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object object = ois.readObject();

        bais.close();
        ois.close();

        return object;
    }
}

package com.statiocraft.jukebox.fromapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);

        o.writeObject(object);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);

        return o.readObject();
    }

    public static boolean save(File file, Serializable object) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] e = serialize(object);

            out.write(e);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ioexception1) {
                ioexception1.printStackTrace();
            }

        }

        return false;
    }

    public static Object load(File file, Class paramClass) {
        try {
            return load(file);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Serializable load(File file) {
        if (!file.exists()) {
            return null;
        } else {
            FileInputStream i = null;
            DataInputStream in = null;

            try {
                i = new FileInputStream(file);
                in = new DataInputStream(i);
                int e = 0;
                byte[] b = new byte[4096];

                while (true) {
                    try {
                        b[e] = in.readByte();
                    } catch (Exception exception) {
                        Serializable serializable = (Serializable) deserialize(b);

                        return serializable;
                    }

                    ++e;
                }
            } catch (Exception exception1) {
                exception1.printStackTrace();
                return null;
            } finally {
                try {
                    in.close();
                } catch (Exception exception2) {
                    exception2.printStackTrace();
                }

                try {
                    i.close();
                } catch (Exception exception3) {
                    exception3.printStackTrace();
                }

            }
        }
    }
}

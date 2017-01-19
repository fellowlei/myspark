package com.mark.hadoop;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by root on 17-1-18.
 */
public class MkdirHdfs {
    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) throws Exception {
        printFile("hdfs://localhost:9000/user/root/word.txt");
//        copyFromRemote("hdfs://localhost:9000/user/root/word.txt", "/home/hadoop/hello.txt");
    }

    public static void copyFromRemote(String remoteURL, String localURL) throws IOException {
        final URL url = new URL(remoteURL);
        final InputStream in = url.openStream();
        OutputStream out = new FileOutputStream(localURL);
        IOUtils.copyBytes(in, out, 1024, true);
        out.close();
        in.close();
    }

    public static void printFile(String path) throws IOException {
        InputStream in = new URL(path).openStream();
        OutputStream out = System.out;
        IOUtils.copyBytes(in, out, 4096, false);
        IOUtils.closeStream(in);
    }
}

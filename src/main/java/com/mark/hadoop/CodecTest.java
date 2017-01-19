package com.mark.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.InputStream;

/**
 * Created by root on 17-1-19.
 */
public class CodecTest {
    //压缩文件
    public static void compress(String codecClassName) throws Exception {
        Class<?> codecClass = Class.forName(codecClassName);
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,conf);

        FSDataOutputStream outputStream = fs.create(new Path("sample.gz"));
        FSDataInputStream in = fs.open(new Path("sample.txt"));

        CompressionOutputStream out = codec.createOutputStream(outputStream);
        IOUtils.copyBytes(in,out,conf);
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }
    //解压缩
    public static  void uncompress(String fileName) throws Exception {
        Class<?> codecClass = Class.forName("org.apache.hadoop.io.compress.GzipCodec");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,conf);
        FSDataInputStream inputStream = fs.open(new Path(fileName));
        InputStream in = codec.createInputStream(inputStream);

        IOUtils.copyBytes(in, System.out,conf);
        IOUtils.closeStream(in);
    }


    public static void main(String[] args) throws Exception {
        compress("org.apache.hadoop.io.compress.GzipCodec");
        uncompress("sample.gz");
    }
}

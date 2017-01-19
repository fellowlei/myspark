package com.mark.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by root on 17-1-18.
 */
public class MyHdfsOpsDemo {

    public static FileSystem getFileSystem() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
//        FileSystem fs = FileSystem.get(URI.create("/"), conf, "root");
        return fs;
    }

    public static void mkdirs(Path path) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            System.out.println("create path " + path.getName());
        } else {
            System.out.println("exists path: " + path.getName());
        }
    }

    public static void remove(Path path, boolean flag) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
        fs.delete(path, flag);
        System.out.println("delete path " + path.getName());
        fs.close();
    }

    public static void copyFromLocal(Path localPath, Path remotePath) throws Exception {
        FileSystem fs = getFileSystem();
        fs.copyFromLocalFile(localPath, remotePath);
        fs.close();
    }

    public static void printPath(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FSDataInputStream in = fs.open(path);
        OutputStream out = System.out;
        IOUtils.copyBytes(in, out, 4096, false);
        System.out.println("from begin....");
        in.seek(0);
        IOUtils.copyBytes(in, out, 4906, false);
        IOUtils.closeStream(in);
    }

    public static void createFile(Path path) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
        FSDataOutputStream out = fs.create(path);
        out.writeChars("my test txt1\n");
        out.writeChars("my test txt2\n");
        out.flush();
        out.close();
        System.out.println("create file : " + path.getName());
    }

    // append file
    public static void append(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FSDataOutputStream out = fs.append(path);
        out.writeChars("append test 1\n");
        out.writeChars("append test 2\n");
        out.flush();
        IOUtils.closeStream(out);
    }

    // progress
    public static void createWithProgress(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FSDataOutputStream fout = fs.create(path, new Progressable() {
            @Override
            public void progress() {
                System.out.println("write is in progress......");
            }
        });

        Scanner sc = new Scanner(System.in);
        System.out.println("Please type your enter : ");
        String name = sc.nextLine();
        while (!"quit".equals(name)) {
            if (null == name || "".equals(name.trim())) {
                continue;
            }
            fout.writeChars(name);
            System.out.print("Please type your enter : ");
            name = sc.nextLine();
        }
    }

    public static void listFiles(Path path) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(path, false);
        while (it.hasNext()) {
            LocatedFileStatus status = it.next();
            System.out.println(status.getPath().getName() + "|" + status.getLen() + "|" + status.getOwner());
        }
    }

    public static void showStatus(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FileStatus status = fs.getFileStatus(path);
        System.out.println(status.getPath().getName() + "|" + status.getPath().getParent().getName() + "|"
                + status.getBlockSize() + "|" + status.getReplication() + "|" + status.getOwner());

    }


    public static void listStatus(Path path) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);

        FileStatus[] fileStatuses = fs.listStatus(path);
        for (FileStatus status : fileStatuses) {
            System.out.println(status.getPath().getName() + "|" + status.getPath().getParent().getName() + "|"
                    + status.getBlockSize() + "|" + status.getReplication() + "|" + status.getOwner());
        }
        System.out.println("show status#################");
        Path[] listPaths = FileUtil.stat2Paths(fileStatuses);
        for (Path p : listPaths) {
            System.out.println(p);
        }
    }


    public static void listStatusWithFilter(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(path, new PathFilter() {
            @Override
            public boolean accept(Path path) {
                String name = path.getName();
                if (name.endsWith(".txt")) {
                    return true;
                }
                return false;
            }
        });
        for (FileStatus status : fileStatuses) {
            System.out.println(status.getPath().getName() + "|" + status.getPath().getParent().getName() + "|"
                    + status.getBlockSize() + "|" + status.getReplication() + "|" + status.getOwner());
        }

    }

    public static void ListStatusWithPattern(Path path) throws Exception {
        FileSystem fs = getFileSystem();
        FileStatus[] fileStatuses = fs.globStatus(path);
        for (FileStatus status : fileStatuses) {
            System.out.println(status.getPath());
        }
    }


    public static void main(String[] args) throws Exception {
//        mkdirs(new Path("/demo"));
//        remove(new Path("/demo"),false);
//        printPath(new Path("word.txt"));
//        createFile(new Path("test.txt"));
//        append(new Path("test.txt"));

//        createWithProgress(new Path("progress.txt"));
//        listFiles(new Path("/user/root/"));

//        showStatus(new Path("/user/root/progress.txt"));

//        listStatus(new Path("/user/root"));

//        listStatusWithFilter(new Path("/user/root"));


    }
}

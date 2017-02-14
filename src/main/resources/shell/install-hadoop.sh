swdir=/home/hadoo/sw
# install java
# sw jdk-8u111-linux-x64.tar.gz
mkdir /usr/lib/jvm
cd $swdir
tar -xzvf jdk-8u111-linux-x64.tar.gz -C /usr/lib/jvm
cd /usr/lib/jvm
mv jdk1.8.0_111 jdk

echo """export JAVA_HOME=/usr/lib/jvm/jdk
export CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib:$CLASSPATH
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH""" >> /etc/profile

source /etc/profile

java -version

echo "##############install java over####################"

# install scala
# sw scala-2.11.8.tgz
cd $swdir
tar -xzvf scala-2.11.8.tgz -C /usr/local
cd /usr/local
mv scala-2.11.8 scala
echo """export SCALA_HOME=/usr/local/scala
export PATH=$SCALA_HOME/bin:$PATH""" >> /etc/profile

source /etc/profile
scala -version

echo "#############install scala over###################"
# install hadoop
# sw hadoop-2.6.4.tar.gz
cd $swdir
tar -xzvf hadoop-2.6.4.tar.gz -C /usr/local
cd /usr/local
mv hadoop-2.6.4 hadoop
chown -R root:root hadoop

echo """export HADOOP_HOME=/usr/local/hadoop
export PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH""" >> /etc/profile

source /etc/profile
hadoop version

# config hadoop
cd /usr/local/hadoop/etc/hadoop
#config core-site.xml
echo """<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>hadoop.tmp.dir</name>
             <value>file:/usr/local/hadoop/tmp</value>
             <description>Abase for other temporary directories.</description>
        </property>
        <property>
             <name>fs.defaultFS</name>
             <value>hdfs://localhost:9000</value>
        </property>
</configuration>
""" > core-site.xml
#config hdfs-site.xml
echo """<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>dfs.replication</name>
             <value>1</value>
        </property>
        <property>
             <name>dfs.namenode.name.dir</name>
             <value>file:/usr/local/hadoop/tmp/dfs/name</value>
        </property>
        <property>
             <name>dfs.datanode.data.dir</name>
             <value>file:/usr/local/hadoop/tmp/dfs/data</value>
        </property>
</configuration>
""" > hdfs-site.xml

#hadoop-env.sh
sed -i "s/${JAVA_HOME}/\/usr\/lib\/jvm\/jdk/g" hadoop-env.sh

#config mapred-site.xml
echo """<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
        <property>
             <name>mapreduce.framework.name</name>
             <value>yarn</value>
        </property>
</configuration>
""" > mapred-site.xml

#config yarn-site.xml
echo """<?xml version="1.0" encoding="UTF-8"?>
<configuration>
        <property>
             <name>yarn.nodemanager.aux-services</name>
             <value>mapreduce_shuffle</value>
            </property>
</configuration>
""" > yarn-site.xml


#gen start-hadoop.sh
cd /usr/local/hadoop
echo """#!/bin/bash
# 启动hadoop
start-dfs.sh
# 启动yarn
start-yarn.sh
# 启动历史服务器,以便在Web中查看任务运行情况
mr-jobhistory-daemon.sh start historyserver
""" > start-hadoop.sh
#gen stop-hadoop.sh
echo """#!/bin/bash
# 停止历史服务器
mr-jobhistory-daemon.sh stop historyserver
# 停止yarn
stop-yarn.sh
# 停止hadoop
stop-dfs.sh
""" > stop-hadoop.sh

chmod +x start-hadoop.sh
chmod +x stop-hadoop.sh

echo "#################install hadoop over#################"

# install spark
# sw spark-1.6.1-bin-hadoop2.6.tgz
cd $swdir
tar -zxvf spark-1.6.1-bin-hadoop2.6.tgz -C /usr/local
cd /usr/local
mv spark-1.6.1-bin-hadoop2.6 spark

echo """export SPARK_HOME=/usr/local/spark
export PATH=$SPARK_HOME/bin:$SPARK_HOME/sbin:$PATH
""" >> /etc/profile

chown -R root:root spark

#config spark-env.sh
cd /usr/local/spark/conf
echo """export SPARK_DIST_CLASSPATH=$(/usr/local/hadoop/bin/hadoop classpath)
export JAVA_HOME=/usr/lib/jvm/jdk
""" > spark-env.sh

chmod +x spark-env.sh

#test
/usr/local/spark/bin/run-example SparkPi 2>&1 | grep "Pi is roughly"



#gen  start-spark.sh
cd /usr/local/spark

echo """#!/bin/bash
# 启动Hadoop以及yarn
start-dfs.sh
start-yarn.sh
# 启动历史服务器
mr-jobhistory-daemon.sh start historyserver
# 启动Spark
/usr/local/spark/sbin/start-all.sh
""" > start-spark.sh

echo """#!/bin/bash
# 停止Spark
/usr/local/spark/sbin/stop-all.sh
# 停止历史服务器
mr-jobhistory-daemon.sh stop historyserver
# 停止Hadoop以及yarn
stop-dfs.sh
stop-yarn.sh
""" > stop-spark.sh
chmod +x start-spark.sh
chmod +x stop-spark.sh
 

echo "###############install spark over############"

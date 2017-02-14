#mysql -uroot -p1 --default-character-set=utf8 -e "select * from test.user;"


MYSQL="mysql -uroot -p1 --default-character-set=utf8 -A -N"
#这里面有两个参数，-A、-N，-A的含义是不去预读全部数据表信息，这样可以解决在数据表很多的时候卡死的问题
#-N，很简单，Don't write column names in results，获取的数据信息省去列名称

sql="select * from test.user"
result="$($MYSQL -e "$sql")"

dump_data=./data.user.txt

echo -e "$result" > $dump_data


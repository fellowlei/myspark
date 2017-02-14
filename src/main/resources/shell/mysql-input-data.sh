MYSQL="mysql -uroot -p1 --default-character-set=utf8 -A -N"
cat data.user.txt | while read id name
do
	sql="insert into test.user(id,name) values(${id},'${name}');"
	$MYSQL -e "$sql"
done



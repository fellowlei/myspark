#http://phantomjs.org/
#phantomjs study
cd /home/hadoop/sw
#wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2
tar -jxvf phantomjs-2.1.1-linux-x86_64.tar.bz2 -C /usr/local
cd /usr/local
mv phantomjs-2.1.1-linux-x86_64/ phantomjs
echo """export PHANTOMJS_HOME=/usr/local/phantomjs/
export PATH=\$PATH:\$PHANTOMJS_HOME/bin
""" >> /etc/profile

source /etc/profile

#test
#http://phantomjs.org/screen-capture.html
cd /usr/local/phantomjs
mkdir mycode
cd /usr/local/phantomjs/mycode
echo """var page = require('webpage').create();
page.open('http://github.com/', function() {
  page.render('github.png');
  phantom.exit();
});
""" > github.js
echo "gen github.js success"

echo """var page = require('webpage').create();
var system = require("system")
var url = "http://" + system.args[1];
var name = system.args[2];
page.open(url, function() {
  page.render(name);
  phantom.exit();
});
""" > baidu.js

echo "start test capture..."
phantomjs github.js
echo "gen github png success"
cd /usr/local/phantomjs/examples
#gen png
phantomjs rasterize.js http://ariya.github.io/svg/tiger.svg tiger.png
echo "gen tiger.png success"
#gen png
phantomjs rasterize.js https://dmitrybaranovskiy.github.io/raphael/polar-clock.html clock.png
echo "gen clock.png success"

#gen pdf
phantomjs rasterize.js 'http://en.wikipedia.org/w/index.php?title=Jakarta&printable=yes' jakarta.pdf
echo "gen jakarta.pdf success"


#convert png size
#install ImageMagick
#wget https://www.imagemagick.org/download/ImageMagick.tar.gz
#tar -xvf ImageMagick.tar.gz
#cd ImageMagick-7.0.4-8/
#./configure 
#make

#convert png
#convert -resize 320X240 github.png  github.min.png

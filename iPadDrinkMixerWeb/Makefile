SHELL = /bin/sh
CHMOD = chmod
CP = cp
MV = mv
NOOP = $(SHELL) -c true
RM_F = rm -f
RM_RF = rm -rf
TEST_F = test -f
TOUCH = touch
UMASK_NULL = umask 0
DEV_NULL = > /dev/null 2>&1
MKPATH = mkdir -p
CAT = cat
MAKE = make
OPEN = open
ECHO = echo
ECHO_N = echo -n
JAVA = java
DOXYGEN = 
IPHONE_DOCSET_TMPDIR = docs/iphone/tmp

all :: iphone blackberry android docs

clean :: clean_docs clean_libs

clean_docs:
	-$(RM_RF) docs/javascript
	-$(RM_RF) docs/iphone

clean_libs:
	-$(RM_RF) lib

iphone/www/phonegap.js: lib/iphone/phonegap-min.js
	$(CP) lib/iphone/phonegap-min.js $@

docs :: javascript_docs iphone_docs

iphone_docs:
javascript_docs :: javascripts/*.js
	$(JAVA) -jar util/jsdoc-toolkit/jsrun.jar util/jsdoc-toolkit/app/run.js -a -d=docs/javascript -t=util/jsdoc-toolkit/templates/jsdoc  javascripts/*.js
iphone: lib/iphone/phonegap-min.js

lib/iphone/phonegap-min.js: lib/iphone/phonegap.js
	$(JAVA) -jar util/yuicompressor-2.4.2.jar --charset UTF-8 -o $@ lib/iphone/phonegap.js

lib/iphone/phonegap.js: javascripts/phonegap.js.base javascripts/*.js javascripts/iphone/*.js
	$(RM_RF) lib/iphone
	$(MKPATH) lib/iphone
	$(RM_F) $@
	$(CAT) javascripts/phonegap.js.base >> $@
	$(CAT) javascripts/*.js >> $@
	$(CAT) javascripts/iphone/*.js >> $@
blackberry: lib/blackberry/phonegap-min.js

lib/blackberry/phonegap-min.js: lib/blackberry/phonegap.js
	$(JAVA) -jar util/yuicompressor-2.4.2.jar --charset UTF-8 -o $@ lib/blackberry/phonegap.js

lib/blackberry/phonegap.js: javascripts/phonegap.js.base javascripts/*.js javascripts/blackberry/*.js
	$(RM_RF) lib/blackberry
	$(MKPATH) lib/blackberry
	$(RM_F) $@
	$(CAT) javascripts/phonegap.js.base >> $@
	$(CAT) javascripts/*.js >> $@
	$(CAT) javascripts/blackberry/*.js >> $@
android: lib/android/phonegap-min.js

lib/android/phonegap-min.js: lib/android/phonegap.js
	$(JAVA) -jar util/yuicompressor-2.4.2.jar --charset UTF-8 -o $@ lib/android/phonegap.js

lib/android/phonegap.js: javascripts/phonegap.js.base javascripts/*.js javascripts/android/*.js
	$(RM_RF) lib/android
	$(MKPATH) lib/android
	$(RM_F) $@
	$(CAT) javascripts/phonegap.js.base >> $@
	$(CAT) javascripts/*.js >> $@
	$(CAT) javascripts/android/*.js >> $@

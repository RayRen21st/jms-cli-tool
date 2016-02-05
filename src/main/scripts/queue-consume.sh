#!/bin/sh

# Execution directory
_bindir=`dirname $0`
_basedir=`cd ${_bindir}/../ && pwd`

LIB="${_basedir}/lib/*"

# java set? If not use JDK in MSDP
JAVA_HOME=${JAVA_HOME:="@JAVA_HOME@"}

# java options
JAVA_OPTS="-client -XX:PermSize=16m -Xms32m -Xmx1024m"

$JAVA_HOME/bin/java ${JAVA_OPTS} -cp "${LIB}" jms.client.App "$@"

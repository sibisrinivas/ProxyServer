@echo off
javac ProxyServer.java DataProcessorThread.java -d classes
java -cp "classes" com.network.proxy.ProxyServer 7443 192.168.1.1 8877
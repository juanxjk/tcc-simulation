

export LD_LIBRARY_PATH=/usr/lib/jni


#ECHO $LD_LIBRARY_PATH=/home/user/Desktop/j3d-1_5_2-linux-i586/lib/i386

java -cp ./HelloWorld3D.jar:./j3dcore.jar:./j3dutils.jar:./vecmath.jar   jat.examples.java3D.HelloWorld3D

echo press enter

read input



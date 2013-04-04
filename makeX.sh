#! /bin/bash/

echo "makeX?"

#Clean class files
for file in $(find -name *.class)
do
	echo "Removing $file"
	rm $file
done


LWJGLJARS='./lwjgl/jar/lwjgl.jar:./lwjgl/jar/lwjgl_util.jar'

for file in $(find -name *.java)
do
	javac -sourcepath ./source/ -cp $LWJGLJARS $file -d ./bin/class/
done


#if (($1 == c))
#then
#	for file in 


for /r %%f in (*.java) do (
 javac -sourcepath source -cp lwjgl\jar\lwjgl.jar;lwjgl\jar\lwjgl_util.jar %%f -d bin\class
  )

call runDebug.bat

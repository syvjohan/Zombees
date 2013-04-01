

for /r %%f in (*.java) do (
 javac -sourcepath source -cp lwjgl\jar\lwjgl.jar;lwjgl\jar\lwjgl_util.jar %%f -d bin\class
  )

:: Prefer this over runJar.bat for assertions when debugging.
call runDebug.bat

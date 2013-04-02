
:: Clean build
FOR /F "tokens=*" %%G IN ('DIR /B /S *.class') DO DEL /S /Q %%G

for /r %%f in (*.java) do (
 javac -sourcepath source -cp lwjgl\jar\lwjgl.jar;lwjgl\jar\lwjgl_util.jar %%f -d bin\class
  )

:: Give time to see compilation results, when running from CMD.
PAUSE

:: Prefer this over runJar.bat for assertions when debugging.
call runDebug.bat

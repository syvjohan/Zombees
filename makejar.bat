
::It's probably better if we just make rars for distributing the software.

::FOR /F "tokens=*" %%G IN ('DIR /B /S *.class') DO DEL /S /Q %%G
::call compile.bat

DEL bin\jar\Zombees.jar
jar -0vcmf manifest.txt bin\jar\Zombees.jar -C bin\class\ .

::call runjar.bat


call compile.bat

DEL bin\jar\Zombees.jar
jar -0vcmf manifest.txt bin\jar\Zombees.jar -C bin\class\ .

call runjar.bat

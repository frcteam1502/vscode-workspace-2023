# CLI

WPI-vscode sets some environment variables for you:
```
GIT_ASKPASS                    c:\Users\Public\wpilib\2023\vscode\resources\app\extensions\git\dist\askpass-main.js 
JAVA_HOME                      C:\Users\Public\wpilib\2023\jdk
NIEXTCCOMPILERSUPP             C:\Program Files (x86)\National Instruments\Shared\ExternalCompilerSupport\C\ 
OV_NI_PLUGIN_DIR               C:\Program Files\National Instruments\Shared\OpenVINO\
VSCODE_GIT_ASKPASS_EXTRA_ARGS  --ms-enable-electron-run-as-node
VSCODE_GIT_ASKPASS_MAIN        c:\Users\Public\wpilib\2023\vscode\resources\app\extensions\git\dist\askpass-main.js 
VSCODE_GIT_ASKPASS_NODE        C:\Users\Public\wpilib\2023\vscode\Code.exe
VSCODE_GIT_IPC_HANDLE          \\.\pipe\vscode-git-bb324bfa8b-sock
VSCODE_INJECTION               1
```

# New Project no build tools WPI-vscode
ctrl+shift+P: `java: Create java project...`

Makes a sample src\App.java

To compile: `javac src\App.java` - makes src\App.class
To run: `java -cp src App` - prints "Hello World", NOTE -cp (classpath) needs to be specified and "App" has no extension

Put packages in director: `javac -d .\build\classes .\src\team1502\configuration\*.java src\App.java`
(what is diff bin vs build\classes)

run it: `java -cp .\build\classes App`


# UNIT TESTING
JUnit - import 

Heap starts at 64MB and max is 64MB
```
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
```

```
${env:HALSIM_EXTENSIONS}='C:\Users\jonathan.berent\source\frcteam\CompBot-2023\build\jni\release\halsim_gui.dll;'; ${env:PATH}='C:\Users\jonathan.berent\source\frcteam\CompBot-2023\build\jni\release;C:\WINDOWS\system32\'; & 'C:\Users\Public\wpilib\2023\jdk\bin\java.exe' '-agentlib:jdwp=transport=dt_socket,server=n,suspend=y,address=localhost:59351' '@C:\Users\JONATH~1.BER\AppData\Local\Temp\cp_ertcds8uv9d0575x18obhu88s.argfile' 'frc.robot.Main'

c:; cd 'c:\Users\jonathan.berent\source\frcteam\CompBot-2023'; ${env:HALSIM_EXTENSIONS}='C:\Users\jonathan.berent\source\frcteam\CompBot-2023\build\jni\release\halsim_gui.dll;'; ${env:PATH}='C:\Users\jonathan.berent\source\frcteam\CompBot-2023\build\jni\release;C:\WINDOWS\system32\'; & 'C:\Users\Public\wpilib\2023\jdk\bin\java.exe' '-agentlib:jdwp=transport=dt_socket,server=n,suspend=y,address=localhost:61306' '@C:\Users\JONATH~1.BER\AppData\Local\Temp\cp_ertcds8uv9d0575x18obhu88s.argfile' 'frc.robot.Main'


```
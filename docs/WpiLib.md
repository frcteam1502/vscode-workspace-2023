# WpiLib VSCode

## Build and Deploy
 .\gradlew.bat build
 .\gradlew.bat deploy  -PteamNumber=1502 --offline  -Dorg.gradle.java.home="C:\Users\Public\wpilib\2023\jdk"
 
 ?? .\gradlew.bat deployfrcJavaroborio


## Tasks

 .\gradlew.bat tasks

set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

> Task :tasks

------------------------------------------------------------
Tasks runnable from root project 'vscode-workspace-2023'
------------------------------------------------------------

Build tasks
-----------
assemble - Assembles the outputs of this project.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

DeployUtils tasks
-----------------
deploy - Deploy all artifacts on all targets
deployfrcJavaroborio - Deploys frcJava to roborio
deployfrcStaticFileDeployroborio - Deploys frcStaticFileDeploy to roborio
deployjrefrcJavaroborio - Deploys jrefrcJava to roborio
deploynativeZipsfrcJavaroborio - Deploys nativeZipsfrcJava to roborio
deployprogramKillroborioroborio - Deploys programKillroborio to roborio
deployprogramStartfrcJavaroborio - Deploys programStartfrcJava to roborio
deployroborio - Deploy task for roborio
deployrobotCommandfrcJavaroborio - Deploys robotCommandfrcJava to roborio
discoverroborio - Determine the address(es) of target roborio

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code.

GradleRIO tasks
---------------
DataLogTool - Run the tool DataLogTool
deployStageroborioAfterProgramKill - Deploy stage AfterProgramKill for roborio
deployStageroborioAfterProgramStart - Deploy stage AfterProgramStart for roborio
deployStageroborioBeforeProgramKill - Deploy stage BeforeProgramKill for roborio
deployStageroborioBeforeProgramStart - Deploy stage BeforeProgramStart for roborio
deployStageroborioFileDeploy - Deploy stage FileDeploy for roborio
deployStageroborioFileRetreival - Deploy stage FileRetreival for roborio
deployStageroborioProgramKill - Deploy stage ProgramKill for roborio
deployStageroborioProgramStart - Deploy stage ProgramStart for roborio
explainRepositories - Explain all Maven Repos present on this project
Glass - Run the tool Glass
InstallAllTools - Install All Tools
OutlineViewer - Run the tool OutlineViewer
PathWeaver - Run the tool PathWeaver
PathWeaverInstall - Install the tool PathWeaver
roboRIOTeamNumberSetter - Run the tool roboRIOTeamNumberSetter
RobotBuilder - Run the tool RobotBuilder
RobotBuilderInstall - Install the tool RobotBuilder
ShuffleBoard - Run the tool ShuffleBoard
ShuffleBoardInstall - Install the tool ShuffleBoard
SmartDashboard - Run the tool SmartDashboard
SmartDashboardInstall - Install the tool SmartDashboard
SysId - Run the tool SysId
wpiVersions - Print all versions of the wpi block

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'vscode-workspace-2023'.
dependencies - Displays all dependencies declared in root project 'vscode-workspace-2023'.
dependencyInsight - Displays the insight into a specific dependency in root project 'vscode-workspace-2023'.
help - Displays a help message.
javaToolchains - Displays the detected java toolchains.
outgoingVariants - Displays the outgoing variants of root project 'vscode-workspace-2023'.
projects - Displays the sub-projects of root project 'vscode-workspace-2023'.
properties - Displays the properties of root project 'vscode-workspace-2023'.
resolvableConfigurations - Displays the configurations that can be resolved in root project 'vscode-workspace-2023'.
tasks - Displays the tasks runnable from root project 'vscode-workspace-2023'.

NativeUtils tasks
-----------------
vendordep - Install vendordep JSON file from URL or local wpilib folder

Verification tasks
------------------
check - Runs all checks.
test - Runs the test suite.

Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.

To see all tasks and more detail, run gradlew tasks --all

To see more detail about a task, run gradlew help --task <task>
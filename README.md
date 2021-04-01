# yq-proj

a simple java project

本项目是软设课程上，根据老师要求做的，实现对一个文件的读取，并格式化输出到另一个文件



### 作为简单项目，可在命令行编译运行

- 编译

  javac .\src\\\*.java -encoding utf-8 -d .\build

- 执行

  java -cp .\build lm.Yq [读取文件路径] [输出文件路径] [省名称] 

- 在build目录下

  生成 jar： jar cvfm yq.jar manifest.mf *

  执行 jar（可放在任意目录下执行）： java -jar yq.jar [读取文件路径] [输出文件路径] [省名称] 

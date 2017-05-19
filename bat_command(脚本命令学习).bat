@echo off
rem on/off,off表示命令行中不输出命令行，on则输出命令行
rem rem指令是备注指令或者采用::,也是备注

set num=10
rem set指令是设置变量值

echo %num%
rem echo指令是输出后面的数据
rem 引用变量需要放在2个百分号之间，例如%num%,本例输出结果为10


set str=1234567890
echo %str:~2,5%
rem ~2,5表示2之后的5个字符，因此上面输出为34567
echo %str:~-5%
rem 最后5个字符

pause
rem pause指令为暂停，运行会提示 “请按任意键继续. . .”, 命令运行中，按键盘的pause则会暂停

cls
rem 清理屏幕

set /p name=请输入用户名：
set /p password=请输入密码：
echo 我叫%name%,今年%password%岁
rem /p 本命令为暂停，等待用户输入，请输入用户名：wangqiubo；请输入密码：123456；我叫wangqiubo,今年123456岁
rem wangqiubo和123456是输入的数据

rem 计算，采用指令 /a
set /a num=1+2
echo %num%
rem 执行结果为3

rem @set 显示当前以及定义的变量，环境变量中的所有值，及其上面定义的变量
@set
@set s
rem 输出所有s开头的变量

rem 加载java的JAR包，并执行main函数(可以作为入库函数)
set tag_libs=qiubowang\libs
set tag_class=%tag_libs%\11.jar;%tag_libs%\22.jar;
rem tag_libs为jar所在的目录，tag_class即是包路径
java -classpath %tag_class% cn.wps.wow.common.utils.JarUtil deploy\mini\tag.js 
rem 加载tag_class指定的jar包到虚拟机，执行cn.wps.wow.common.utils.JarUtil中的main函数，后面是参数，传给main函数的args[]

java -cp %tag_class% cn.wps.wow.common.utils.JarUtil deploy\mini\tag.js 
rem 同理classpath,cp加载jar包

call aa.bat
rem 调用另外一个bat脚本
call bash aa.sh
rem 调用shell命令文件 aa.sh


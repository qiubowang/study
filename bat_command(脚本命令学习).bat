@echo off
rem on/off,off��ʾ�������в���������У�on�����������
rem remָ���Ǳ�עָ����߲���::,Ҳ�Ǳ�ע

set num=10
rem setָ�������ñ���ֵ

echo %num%
rem echoָ����������������
rem ���ñ�����Ҫ����2���ٷֺ�֮�䣬����%num%,����������Ϊ10


set str=1234567890
echo %str:~2,5%
rem ~2,5��ʾ2֮���5���ַ�������������Ϊ34567
echo %str:~-5%
rem ���5���ַ�

pause
rem pauseָ��Ϊ��ͣ�����л���ʾ ���밴���������. . .��, ���������У������̵�pause�����ͣ

cls
rem ������Ļ

set /p name=�������û�����
set /p password=���������룺
echo �ҽ�%name%,����%password%��
rem /p ������Ϊ��ͣ���ȴ��û����룬�������û�����wangqiubo�����������룺123456���ҽ�wangqiubo,����123456��
rem wangqiubo��123456�����������

rem ���㣬����ָ�� /a
set /a num=1+2
echo %num%
rem ִ�н��Ϊ3

rem @set ��ʾ��ǰ�Լ�����ı��������������е�����ֵ���������涨��ı���
@set
@set s
rem �������s��ͷ�ı���



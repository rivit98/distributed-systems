start C:\"Program Files"\ConEmu\ConEmu64.exe -cmdlist ^
cmd /k -cur_console:n      "cd && java -cp .\java\out\production\lab1 zad4.JavaUdpServer" ^|^|^| ^
cmd /k -cur_console:s1TVn  "cd && python .\python\zad4\PythonUdpClient.py" ^|^|^| ^
cmd /k -cur_console:s1THn  "cd && java -cp .\java\out\production\lab1 zad4.JavaUdpClient" ^|^|^| ^
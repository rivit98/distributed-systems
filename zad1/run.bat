start C:\"Program Files"\ConEmu\ConEmu64.exe -cmdlist ^
cmd /k -cur_console:n      "java -cp .\build\libs\zad1-1.0-all.jar server.Server" ^|^|^| ^
cmd /k -cur_console:s1TVn  "java -cp .\build\libs\zad1-1.0-all.jar client.ChatClient" ^|^|^| ^
cmd /k -cur_console:s1THn  "java -cp .\build\libs\zad1-1.0-all.jar client.ChatClient" ^|^|^| ^
cmd /k -cur_console:s2THn  "java -cp .\build\libs\zad1-1.0-all.jar client.ChatClient"
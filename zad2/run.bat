start C:\"Program Files"\ConEmu\ConEmu64.exe -cmdlist ^
cmd /k -cur_console:n      "java -cp .\build\libs\zad2-1.0-all.jar supplier.Supplier s1 tlen buty" ^|^|^| ^
cmd /k -cur_console:s1TVn  "java -cp .\build\libs\zad2-1.0-all.jar supplier.Supplier s2 tlen plecak" ^|^|^| ^
cmd /k -cur_console:s1THn  "java -cp .\build\libs\zad2-1.0-all.jar team.Team e1" ^|^|^| ^
cmd /k -cur_console:s2THn  "java -cp .\build\libs\zad2-1.0-all.jar team.Team e2"
start C:\"Program Files"\ConEmu\ConEmu64.exe -cmdlist ^
cmd /k -cur_console:n      "java -cp .\build\libs\zad2-1.0-all.jar admin.Admin" ^|^|^| ^
cmd /k -cur_console:s1TVn  "java -cp .\build\libs\zad2-1.0-all.jar supplier.Supplier supplier1 oxygen shoes" ^|^|^| ^
cmd /k -cur_console:s1THn  "java -cp .\build\libs\zad2-1.0-all.jar team.Team team1" ^|^|^| ^
cmd /k -cur_console:s2THn  "java -cp .\build\libs\zad2-1.0-all.jar team.Team team2" ^|^|^| ^
cmd /k -cur_console:s2TVn  "java -cp .\build\libs\zad2-1.0-all.jar supplier.Supplier supplier2 oxygen backpack"

# visualiser-obj-files
Návod, ako získať Fx...
Oracle v Java11 vyhodil JavaFX z distribúcie JDK11. Preto tento návod. Ak ale máte JDK >= 8 && JDK < 11, ste
zrejme v pohode. Testuj skôr. Alternatíva je doinštalovať si iné, staršie 8<=JDK<11.
------------------------------------------------------------------------------------------------------------------------
V IntelliJ skúste File/New/Project/JavaFX/Project SDK11/Next, ProjectName: PokusnyFX, Finish. V
src/sample/Main.java ak vidíte more červeného a projekt vám nejde skompilovať (a spustiť), čítajte ďalej. Ak vám
projekt ide, alebo máte JDK<11, alebo máte iné šťastie, nečítajte ďalej.
Inak:
1. Stiahnite si JavaFX SDK, podľa OS, a rozbalte .zip niekde u seba https://gluonhq.com/products/javafx/
Príklad: rozbalím do c:\Program Files\Java\javafx-sdk-11.0.2\, ak taký priečinom mám...
2. cez File/Project Structure/Project Settings/Libraries/+/Java nájdite lib podadresár, kde si si JavaFX SDK
rozbalili (teda Príklad: c:\Program Files\Java\javafx-sdk-11.0.2\lib), OK. Library 'lib' will be added to the selected
modules, vidíte tam meno projektu PokusnyFX , ťapnite na OK, ešte raz OK.
3. V src/sample/Main.java vám už javafx nesvieti červeno, skúste Run Main
4. ak dostanete podobnú smršť, pokračujte ďalším bodom Exception in Application start method
java.lang.reflect.InvocationTargetException
Caused by: java.lang.RuntimeException: Exception in Application start method
Caused by: java.lang.IllegalAccessError: class com.sun.javafx.fxml.FXMLLoaderHelper (in unnamed
5. ---- odtiaľto ďalej už môžete preskočením bodov 4..., a pokračovať, ak zrušíte celý podadresár sample/, a
použijte niečo z cvičenia. Problém je FX-Loader, ktorý nebudeme používať.
6. Run/Edit Configurations nastavte v VM Options
7. --module-path cesta-až-k-javafx-sdk-11/lib --add modules=javafx.controls,javafx.fxml
8. Príklad: --module-path "c:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
9. Apply, OK, Run.
10. Ak sa vám nezjaví okno s Hello World, skúste si návod prejsť ešte raz,
11. Ak ani potom, skúste https://stackoverflow.com/questions/52467561/intellij-cant-recognize-javafx-
11-with-openjdk-11

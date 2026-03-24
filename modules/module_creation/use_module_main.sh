javac --module-path calcylib -d calcyuse main.app/com/ashvin/main/Main.java main.app/module-info.java 
java --module-path calcylib:calcyuse -m main.app/com.ashvin.main.Main

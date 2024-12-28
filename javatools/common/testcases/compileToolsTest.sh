javac Student.java
java -classpath ../dist/tmcommon.jar:. com.thinking.machines.utils.TMSetterGetterGenerator Student
#Setter Getter are created in tmp.tmp file: Now copy paste that setter/getter in Student.java and next
java -classpath ../dist/tmcommon.jar:. com.thinking.machines.utils.TMAnalyzer Student 

javac Student.java
#Testing for creating construct by default
java -classpath ../dist/tmcommon.jar:. com.thinking.machines.utils.TMSetterGetterGenerator Student
#testing for not creating constructor by default
#javac -classpath ../dist/*:. com.thinking.machines.utils.TMSetterGetterGenerator Student constructor=false

#Setter Getter are created in tmp.tmp file: Now copy paste that setter/getter in Student.java and next

java -classpath ../dist/tmcommon.jar:. com.thinking.machines.utils.TMAnalyzer Student 

package com.ashvin.main;
import com.ashvin.calcy.*;

public class Main
{
public static void main(String args[])
{
SimpleInterestCalculator sic=new SimpleInterestCalculator();
System.out.println("Interest is: "+sic.calculateInterest(100,10,5));
}
}

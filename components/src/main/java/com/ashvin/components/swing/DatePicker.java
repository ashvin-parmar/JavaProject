package com.ashvin.components.swing;
import java.util.*;
import java.time.*;
public class DatePicker
{
int[][] getDays(int months,int year)
{
Date firstDaysOfMonth=new Date(year-1900,months-1,1);
Calendar firstDaysOfMonthCalendar=Calendar.getInstance();
firstDaysOfMonthCalendar.setTime(firstDaysOfMonth);
int daysOfWeekOfFirstDayOfMonth=firstDaysOfMonthCalendar.get(Calendar.DAY_OF_WEEK);
YearMonth yearMonth=YearMonth.of(year,months);
int numberOfDaysInMonth=yearMonth.lengthOfMonth();
Date lastDaysOfMonth=new Date(year-1900,months-1,numberOfDaysInMonth);
Calendar lastDaysOfMonthCalendar=Calendar.getInstance();
lastDaysOfMonthCalendar.setTime(lastDaysOfMonth);
int daysOfWeekOfLastDayOfMonth=lastDaysOfMonthCalendar.get(Calendar.DAY_OF_WEEK);
int weekNumber=lastDaysOfMonthCalendar.get(Calendar.WEEK_OF_MONTH);

int days[][]=new int[weekNumber][7];
int c=daysOfWeekOfFirstDayOfMonth-1;
int r=0;
for(int i=1;i<=numberOfDaysInMonth;i++)
{
days[r][c]=i;
c++;
if(c==7)
{
c=0;
r++;
}
}
return days;
}
public static void main(String gg[])
{
int month=Integer.parseInt(gg[0]);
int year=Integer.parseInt(gg[1]);
DatePicker dp=new DatePicker();
int days[][]=dp.getDays(month,year);
for(int r=0;r<days.length;r++)
{
for(int c=0;c<days[r].length;c++)
{
System.out.printf("%-2d ",days[r][c]);
}
System.out.println();
}
}
}

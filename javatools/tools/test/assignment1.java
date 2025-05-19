//DatePicker simple arragement based on month and year

//java psp 4 2000	Year 2000 and month 4->april has how many days based on leap year [If its february then 29 days]
	// else 28 days

class psp
{
public static void main(String gg[])
{
int day=Integer.parseInt(gg[2]);
int month=Integer.parseInt(gg[0]);
int year=Integer.parseInt(gg[1]);
boolean isLeapYear=false;
if(year%4==0) isLeapYear=true;

//Count days of year
int numberOfDays=-1;
numberOfDays+=(((year-1900)/4)*(366+365+365+365));
int current=366;
for(int i=0;i<(year%4);i++)
{
numberOfDays+=current;
current=365;
}

//Count days of month
//System.out.println("Number of days: "+numberOfDays);
int[] monthDays={31,28,31,30,31,30,31,31,30,31,30,31};
int i=0;
for(i=0;i<month-1;i++)
{
numberOfDays+=monthDays[i];
}
if(i>=2 && isLeapYear) numberOfDays++;
System.out.println("Number of days: "+numberOfDays);
int currentMonthDays=monthDays[month-1];
if(month==2 && isLeapYear) currentMonthDays++;

/*
numberOfDays+=day;
System.out.println("Number of days: "+numberOfDays);

String[] days={"Sunday","Monday","Tuesday","Wednesday","Thusday","Friday","Saturday"};
System.out.println(days[numberOfDays%7]);
*/
int si=(numberOfDays+1)%7;
int rows=((currentMonthDays+si)/7)+((currentMonthDays+si)%7!=0?1:0);
int columns=7;
System.out.println("Rows: "+rows+"  Columns: "+columns);


Integer[][] dateBlock=new Integer[rows][columns];
current=1;
int j=0;
//System.out.println("Month days: "+currentMonthDays);
for(i=0;i<si;i++) dateBlock[j][i]=0;
for(j=0;j<rows;j++)
{
if(j!=0) i=0;
for(;i<columns;i++)
{
	dateBlock[j][i]=current;
	current++;
	if(current-1>currentMonthDays) break;
}
}
for(;i<columns;i++) dateBlock[4][i]=0;

String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
System.out.println();
System.out.println("Month: "+months[month-1]+", Year: "+year);
for(i=0;i<rows;i++)
{
for(j=0;j<columns;j++) 
{
System.out.printf(" %-2d ",dateBlock[i][j]);
}
System.out.println();
}

}
}

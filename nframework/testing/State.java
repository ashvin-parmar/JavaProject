public class State implements java.io.Serializable
{
private COUNTRY country;
private String state;
public State(COUNTRY country,String state)
{
this.country=country;
this.state=state;
}
public void setCountry(COUNTRY country)
{
this.country=country;
}
public void setCountry(String country)
{
this.country=COUNTRY.valueOf(country);
}
public void setState(String state)
{
this.state=state;
}
public String getCountry()
{
return this.country.toString();
}
public String getState()
{
return this.state;
}
}

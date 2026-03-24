import com.ashvin.chat.server.*;
class testingServer
{
  public static void main(String args[])
  {
    WChatServerApplication serverApplication=WChatServerApplication.getServerApplication();
    serverApplication.start();
  }
}

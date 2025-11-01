import com.ashvin.accounting.dbdl.dummy.*;

import java.util.Date;
class psp
{
public static void main(String gg[])
{
DummyCreation dummy=new DummyCreation();
dummy.createDummyPurchases2100(new Date());
dummy.createDummyPayments100(new Date());
}
}

//Add all data from single example

import com.ashvin.accounting.dbdl.dummy.*;
import java.util.*;

class psp
{
public static void main(String gg[])
{
DummyCreation dummy=new DummyCreation();
dummy.createDummyCustomers100();
dummy.createDummyItems400();
dummy.createDummySuppliers100();
dummy.createDummySales3000(new Date());
dummy.createDummyPurchases2100(new Date());
dummy.createDummyReceipts100(new Date());
dummy.createDummyPayments100(new Date());

}
}

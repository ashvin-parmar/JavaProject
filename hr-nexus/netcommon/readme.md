			#HR Network Application

Most Important: We are not changing a single line of data layer, business layer and presentation layer. We are creating Network application seperately and using the same layers for all work.

Now, Moving forward to move this application to network level. Here, We are going to create a server, client seperate ends.
To manage the server using data layer and business layer. To manage the client using presentation layer copies on easy machine.

		## proxybl
1: We have to create seperate proxybl in client side, which has same structure just like business layer. All package structures are same. 
2: It will store no data structures to store data.
3: All the definations of functions are changes and replaces with **code to send request to server**. 
4: populateDS() method will remove.
5: Same pojo classes structures, same manager package structures.
6: All 4 interfaces, pojo, managers, and exceptions.

# Network Programming [Seperately Generalised]
## netcommon
Here, All common classes that are necessary to communicate between server and client. Request, Response and NetworkException classes are stored here.

### Request
Here, Three properties until now, Serializable 
1: String mangers -> Used for determine type of class
2: String action -> Used to method name
3: Object[] arguments --> Arguments required in the function --> ... (varargs)

### Response
Here, Three properties until now, Serializable
1: boolean Success --> Used for checking process is completed or not
2: Object result --> Result exchanging as output
3: Object exception --> Used specific mistakes alert.

### NetworkException
Here, Exception message with checked exception with extended with Exception class 

## netclient
Here, we are seperately client side networking send conditions to connect with business layer. Response send(Request) , used to communicate between client with server.

## netserver
//Not yet discovered

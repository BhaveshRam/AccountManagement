# AccountManagement
\
\
In this I have used Java swing to create frontend and backend part and I have used Mysql as a database.\
I have created 5 files to seperate the frames from each other.\
\
We need to run LoginPage.java first and can move on to other files from it.\
\
In Login Page we have User Email and Password fields and a login button and a reset button.\
\
After entering Login information clicking login button we move to next frame i.e., UserInfo page\
but clicking reset button clears out the information present in the textfields.\
\
In Userinfo page we have user information along with a button to display bond information and a field to transfer money \
and free amount request for first time login user to request for a amount of $100\
\
To create a new bond clicking the same bond information moves to new bond page from where we can create a new bond and \
after creating a new bond we can view bond information.\
\
Bond information is present in Bonding page.\
\
To run the above program we require a database named login and \
three tables:\
\
LOGININFO Table:\
Attributes:\
\
ID (INT): a unique ID given to every user at time of registration\
Name (varchar): Name of the user.\
Email (varchar): Email ID of the user\
Password (varchar): Password of the user\
\
UsrInf Table:\
Attributes:\
\
ID (INT): Specifying row specified to that ID\
AccBal (Float): Account Balance of the user\
BondSt (Boolean): Status of bond whether bond is created or not\
FamReq (Boolean): Free Amount Request status whether claimed or not\
\
Bond Table:\
Attributes:\
\
ID (INT): Specifying row specified to that ID\
BondAm (Float): Bond Amount\
BondDur (int): Duration of bond in years\
CurTm (int): Current time of bond creation for calculation purposes\
CurRwds (float): Current Rewards of the user

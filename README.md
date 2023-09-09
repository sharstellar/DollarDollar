# My Personal Project

## Proposal  

*DollarDollar is an application designed for tracking earnings and expenses.*

Do you struggle with saving money? Or maybe have trouble with adding up your finances? You should try this application
because with **DollarDollar, bookkeeping has never been easier!**

Users can: 
- record income and expenses
- categorize transactions 

I designed this application in hopes to improve my personal finances.
Paying has never been easier with cell phones, but at the same time, monitoring my spending has never been harder.
Often times, I find myself completely unaware of where all my money went. At the end of the day, 
I just want an application that can help me keep track of how much money I have and what I am spending my money on. 

## User Stories

- As a user, I want to be add or delete transactions
- As a user, I want to be able to categorize my transactions as income or expenses
- As a user, I want to be able to see a list of all of my transactions
- As a user, I want to be able to have the choice of loading my transaction list from file when I start application
- As a user, I want to be able to have the option of saving my transaction list when I quit the application 

## Instructions for Grader
- You can generate the first required event relating to adding transactions to account through the "Add a Transaction" 
button and entering information following prompt.
- You can generate the second required event relating to deleting transactions from account through the 
"Remove a Transaction" button and entering information following prompt.
- You can locate my visual component within a pop-up that can be shown through the "Quit" menu at the top of menu bar.
- You can save the state of my application by pressing the "Save Transactions" button.
- You can reload the state of my application by pressing the "Load Previous Transactions" button.

## Phase 4 : Task 2
Here is a representative sample of events that occur when DollarDollar runs:

Fri Dec 02 00:01:00 PST 2022 : Added Transaction: INCOME $100.0

Fri Dec 02 00:01:07 PST 2022 : Added Transaction: INCOME $500.0

Fri Dec 02 00:01:13 PST 2022 : Added Transaction: EXPENSE $200.0

Fri Dec 02 00:01:16 PST 2022 : Saved current transaction list

## Phase 4 : Task 3
Reflection: 
- The current design of the Account class includes functions to calculate a balance and functions for a list of 
transaction. To follow the Single Responsibility Principle for strictly, I would create two more classes (one for balance
and one for a list of transactions) and then put them together in the Account class. 
- There is code duplication throughout the program, notably under the Account class 
(addTransaction() & removeTransaction()) and in AppGui (for pop-up window display). To reduce this, I would create a new 
method that I could call within the old methods in the future.



## Citation 
The Json methods and tests are modeled from 
[JsonSerializationDemo - WorkRoomAPP.java](https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

Other individual references are stated directly above relating methods as comments.

Visual component is from [here](data:image/png;base64,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)



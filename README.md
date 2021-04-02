# PC Demo 

## Project Description

The project's intent is to model a **personal computer** (PC) and its application functions.

A. The PC will allow you to:
- access and use an SMS app
- access and play a game app

B. This PC model is available for study and usage.

C. The project serves as an incentive to apply *concepts* to create a useful **application**.

## User Stories

In the context of a PC model:

- As a user, I want to be able to send a message in my SMS.
- As a user, I want to be able to delete a message in my SMS.
- As a user, I want to be able to view my sent messages in my SMS.
- As a user, I want to be able to view the number of sent messages in my SMS.
- As a user, I want to be able to earn treasure coins in my game.
- As a user, I want to be able to use treasure coins in my game.
- As a user, I want to be able to save my messages in my SMS.
- As a user, I want to be able to load my messages in my SMS.
- As a user, I want to be prompted to save when leaving my SMS.

## Phase 4: Task 2

I have chosen to test and design the SMS class such that it is robust:
 
- SMS's method with the signature sendMessage(String message) will throw a newly-designed checked exception called 
EmptyMessageException when a Message with empty contents is trying to be sent.
- SMSTest accounts for this new design change and tests to check for the correct behaviour: 
catching EmptyMessageException when attempting to send an empty message (expected), and not catching it when sending a 
non-empty message (unexpected).
- Other subsequent uses of the sendMessage method surround it with try/catch statements to ensure correct behaviour or
save us from crashing when trying to send an empty Message in JsonWriterSMSTest, JsonReaderSMS, PC, and InternalFrame.


##Phase 4: Task 3

If I had more time to work on the project, I would refactor the design by:
- introducing a type hierarchy for the persistence package
- creating a supertype called JsonWriter that JsonWriterGame and JsonWriterSMS extend
- creating a supertype called JsonReader that JsonReaderGame and JsonReaderSMS extend

The resulting design would have decreased duplication of code, decreased semantic coupling, and more presence of the
Single Point of Control principle. 

The UML class diagram would also be much cleaner as InternalFrame and PC would only have to have fields of JsonWriter 
and JsonReader only.
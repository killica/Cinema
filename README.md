# Cinema
Application for ticket reservation and cinema management.\
High school graduation project.
# Used technologies
The entire application has been written in Java/JavaFX in NetBeans IDE 8.2.\
The tool used for creating, maintaining and designing databases compatible with SQLite standards is DB Browser.
# Application walk-through
After the initial loading screen, registration window appears. It is possible to register as a user or as an administrator (with necessary credentials), or to create a new account.\
![load](https://github.com/user-attachments/assets/63c1a839-2403-4b5d-ac7f-7d4da04a6e70)
![login](https://github.com/user-attachments/assets/2241a21f-7123-4415-91d7-e1cc2d672b11)

## Creating new account
It is necessary to fill all the fields in the form. Sensitive data is encrypted using SHA-256 algorithm and stored in database.

![registration](https://github.com/user-attachments/assets/b4310c51-2942-4525-8fe0-bc037ff001dc)

## Administrator interface
In order to log in as an administrator, it is necessary to enter the security PIN. After successful verification, the interface for administrators appears.

![admin](https://github.com/user-attachments/assets/15c2e77c-526c-4865-8b99-24ca3da05f30)

### Overview and manipulation of data from database
Upon clicking on the button "Manage databases", the following window appears, which is essential for the control of data in database:

![databases](https://github.com/user-attachments/assets/e239c776-7147-41c0-82f3-d371270086f7)

Buttons for deleting and updating data work similarly for each table, so their functionality will be demonstrated on the Films database. In order to update data, it is necessary to enter the ID of the row that contains it. Afterwards, all the other fields are automatically completed by existing data, which can be easily changed.

![update](https://github.com/user-attachments/assets/23092569-cbeb-429e-b6f9-39ca08ee6176)

### Adding new films

Upon clicking on the button "Add a new film", the following form for inserting a new film in the database appears.

![add](https://github.com/user-attachments/assets/7f1a2314-2721-4f4f-97ca-0beff065f440)

Aside from general information about the film, it is mandatory to specify its projection schedule. This button ("Specify projection times") also enables administrators to add new projections for already existing films. In that case, it is only mandatory to specify the name and duration of the film.

For scheduling a screening, it is necessary to specify the date of the showing, along with the number of screenings on that day. Then, for each screening, it is required to designate the theater where the film is being shown, as well as the start time (the end time is calculated automatically). Entered schedules must not overlap temporally (if they are in the same theater), both with each other and with previously scheduled screenings. The administrator can easily check which termins are available and then choose the most suitable one for the screening. The "Record" button saves the entered data into the database.

![av](https://github.com/user-attachments/assets/778e1eca-1c10-4596-8fad-949874b357d3)

### Adding new food

Upon clicking the "Add food" button, the following window appears.

![food](https://github.com/user-attachments/assets/42348672-56f6-4430-a33f-a0b8be87094c)

Similar to adding a new movie, for food it is necessary to enter all the requested information. Saving the data is done in the same way as with movies.

## User interface

To log in to your account, you need to enter your username and password. If you've forgotten your password, you can set a new one.

### Forgotten password

In order to set the new password, you need to enter your email address and the answer to the security question you set during registration. After that, you can set your new password.

![forgotten](https://github.com/user-attachments/assets/3dff0c4e-b044-4044-b485-ddba8ceee3e7)

### Login and film list overview

After the successful login, the following window appears.

![films](https://github.com/user-attachments/assets/96f8adb3-d672-4cb8-8eda-91785eaa824a)

Movies are divided into 5 genres. Users can choose from available movies within their preferred genre, and by clicking on a movie, a window opens with basic information about the film. In addition to a brief description of the movie, users can also watch its trailer. If they are interested in the movie, they can check the showtimes and then proceed to reserve tickets for their chosen showtime.

![details](https://github.com/user-attachments/assets/d1bda877-f57e-493b-a1b7-488067effbb7)

By clicking the "Book now!" button, a new window opens where reservation creation begins. First, you need to select one of the upcoming projections.

![projections](https://github.com/user-attachments/assets/451d3c85-cc68-4f33-a58d-0364d7da7673)


After selecting designated projection, a new window opens summarizing the chosen screening details along with ticket prices for children and adults. It is necessary to specify the number of tickets for both categories. Clicking the "Next" button proceeds to the next reservation step.

![booking](https://github.com/user-attachments/assets/9b718b54-07f4-404a-a329-8f6bcbbb5bb3)

In the next step, the user decides whether they want to order food. If the answer is yes, a menu with food options is opened. If not, they proceed immediately to the next step of the reservation process.

![foodopt](https://github.com/user-attachments/assets/c7c51413-7e68-4312-93a4-4dc3f15157c6)

### Food and drinks ordering

If the user decides to order food, the window below will appear. There are different categories of food to choose from. Once the user decides which food they want to order, they need to specify the quantity and then add it to the cart. At any time, the user can view their current bill.

![food](https://github.com/user-attachments/assets/b03765a6-3ad1-4b6c-a7b6-fdfe0f510cbc)

![bill](https://github.com/user-attachments/assets/8951848b-64b1-490e-b6dc-87c6b69d6985)

Once the user finishes selecting their food items, they need to click on "Check out" to review their bill. If everything is correct, they proceed to the next step of making the reservation. However, if there is an error of some kind, the cart is emptied and the user can choose their desired food items again.

### Seats selection and reservation completion

After all necessary preparations are completed, the final step of making the reservation is selecting seats in the cinema hall. Occupied seats are marked in red, while available seats are marked in black. It is necessary to select as many black seats as the number of tickets specified at the beginning of the reservation. Once a sufficient number of seats are selected, a button for completing the reservation appears in the top right corner.

![final](https://github.com/user-attachments/assets/0679d3ce-505d-48ae-a64b-82ba6c4066ce)

### Ticket downloading

After all necessary reservation details are filled out, the final step is to download the ticket. By clicking the "Finish!" button, a popup window opens, notifying the user that the ticket is ready for download.

When the user clicks "Download", they are prompted to specify a folder where they want to save their ticket in PDF format. The document contains all the information about the created reservation. An example of such document is shown below.

![ticket](https://github.com/user-attachments/assets/1c562173-fb44-4ce2-acfd-c75f5d6ab766)

## Editing profile information

Both users and administrators have the option to edit their profiles. Any changes made to the profile information need to be saved.

![profile](https://github.com/user-attachments/assets/7aa9cc37-847a-4eb3-b6aa-952dd409d37a)

## Feedback

As the founder of this application, I value user feedback, so I've enabled an option for users to send emails. Users can use this feature to share their opinions on the cinema's operations and the application's functionality, as well as provide suggestions for improvement.

![email](https://github.com/user-attachments/assets/54f9588d-3ef8-46a5-b5c4-395c9100d099)

# Automatic data updating

If we didn't have this utility program, the databases would quickly become overloaded with outdated data, causing the program to malfunction. Therefore, the purpose of this program is to systematically run every 10 minutes (which can be adjusted as needed) and execute the cleanup of data that is no longer current. This includes movie projection times that have passed and reservations associated with those projections.

To achieve this, Task Scheduler on Windows was used, which executes given tasks. It is necessary to create the new task and specify how often and at what intervals we want this task to repeat. The content of my .bat file is shown below.

![filter](https://github.com/user-attachments/assets/23a1b6a8-3b6f-4845-be2d-657bd60ab6cc)

The program operates on a straightforward principle: when it runs, it examines data from the database and compares the end times of film projections with the current system time (Java system time). If it determines that a projection has ended, the status of the projection or reservation is set to 0 (inactive). If a film has no active projections, its status is also set to 0. When a new projection is added, its status is set back to 1.

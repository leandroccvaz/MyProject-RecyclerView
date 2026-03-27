# MyProject-RecyclerView

*Android RecyclerView Sample*<br>
*Overview*

This project is a simple Android application that demonstrates how to display a list of items using RecyclerView. Each item contains a title and a description. When a user taps on an item, a Toast message is displayed with the selected item's title.<br>

*Features*
Display a list of items using RecyclerView<br>
Custom item layout with title and description<br>
Click listener for each item<br>
Toast message on item click <br><br>
*Commomn Tech Stack*
<br>
Kotlin<br>
Android SDK<br>
RecyclerView(Compose)<br>

<br>    
*How It Works*
<br>
A list of sample data is created using a data class (Item)<br>
The RecyclerView is initialized in MainActivity<br>
A custom ItemAdapter binds the data to the UI<br>
Each item handles click events and displays a Toast with the item's title<br>
<br>
*How to Run*
<br>
*Clone the repository:* https://github.com/leandroccvaz/MyProject-RecyclerView.git<br>
git clone <br>
Open the project in Android Studio<br>
Sync Gradle<br>
Run the app on an emulator or physical device<br><br>

<br>
*Possible Improvements*
<br>
DI
Implement MVVM/MVI architecture + Clean Arch,<br>
Add Unit/Instrumented tests,<br>
Replace Toast with Snackbar,<br>
etc ...<br>

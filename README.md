# MyProject-RecyclerView

*Android RecyclerView Sample*<br>
*Overview*

*A modern Android app built with Clean Architecture, MVVM, and best practices*

This project is a simple Android application that demonstrates how to display a list of items using RecyclerView. Each item contains a title and a description. When a user taps on an item, a Toast message is displayed with the selected item's title.<br>

## Features
- *Material3 Toolbar* with refresh action
- *Reactive data flow* from mock API → Repository → UseCase → ViewModel → UI
- *Sealed UI states* — Loading spinner, Success list, Error message
- *Mock API* via OkHttp interceptor — no backend needed
- *Background sync* with WorkManager (periodic, with retry)
- *All hardcoded values* extracted to AppConstants

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

## How to Run
*Clone the repository:* https://github.com/leandroccvaz/MyProject-RecyclerView.git<br>
git clone <br>
Open the project in Android Studio<br>
Sync Gradle<br>
Run the app on an emulator or physical device<br><br>

## Testing
| Layer | Test File | Cases |
|-------|-----------|:-----:|
| ViewModel | ItemListViewModelTest | 5 |
| UseCase | GetItemsUseCaseTest | 3 |
| Repository | ItemRepositoryImplTest | 4 |
| Mapper | ItemMapperTest | 4 |
| Extensions | StringExtensionsTest | 8 |
| DI | AppModuleTest | 1 |
| Worker | SyncItemsWorkerTest | 4 |
| Scheduler | WorkManagerSchedulerTest | 3 |
| UI | ItemListScreenTest | 5 |
| UI | ItemCardTest | 2 |
| | *Total* | *39* |


## CI/CD
GitHub Actions pipeline (.github/workflows/android-ci.yml) runs on every push/PR to main and develop:

1. *Lint* — static analysis
2. *Unit Tests* — all 32 JVM test cases
3. *Instrumented Tests* — Compose UI tests on emulator
4. *Build* — debug + release APKs uploaded as artifacts
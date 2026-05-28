# TandemCommunity


## Zaid's EXPLAINATION

Thank for considering me for this assignment and interview process. I have Implemented the asked requirementts.

Please note that the App might be not perfect as my aim was to focus on App Architecture, scalability and testability.

I kept these in mind while working on assignment

## Project Goals

Demonstrate Clean Architecture in a real-world Android app

Maintain clear separation of concerns

Enable scalability and ease of testing

Follow modern Android development best practices

Handle both happy paths and failure scenarios

## Architecture Overview

I have implemented Clean Architecture withh MVI design pattern. The folder structure could be different for every project but interactions between different layers stay same.

**Why Clean Architecture?**

UI is independent of data sources

Business logic is framework-independent

Easy to add new features or replace implementations

Testable at every layer

**UI/Presentation Layer contains:**

Composables, ViewModels, Intent, Effects

**Composables**: They are pure UI, having no idea how ViewModels provides them the data. Composables just send signal/event to ViewModel against an action on UI. In response, ViewModel update states and optionally emit Effects.

**ViewModels**: They interact with the data layer via UseCases in Clean Architecture, updates UI and business state. Same like Composables, ViewModels don't know how the data layer implemented and from where data is coming-> Local or Remote.

**Intent** Basically, they are simple event passed to ViewModel to do some act.

**Effects** If UI needs to do some one time actions like showing Snackbar or Dialog, View Models emit Effects

**Key Principles to consider at UI layer**

Single source of truth for UI state, Immutable UI state models, Unidirectional data flow

**Data Layer**

Handles all data sources, Responsible for: API calls, Room database operations, Mapping between DTO <> Entity <> Domain models, Data Sources, 

Remote: Tandem Community API

Local: Room Database (offline & caching support)

Repositories decide where data comes from and how it is combined.

**Domain Layer**:

Contains pure business logic, No Android framework dependencies, 

Includes:

UseCases (one responsibility each)

Domain models

Why this matters?: Domain layer is fully unit-testable, Easy to reuse or move to another platform


**This is how Data is flow in a Clean Architecture Project:**
UI Event
→ ViewModel
→ UseCase
→ Repository
→ Remote / Local Data Source
→ Repository
→ UseCase
→ ViewModel
→ UI State Update

## SOLID Principles

Clean architecture purely follow SOLID principles, that can be seen in my approach for app architecture.


## Testing Strategy

This kind of small app could have many critical tests but I implemented only to test success and failure parts just to demonstrate that I have experience in writing unit tests.


**MVI**
I followed MVI design pattern with Clean Architecture. MVI supports UDF (Uni Directtion FLow) where State travels down to children and events tavel up to parents, they hold the state management. That is State Hoisting in Compose.

## Third-Party Libraries
I used Retrofit for network calls, Mockk for mocking dependencies, Hilt for DI, and Jacoco for code coverage tests.





Let's talk about what I implemented. As I mentioned earlier, the focus was on archiecture and approach.

The App :
-> Shows list of Community Members
-> User can like and unlike the member 

What App missed:
-> I have skipped the loaders and empty state scenarios for sake of assignment project since the purpose was to show scalable and testable architecture
-> dimensions/ dp units are hardcoded, which normally stays in a defined file

I have implemented Code coverage with the help of Jacoco, and Kover could have been another option, but I choosed Jacoco because of stability. The intentionally left alot of stuff in code coverage so that we can see a comprehensive bad report to discuss, if need to. Also, for this small assignment even industry standard of 60-70% coverage is not required, its my personal opinion.



## Running the App

It is simple
Just clone this repository in Android Studio and run on emulator or physical device. However, I have added in .apk file that you can just download and install


Once Again, thank you for this task. If you have any question, you can contact any time

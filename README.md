# HiTechCase

User can sign up with user avatar(taking photo from camera), first name, email, password, web site info

## Overview

This project is built using the MVVM (Model-View-ViewModel) architecture pattern in Android. It leverages ViewBinding to efficiently bind views to their corresponding XML layouts.


## Prerequisites

Before running this project, ensure that you have the following requirements met:

Android Studio: Version [Android Studio Electric Eel | 2022.1.1 Patch 1] or higher.
Android SDK: Minimum SDK version 27 and target SDK version 33.


## Installation

To run this project locally, follow these steps:

1. Clone this repository: `git clone https://github.com/eminesa/HiTechCase.git`.
2. Open Android Studio and select "Open an existing Android Studio project".
3. Navigate to the cloned repository and select the project.
4. Build the project using the Gradle sync button or by selecting "Build > Make Project".
5. Connect an Android device or start an emulator.
6. Run the app by clicking on the "Run" button or by selecting "Run > Run 'app'" in the toolbar.

## Libraries Used

- Navigation Component(https://developer.android.com/guide/navigation/get-started): A navigation graph is a resource file that contains all of the destinations and actions. The graph represents all of our app's navigation paths.
- Lifecycle(livedata-viewmodel): The Lifecycle, LiveData, and ViewModel libraries in Android provide a comprehensive set of tools for building lifecycle-aware applications, enabling seamless management of UI components and data updates.
- Glide (https://github.com/bumptech/glide): Glide is a image loading library
- Camera for taking avatar photo

## Architecture

This project follows the MVVM (Model-View-ViewModel) architecture pattern. Here's a brief overview of each component:

- **Model**: Represents the data and business logic of the application.
- **View**: The UI components that the user interacts with.
- **ViewModel**: Acts as an intermediary between the View and the Model, providing data and handling user interactions.


## Contributing

Contributions to this project are welcome. To contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature/bug fix: `git checkout -b feature-name`.
3. Make your changes and commit them: `git commit -m 'Add some feature'`.
4. Push the changes to your forked repository: `git push origin feature-name`.
5. Create a pull request in the original repository.

## Contact

If you have any questions or suggestions regarding this project, feel free to reach out to the project maintainer:

- Name: Emine Sa
- Email: eminesa34@gmail.com

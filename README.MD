# MamMates - Seller - v1

Welcome to MamMates, a platform that enables food sellers to market and sell surplus food that is still fit for consumption. With MamMates, we can collectively reduce food waste and support efforts to create a more sustainable environment.

## Installing the Kotlin Android Project

1. **System Requirements:**
    - Ensure you have installed Android Studio.
    - Make sure the latest Android SDK is downloaded via Android Studio.
    - Ensure Kotlin/Native software is installed.

2. **Installation Steps:**

    - Step 1: Clone the repository from GitHub.
      ```
      git clone https://github.com/MamMates/MamMates-Seller-v1.git
      ```

    - Step 2: Open the project using Android Studio.
        - Open Android Studio.
        - Select 'Open an existing Android Studio project'.
        - Navigate to the directory of the downloaded project.

    - Step 3: Synchronize and install dependencies.
        - Wait for Android Studio to complete project synchronization.
        - Ensure all dependencies are installed by running `Sync Project with Gradle Files`.

    - Step 4: Ready for development!
        - The project is now ready to be compiled and run on your device or emulator.

## Folder Structure Division

The project's folder structure is using Clean Architecture MVVM Pattern which divided into distinct sections to separate their respective roles:

1. **Data:**
    - Location to access data sources such as local storage, network, or other data providers.
    - Example: `data/local`, `data/remote`.

2. **Domain:**
    - Contains business logic related to task management.
    - This is where business rules concerning tasks and related logic are defined.
    - Example: `domain/model`, `domain/repository`.

3. **Presentation:**
    - Used for the user interface.
    - Activities, fragments, layouts, and UI-related classes are placed here.
    - Example: `presentation/screens`, `presentation/adapters`.

Ensure compliance with this folder structure to facilitate development, maintenance, and team collaboration.

## License
This project is licensed under the Bangkit Academy 2023 batch 2

Thank you for joining and supporting MamMates! Let's work together to create a more sustainable world. 🌍✨
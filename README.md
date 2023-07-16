# Flickr Photo Viewer

This is a simple Android application that allows users to search for public photos using tags.

The project is built using Kotlin and follows the principles of clean architecture. It utilizes Jetpack Compose for the UI, coroutines for asynchronous programming and Hilt for dependency injection. The project is organized into different modules to separate concerns and promote modularization.

## Project Structure

The project is divided into several modules:

- **:core:network**: This module handles the networking logic and provides the necessary APIs to interact with the API.

- **:core:compose**: This module contains common Jetpack Compose components such as themes, colors, and other shared UI components.

- **:domain**: The domain module contains the repository interfaces that define the data operations for retrieving photos. It serves as an abstraction layer between the data layer and the UI layer.

- **:feature:search**: This module implements the search functionality, allowing users to enter tags to search for specific photos. It includes the UI components and logic related to searching.

- **:feature:gallery**: This module represents the main screen of the app, displaying a list of photos. It handles the UI components and logic for displaying the photos in a list or grid view.

- **:feature:photo-view**: This module provides the fullscreen view for displaying the tapped image in its original size.

## Prerequisites

To build and run the application, make sure you have the following:

- Android Studio Hedgehog or later

## Getting Started

To run the application on your local machine, follow these steps:

1. Clone the repository to your local machine.

2. Open the project in Android Studio.

3. Build the project to download the necessary dependencies.

4. Run the application on an emulator or a physical device.

## Screenshots
<img width="386" alt="image" src="https://github.com/rezaiyan/PhotoGallery/assets/16861750/f68fa0ed-14ac-478c-a6a8-e26e6709bce7">

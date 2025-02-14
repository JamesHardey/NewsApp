
---

# News App

This is a News App built using Jetpack Compose for Desktop. It features a simple yet elegant UI, allowing users to browse news articles by categories and view detailed information for each article.

## Features

- **Category Selection**: Users can select from various news categories such as Top Headlines, Business, Technology, Entertainment, Sports, Science, and Health.
- **Article Viewing**: Browse articles in the selected category.
- **Detailed Article View**: View detailed information about each article including title, description, and content.
- **Image Caching**: Efficient image loading and caching to improve performance.

## Installation

1. **Clone the Repository**
   ```sh
   git clone https://github.com/JamesHardey/NewsApp.git
   cd NewsApp
   ```

2. **Open the Project in IntelliJ IDEA**
    - Make sure you have the latest version of IntelliJ IDEA installed.
    - Open the `NewsApp` directory as a project.

3. **Setup NewsAPI**
    - Obtain an API key from [NewsAPI](https://newsapi.org/).
    - Add your API key to the project. You can store it in a properties file or directly in the code (not recommended for production).

4. **Run the Application**
    - Open the `Main.kt` file.
    - Click on the green Run icon in the gutter to start the application.

## Usage

- **Main Screen**
    - The main screen displays a list of news articles in the selected category.
    - Click on the hamburger menu icon to open the category drawer.
    - Select a category to load the relevant news articles.

- **Detail Screen**
    - Click on any article in the main screen to view its details.
    - The detail screen shows the article's image, title, published date, and full content.
    - Click the back arrow to return to the main screen.

## Code Overview

- **Main.kt**: Entry point of the application. Sets up the main window and initializes the `App` composable.
- **App.kt**: Contains the main composable function `App`, which handles screen navigation and state management.
- **MainScreen.kt**: Contains the `MainScreen` composable, which displays the list of articles and handles category selection.
- **DetailScreen.kt**: Contains the `DetailScreen` composable, which displays detailed information about a selected article.
- **ArticleItem.kt**: Contains the `ArticleItem` composable, which represents a single article in the list.
- **CachedAsyncImage.kt**: Utility for loading and caching images asynchronously.
- **ShimmerAnimation.kt**: Provides a shimmer effect for loading states.
- **Category.kt**: Data class representing a news category.
- **NewsApi.kt**: Utility class for interacting with the NewsAPI to fetch news articles.

## Dependencies

- Jetpack Compose for Desktop
- Kotlin Coroutines for asynchronous operations
- [NewsAPI](https://newsapi.org/) for fetching news articles


## Screenshots

![NewsApp Screenshot4](https://github.com/user-attachments/assets/cd72131e-94f9-4b48-b1e2-d4cff6203ff8)
Screenshot 1
<br>
<br>
![NewsApp Screenshot](https://github.com/user-attachments/assets/2dc2131f-b4c6-4182-a4ad-49cba9e1c879)
Screenshot 2
<br>
<br>
![NewsApp Screenshot2](https://github.com/user-attachments/assets/1e48ebb5-34c7-4f25-9382-9d19608020f1)
Screenshot 3
<br>
<br>
![NewsApp Screenshot3](https://github.com/user-attachments/assets/b60ea7c1-d73e-4469-8dc1-f2480e30dd55)

## License

This project is licensed under the MIT License.

---

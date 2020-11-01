# NoD
**NoD - New on Demand** is an application that uses the [News API](https://newsapi.org/docs/endpoints/top-headlines) for fetching 
latest news based on the country code, and stores the retrieved data in the local cache. The user can then read the 
cached news at any point of time.

Both the news list and the news content web pages are accessible offline (provided article details and the web pages 
are fetched at least once online). 

### Project Details:
 * The project setup uses Model-View-ViewModel architecture. 
 * [Dagger](https://google.github.io/dagger/) is used for injecting DataSources into the desired Repository classes.
 * [RxJava](https://github.com/ReactiveX/RxJava) is used for making API calls to fetch the News API data.
 * [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) are used for handing the local cache C.R.U.D. operations.
 * [Glide](https://github.com/bumptech/glide) library is used for caching of images.
 * Application makes use of [Android Architecture Components](https://developer.android.com/topic/libraries/architecture).
 * Application uses [Material Design Components](https://developer.android.com/guide/topics/ui/look-and-feel).
 * Instrumentation Tests are provided for testing the functionality of the local caching mechanism.
 * Unit Tests are provided to test the utility methods.
 
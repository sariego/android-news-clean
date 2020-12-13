# Android News
Clean architecture sample in android.  
The app is a simple news articles adapter using data from a external api

The user can:  
(a) view article in browser  
(b) fetch more articles by pull-to-refresh  
(c) delete unwanted articles by swiping

### Architecture
The application consists of 4 layers:  

Layer | Contains
--- | ---
Presentation | User interface, presentation logic and navigation
Domain | Entities, interactors and repository definitions
Data | Repository implementations and data source definitions
Framework | Data source impleentations, dependency injection, others  

![android architecture](static/android_arch.png)

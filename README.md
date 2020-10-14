# reign-hiring-test
My application for the hiring test at Reign

Hey! 👋
Since the instructions and emails were in english i'm writing this in english too!

You can download a debug apk from the releases page here or you can build the app using the stable version of Android Studio,
no special configurations needed.

The app is a simple adapter of articles using one activity for UI logic and one viewmodel for business and domain logic.
I used 2 non-jetpack third party libraries, one to draw the DELETE label when swiping to delete articles and another to "pretty-format" the timestamps ("3 minutes ago", etc)

### some issues
- Early in development I decided not to use any kind of complex network setup and just use Volley, but i ended regretting it since I had some issues with redirections
and had to modify slightly the url used to fetch data (I used https instead of http)
- I also used CustomTabs to launch the story urls because the vanilla webview had issues displaying some of the articles.
- This maybe is not an issue but i used the story_id of the articles as the primary key in my local db to differentiate articles, this was not specified in the instructions.


Thats's it, thank you for your time and your interest.
✌️✌️

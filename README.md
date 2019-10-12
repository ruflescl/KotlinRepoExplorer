# KotlinRepoExplorer

## What is it?

It's a simple Android app that fetches all Kotlin-based repositories from GitHub, ordered by their stargazer count.

## How it works?

It has two possible user actions:
1)  Infinite scrolling (paging): The user can scroll down to fetch more data as much as he/she likes (until GitHub runs out of data, of course);
2)  Swipe down to reload: The user can swipe down to re-fetch all data from the very beginning.

And it has a few other quirks:
1)  Empty state: If no data could be fetched (either from an error or first fetch with no network connection), an empty state view will appear telling what the user could do;
2)  Internal data and image caching: Data will be stored for two minutes, before it needs to be fetched again from the network;
3)  No data loss on screen rotation.

## What is it made of?
 - Language: Kotlin
 - Architecture: MVVM
 - Android Architecture Components (Data Binding, LiveData, Room)
 - Retrofit
 - Kotlin Coroutines
 - Espresso
 - MockK
 
## What it lacks?
 - More unit tests (Right now, it has one Espresso-based instrumented test for its only Activity and one MockK unit test for its only ViewModel)
 - I could not import EspressoIdlingResources for better test timing, so I'm using `Thread.sleep` for now :(
 - A better use of Room's capabilities
 - And maybe some nice layout updates
 
And that's it for now! I hope you like it! :D

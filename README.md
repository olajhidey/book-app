# book-app

A mobile application that depicts the use of Async Task in android. In this project we decided to query the Google books api
to get result of author of a particular book based on the name of the book provided by the user.

## Get started 

1. Download zip file or <code>git clone https://github.com/olajhidey/book-app.git/</code>
2. Navigate to folder and then open with Android studio
3. Run application.

## Overview
The mobile application consist of an editText that collects the information concerning the book (Book name) then we take the 
query name and pass to our Google Books api which then provide us with possible authors of the book. But as at this time we decided to only
show the first result from our response code.

The use of AsyncTask was very vital. Has it was used to load our <code>NetworkUtils</code> class which contains the operations to load the data from the internet
it loaded it using the loadInBackground and the onPostExecute to get the result from the doInBackground method. Then appended the result to the 
textview in the scrollview. See screenshot below

## Screenshot
<img src="https://user-images.githubusercontent.com/18614379/57959495-06a3ca80-78fc-11e9-87b7-adaea3e1b2ed.png" width="300px" />  <img src="https://user-images.githubusercontent.com/18614379/57959520-24712f80-78fc-11e9-853f-555146d32cab.png" width="300px" />

## Reference
<ul>
<li><a href="https://developer.android.com/training/basics/network-ops/connecting.html" target="_blank">Connect to the network</a></li>
<li><a href="https://developer.android.com/training/basics/network-ops/managing.html" target="_blank">Manage network usage</a></li>
<li><a href="https://developer.android.com/guide/components/loaders.html" target="_blank">Loaders</a></li>
<li><a href="http://developer.android.com/reference/android/os/AsyncTask.html" target="_blank"><code>AsyncTask</code></a></li>
<li><a href="https://developer.android.com/reference/android/content/AsyncTaskLoader.html" target="_blank"><code>AsyncTaskLoader</code></a></li>
</ul>

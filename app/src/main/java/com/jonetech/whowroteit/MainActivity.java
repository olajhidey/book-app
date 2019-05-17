package com.jonetech.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private EditText textInput;
    private TextView authorText;
    private TextView bookTitle;

    // Weak References to be used in the Async Task subclass

    private WeakReference<TextView> returnAuthorText;
    private WeakReference<TextView> returnBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.bookInput);
        authorText = findViewById(R.id.authorText);
        bookTitle = findViewById(R.id.titleText);
    }

    /**
     * Method used to search the api for available books provided by the user from the input
     * or edit text
     *
     * @param view
     */

    public void searchBooks(View view) {

        // Get the text string from the editText
        String queryText = textInput.getText().toString();


        // Get the keyboard running on the app
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Check if its not null
        if (inputManager != null) {

            // Hide the keyboard on the click of the button
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the phone connection state
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get the network info of the phone
        NetworkInfo mNetworkInfo = null;

        // if the phone connection is not null
        if (connManager != null) {

            // Get the active connection info of mobile app
            mNetworkInfo = connManager.getActiveNetworkInfo();
        }

        // if the network info is not null
        // and the the phone is connected to the internet
        // and the text in the input is not null

        if (mNetworkInfo != null && mNetworkInfo.isConnected() && queryText.length() != 0) {

            // perform a query on the api and fetch our result

            new FetchBook(authorText, bookTitle).execute(queryText);

            authorText.setText("");

            // Show that the app is loading in the background for results
            bookTitle.setText(R.string.loading);

        } else {


            // if the input text is 0
            if (queryText.length() == 0) {
                authorText.setText("");

                // notify the user that the EditText can't be empty
                bookTitle.setText(R.string.no_search_term);
            } else {
                authorText.setText("");

                // notify the user that there is not network available on the app
                bookTitle.setText(R.string.no_network);
            }
        }


    }

    public class FetchBook extends AsyncTask<String, Void, String> {

        FetchBook(TextView bookAuthor, TextView bookTitle) {
            returnAuthorText = new WeakReference<>(bookAuthor);
            returnBookTitle = new WeakReference<>(bookTitle);
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getBookInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject mJSONObject = new JSONObject(s);
                JSONArray itemsArray = mJSONObject.getJSONArray("items");

                int i = 0;
                String author = null;
                String title = null;

                while (i < itemsArray.length() && (author == null && title == null)) {

                    // Get the current item information
                    JSONObject book = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    // Try to get the author  and title from the current view
                    // catch if any field is empty or move on


                    try {
                        title = volumeInfo.getString("title");
                        author = volumeInfo.getString("authors");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    i++;

                }

                if (title != null && author != null) {
                    System.out.println(title);
                    System.out.println(author);
                    returnBookTitle.get().setText(title);
                    returnAuthorText.get().setText(author);
                } else {
                    returnBookTitle.get().setText(getString(R.string.no_result));
                    returnAuthorText.get().setText("");
                }

            } catch (JSONException json) {

                // If onPostExecute does not receive a proper JSON string,
                // update the UI to show failed results.
                returnBookTitle.get().setText(R.string.no_result);
                returnAuthorText.get().setText("");
                json.printStackTrace();
            }
        }
    }
}

package com.jonetech.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String TAG_LOG = NetworkUtils.class.getSimpleName();

    // BASE URL FOR THE API
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

    // PARAMETER FOR THE SEARCH STRING
    private static final String QUERY_PARAMS = "q";

    // PARAMETER THAT LIMIT SEARCH RESULTS
    private static final String MAX_RESULT = "maxResults";

    // PARAMETER TO FILTER BY PRINT TYPE
    private static final String PRINT_TYPE = "printType";


    /**
     * This method takes the query string from the UI and then sends
     * it to the api to return our book info
     *
     * @param query This query the api for the book information that is been requested
     * @return
     */
    static String getBookInfo(String query) {

        // The following local variables would be used for connecting to the
        // internet
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String bookJsonString = null;

        try {

            Uri builtUri = Uri.parse(BASE_URL).buildUpon().
                    appendQueryParameter(QUERY_PARAMS, query).
                    appendQueryParameter(MAX_RESULT, "10").
                    appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestUrl = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the inputStream
            InputStream mInputStream = urlConnection.getInputStream();

            // Create a buffered reader froom the inputstream
            bufferedReader = new BufferedReader(new InputStreamReader(mInputStream));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufferedReader.readLine()) != null){

                builder.append(line);

                builder.append("\n");
            }

            if(builder.length() == 0){
                return null;
            }

            bookJsonString = builder.toString();

        } catch (IOException io) {
            io.printStackTrace();
        } finally {

            if(urlConnection != null){
                urlConnection.disconnect();

            }

            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException io){
                    io.printStackTrace();
                }
            }

        }

        Log.d(TAG_LOG, bookJsonString);

        return bookJsonString;

    }
}

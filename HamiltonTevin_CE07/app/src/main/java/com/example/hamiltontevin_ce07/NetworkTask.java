package com.example.hamiltontevin_ce07;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class NetworkTask extends AsyncTask<String,String,ArrayList<Books>> {

    final private OnFinished mOnFinishedInterface;

    //C-tor
    public NetworkTask(OnFinished mOnFinishedInterface) {
        this.mOnFinishedInterface = mOnFinishedInterface;
    }

    //interface
    interface OnFinished{
        void onPre();
        void onPost(ArrayList list);
    }

    @Override
    protected void onPreExecute() {
        mOnFinishedInterface.onPre();
    }

    @Override
    protected ArrayList<Books> doInBackground(String... strings) {

        if(strings == null || strings.length <= 0 || strings[0].trim().isEmpty()){
            return null;
        }
            String jsonString = null;
            // HTTP URL connection reference.
            HttpURLConnection connection = null;

            // URL reference
            URL url;

            try {
                // Create new URL
                url = new URL(strings[0]);
                // Open connection
                connection = (HttpURLConnection)url.openConnection();
                // Perform connection operation
                connection.connect();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            InputStream is = null;

            try {
                is = connection.getInputStream();
                jsonString = IOUtils.toString(is,"UTF-8");
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                // If we have a stream, try to close it.

                if(connection != null){
                    if (is  != null) {
                        try{
                            is.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        connection.disconnect();
                    }
                }
            }
            return convertJsonObjToBooksObj(jsonString);
        }


    @Override
    protected void onPostExecute(ArrayList<Books> books) {
        mOnFinishedInterface.onPost(books);
    }

    private ArrayList<Books> convertJsonObjToBooksObj(String jsonString)  {

        if(jsonString != null) {
            ArrayList<Books> booksArrayList = new ArrayList<>();
            try {

                JSONObject outerObj = new JSONObject(jsonString);
                JSONArray outerArr = outerObj.getJSONArray("items");
                for (int i = 0; i < outerArr.length(); i++) {
                    JSONObject obj = outerArr.getJSONObject(i);
                    JSONObject innerJsonObj = obj.getJSONObject("volumeInfo");
                    String title = innerJsonObj.getString("title");
                    JSONObject imageJsonObj = innerJsonObj.getJSONObject("imageLinks");
                    String bookImage = imageJsonObj.getString("smallThumbnail");

                    booksArrayList.add(new Books(title,bookImage));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return booksArrayList;
        }
       return null;
    }

}

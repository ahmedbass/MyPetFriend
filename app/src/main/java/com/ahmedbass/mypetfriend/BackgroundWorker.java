package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;
import static com.ahmedbass.mypetfriend.LauncherActivity.PREF_LOGGED_IN;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    private Activity activity;
    private ProgressDialog progressDialog;

    public static String serverDomain = "http://192.168.1.4/"; //192.168.1.4 - 10.22.136.239 - 10.22.139.249
    private String type;
    private String email, password;

    BackgroundWorker(Activity activity){
        this.activity = activity;
    }

    static void setServerDomainUrl(String domainUrl){
        if (domainUrl.startsWith("http://")) {
            serverDomain = domainUrl;
        } else {
            serverDomain = "http://" + domainUrl;
        }
        if (!domainUrl.endsWith("/")) {
            serverDomain += "/";
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        if (type.equals("login")) {
            String login_url = serverDomain + "login.php";
            email = params[1];
            password = params[2];
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(login_url);
                //post data to the server
                String postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                postDataToServer(httpURLConnection, postData);
                //read response back from server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        } else if (type.equals("register")) {
            String register_url = serverDomain + "register.php";
            String createDate = params[1];
            String userType = params[2];
            String firstName = params[3];
            String lastName = params[4];
            String email = params[5];
            String password = params[6];
            String birthDate = params[7];
            String gender = params[8];
            String country = params[9];
            String city = params[10];
            String phone = params[11];
            String profilePhoto = params[12];
            String profileDescription = params[13];
            String availability = params[14];
            String yearsOfExperience = params[15];;
            String averageRatePerHour = params[16];
            String serviceProvidedFor = params[17];
            String serviceProvided = params[18];
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(register_url);
                //post data to the server
                String postData = URLEncoder.encode("createDate", "UTF-8") + "=" + URLEncoder.encode(createDate, "UTF-8") +
                        "&" + URLEncoder.encode("userType", "UTF-8") + "=" + URLEncoder.encode(userType, "UTF-8") +
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                        "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") +
                        "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") +
                        "&" + URLEncoder.encode("birthDate", "UTF-8") + "=" + URLEncoder.encode(birthDate, "UTF-8") +
                        "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") +
                        "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") +
                        "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") +
                        "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") +
                        "&" + URLEncoder.encode("profilePhoto", "UTF-8") + "=" + URLEncoder.encode(profilePhoto, "UTF-8") +
                        "&" + URLEncoder.encode("profileDescription", "UTF-8") + "=" + URLEncoder.encode(profileDescription, "UTF-8") +
                        "&" + URLEncoder.encode("availability", "UTF-8") + "=" + URLEncoder.encode(availability, "UTF-8") +
                        "&" + URLEncoder.encode("yearsOfExperience", "UTF-8") + "=" + URLEncoder.encode(yearsOfExperience, "UTF-8") +
                        "&" + URLEncoder.encode("averageRatePerHour", "UTF-8") + "=" + URLEncoder.encode(averageRatePerHour, "UTF-8") +
                        "&" + URLEncoder.encode("serviceProvidedFor", "UTF-8") + "=" + URLEncoder.encode(serviceProvidedFor, "UTF-8") +
                        "&" + URLEncoder.encode("serviceProvided", "UTF-8") + "=" + URLEncoder.encode(serviceProvided, "UTF-8");
                postDataToServer(httpURLConnection, postData);
                //get response result from the server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        } else if (type.equals("updateUserInfo")) {
            String updateUserInfo_url = serverDomain + "update_user_info.php";
            String userId = params[1];
            String userType = params[2];
            String firstName = params[3];
            String lastName = params[4];
            String email = params[5];
            String password = params[6];
            String birthDate = params[7];
            String gender = params[8];
            String country = params[9];
            String city = params[10];
            String phone = params[11];
            String profilePhoto = params[12];
            String profileDescription = params[13];
            String availability = params[14];
            String yearsOfExperience = params[15];
            String averageRatePerHour = params[16];
            String serviceProvidedFor = params[17];
            String serviceProvided = params[18];
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(updateUserInfo_url);
                //post data to the server
                String postData = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") +
                        "&" + URLEncoder.encode("userType", "UTF-8") + "=" + URLEncoder.encode(userType, "UTF-8") +
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                        "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") +
                        "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") +
                        "&" + URLEncoder.encode("birthDate", "UTF-8") + "=" + URLEncoder.encode(birthDate, "UTF-8") +
                        "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") +
                        "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") +
                        "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") +
                        "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") +
                        "&" + URLEncoder.encode("profilePhoto", "UTF-8") + "=" + URLEncoder.encode(profilePhoto, "UTF-8") +
                        "&" + URLEncoder.encode("profileDescription", "UTF-8") + "=" + URLEncoder.encode(profileDescription, "UTF-8") +
                        "&" + URLEncoder.encode("availability", "UTF-8") + "=" + URLEncoder.encode(availability, "UTF-8") +
                        "&" + URLEncoder.encode("yearsOfExperience", "UTF-8") + "=" + URLEncoder.encode(yearsOfExperience, "UTF-8") +
                        "&" + URLEncoder.encode("averageRatePerHour", "UTF-8") + "=" + URLEncoder.encode(averageRatePerHour, "UTF-8") +
                        "&" + URLEncoder.encode("serviceProvidedFor", "UTF-8") + "=" + URLEncoder.encode(serviceProvidedFor, "UTF-8") +
                        "&" + URLEncoder.encode("serviceProvided", "UTF-8") + "=" + URLEncoder.encode(serviceProvided, "UTF-8");
                postDataToServer(httpURLConnection, postData);
                //get response result from the server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        }  else if (type.equals("addNewAdvert")) {
            String register_url = serverDomain + "add_new_advert.php";
            String sellerId = params[1];
            String createDate = params[2];
            String isSold = params[3];
            String viewCount = params[4];
            String category = params[5];
            String type = params[6];
            String title = params[7];
            String price = params[8];
            String details = params[9];
            String country = params[10];
            String city = params[11];
            String email = params[12];
            String phone = params[13];
            String petType = params[14];
            String petBreed = params[15];;
            String petBirthDate = params[16];
            String petGender = params[17];
            String isPetNeutered = params[18];
            String isPetMicrochipped = params[19];
            String isPetVaccinated = params[20];
            String photo = params[21];
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(register_url);
                //post data to the server
                String postData = URLEncoder.encode("sellerId", "UTF-8") + "=" + URLEncoder.encode(sellerId, "UTF-8") +
                        "&" + URLEncoder.encode("createDate", "UTF-8") + "=" + URLEncoder.encode(createDate, "UTF-8") +
                        "&" + URLEncoder.encode("isSold", "UTF-8") + "=" + URLEncoder.encode(isSold, "UTF-8") +
                        "&" + URLEncoder.encode("viewCount", "UTF-8") + "=" + URLEncoder.encode(viewCount, "UTF-8") +
                        "&" + URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8") +
                        "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") +
                        "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") +
                        "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") +
                        "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(details, "UTF-8") +
                        "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") +
                        "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") +
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") +
                        "&" + URLEncoder.encode("petType", "UTF-8") + "=" + URLEncoder.encode(petType, "UTF-8") +
                        "&" + URLEncoder.encode("petBreed", "UTF-8") + "=" + URLEncoder.encode(petBreed, "UTF-8") +
                        "&" + URLEncoder.encode("petBirthDate", "UTF-8") + "=" + URLEncoder.encode(petBirthDate, "UTF-8") +
                        "&" + URLEncoder.encode("petGender", "UTF-8") + "=" + URLEncoder.encode(petGender, "UTF-8") +
                        "&" + URLEncoder.encode("isPetNeutered", "UTF-8") + "=" + URLEncoder.encode(isPetNeutered, "UTF-8") +
                        "&" + URLEncoder.encode("isPetMicrochipped", "UTF-8") + "=" + URLEncoder.encode(isPetMicrochipped, "UTF-8") +
                        "&" + URLEncoder.encode("isPetVaccinated", "UTF-8") + "=" + URLEncoder.encode(isPetVaccinated, "UTF-8") +
                        "&" + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8");
                postDataToServer(httpURLConnection, postData);
                //get response result from the server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        }

        else if (type.equals("getPetCareProviders")) {
            String getPetCareProviders_url = serverDomain + "get_pet_care_providers.php";
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(getPetCareProviders_url);
                //get response result from the server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }

        } else if (type.equals("getAdverts")) {
            String getAdverts_url = serverDomain + "get_adverts.php";
            try {
                //set the http connection
                HttpURLConnection httpURLConnection = setHttpConnection(getAdverts_url);
                //get response result from the server
                return readServerResponse(httpURLConnection);
            } catch (IOException e) {
                return "Error: Cannot Connect To The Server";
            }
        }

        return null;
    }

    private HttpURLConnection setHttpConnection(String _url) throws IOException{
        URL url = new URL(_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    private void postDataToServer(HttpURLConnection httpURLConnection, String postData) throws IOException {
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(postData);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
    }

    private String readServerResponse(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        String result = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
    }

    //----------------------------------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(String result) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (type.equals("login")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                PetOwner petOwner = getUserInfoFromLocalDatabase();
                if (petOwner != null) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("userInfo", petOwner);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            } else if (result.trim().equals("Login Failed")) {
                Toast.makeText(activity, "Login Failed: Wrong Username or Password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
                activity.getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).edit().putBoolean(PREF_LOGGED_IN, true).apply();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("userInfo", getUserInfoFromJson(result)); //form PetOwner object from the json result and send it with intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }

        } else if (type.equals("register")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
            } else if (result.trim().equals("This Email Is Already Registered")) {
                Toast.makeText(activity, "This Email Is Already Registered. Please Use Different Email.", Toast.LENGTH_SHORT).show();
            } else if (result.trim().equals("Registration Failed")) {
                Toast.makeText(activity, "Registration Failed: Please Try Again Later", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show();
                activity.getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).edit().putBoolean(PREF_LOGGED_IN, true).apply();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("userInfo", getUserInfoFromJson(result)); //form PetOwner object from the json result and send it with intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }

        } else if (type.equals("updateUserInfo")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
            } else if (result.trim().equals("Update Failed")) {
                Toast.makeText(activity, "Update Failed: Please Try Again Later", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("userInfo", getUserInfoFromJson(result)); //form PetOwner object from the json result and send it with intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }

        }  else if (type.equals("addNewAdvert")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
            } else if (result.trim().equals("Upload Failed")) {
                Toast.makeText(activity, "Advert Publish Failed. Please Try Again Later.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Advert Publish Successful", Toast.LENGTH_SHORT).show();
                saveMyAdvertToLocalDatabase(result);
                activity.setResult(RESULT_OK);
                activity.finish();
            }
        }

        else if (type.equals("getPetCareProviders")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, PetCareServiceActivity.class), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                Intent intent = new Intent(activity, PetCareServiceActivity.class);
                intent.putExtra("petCareProvidersInfo", getPetCareProvidersFromJson(result)); //form PetCareProvider objects from the json result and send it with intent
                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }

        } else if (type.equals("getAdverts")) {
            if (result.trim().equals("Error: Cannot Connect To The Server")) {
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, PetMarketActivity.class), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                Intent intent = new Intent(activity, PetMarketActivity.class);
                intent.putExtra("advertsInfo", getAdvertsFromJson(result)); //form Advert objects from the json result and send it with intent
                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        }
    }

    private PetOwner getUserInfoFromJson(String jsonResult) {
        if (type.equals("login") || type.equals("register") || type.equals("updateUserInfo")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                if(jsonArray.length() > 0) {
                    JSONObject innerJsonObject = jsonArray.getJSONObject(0);

                    //------------------------------------------------------------------------------
                    //insert/update user record in the local database too
                    if (type.equals("register") || type.equals("updateUserInfo")) {
                        MyDBHelper dbHelper = new MyDBHelper(activity);
                        dbHelper.open();
                        String[] columnNames = dbHelper.getColumnNames(MyPetFriendContract.UsersEntry.TABLE_NAME);
                        Object[] columnValues = new Object[]{innerJsonObject.getInt("_id"), innerJsonObject.getLong("createDate"),
                                innerJsonObject.getString("userType"), innerJsonObject.getString("firstName"), innerJsonObject.getString("lastName"),
                                innerJsonObject.getString("email"), innerJsonObject.getString("password"), innerJsonObject.getLong("birthDate"),
                                innerJsonObject.getString("gender"), innerJsonObject.getString("country"), innerJsonObject.getString("city"),
                                innerJsonObject.getString("phone"), innerJsonObject.getString("profilePhoto"),
                                innerJsonObject.getString("profileDescription"), innerJsonObject.getString("availability"),
                                innerJsonObject.getString("yearsOfExperience"), innerJsonObject.getString("averageRatePerHour"),
                                innerJsonObject.getString("serviceProvidedFor"), innerJsonObject.getString("serviceProvided")};
                        if (type.equals("register")) {
                            dbHelper.insetRecord(MyPetFriendContract.UsersEntry.TABLE_NAME, columnNames, columnValues);
                            dbHelper.close();
                        } else if (type.equals("updateUserInfo")) {
                            dbHelper.updateRecord(MyPetFriendContract.UsersEntry.TABLE_NAME, columnNames, columnValues,
                                    MyPetFriendContract.UsersEntry.USER_ID, innerJsonObject.getString("_id"));
                        }
                        dbHelper.close();
                    }
                    //------------------------------------------------------------------------------

                    if (innerJsonObject.getString("userType").equals(MyPetFriendContract.UsersEntry.USER_TYPE_PET_CARE_PROVIDER)) {
                        return new PetCareProvider(innerJsonObject.getInt("_id"), innerJsonObject.getLong("createDate"),
                                innerJsonObject.getString("userType"), innerJsonObject.getString("firstName"), innerJsonObject.getString("lastName"),
                                innerJsonObject.getString("email"), innerJsonObject.getString("password"), innerJsonObject.getLong("birthDate"),
                                innerJsonObject.getString("gender"), innerJsonObject.getString("country"), innerJsonObject.getString("city"),
                                innerJsonObject.getString("phone"), innerJsonObject.getString("profilePhoto"),
                                innerJsonObject.getString("profileDescription"), innerJsonObject.getString("availability"),
                                innerJsonObject.getString("yearsOfExperience"), innerJsonObject.getString("averageRatePerHour"),
                                innerJsonObject.getString("serviceProvidedFor"), innerJsonObject.getString("serviceProvided"),
                                (innerJsonObject.getString("latitude").isEmpty() ? 0 : innerJsonObject.getDouble("latitude")),
                                (innerJsonObject.getString("longitude").isEmpty() ? 0 : innerJsonObject.getDouble("longitude")));
                    } else {
                        //if userType != PetCareProvider, assume it's PetOwner
                        return new PetOwner(innerJsonObject.getInt("_id"), innerJsonObject.getLong("createDate"),
                                innerJsonObject.getString("userType"), innerJsonObject.getString("firstName"), innerJsonObject.getString("lastName"),
                                innerJsonObject.getString("email"), innerJsonObject.getString("password"), innerJsonObject.getLong("birthDate"),
                                innerJsonObject.getString("gender"), innerJsonObject.getString("country"), innerJsonObject.getString("city"),
                                innerJsonObject.getString("phone"), innerJsonObject.getString("profilePhoto"),
                                (innerJsonObject.getString("latitude").isEmpty() ? 0 : innerJsonObject.getDouble("latitude")),
                                (innerJsonObject.getString("longitude").isEmpty() ? 0 : innerJsonObject.getDouble("longitude")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Error: Cannot Get User Information " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    //for if we couldn't get user information online (using above method), try to find it in the local database
    private PetOwner getUserInfoFromLocalDatabase() {
        MyDBHelper dbHelper = new MyDBHelper(activity);
        dbHelper.open();
        Cursor cursor = dbHelper.getRecord(MyPetFriendContract.UsersEntry.TABLE_NAME, null,
                new String[]{MyPetFriendContract.UsersEntry.COLUMN_EMAIL, MyPetFriendContract.UsersEntry.COLUMN_PASSWORD}, new String[]{email, password});
        if (cursor.moveToFirst()) {
            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
            activity.getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).edit().putBoolean(PREF_LOGGED_IN, true).apply();
            if (cursor.getString(2).equals(MyPetFriendContract.UsersEntry.USER_TYPE_PET_CARE_PROVIDER)) {
                PetCareProvider petCareProvider = new PetCareProvider(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getLong(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13),
                        cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), 0, 0);
                cursor.close();
                dbHelper.close();
                return petCareProvider;
            } else {
                //if userType != PetCareProvider, assume it's PetOwner
                PetOwner petOwner = new PetOwner(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getLong(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), 0, 0);
                cursor.close();
                dbHelper.close();
                return petOwner;
            }
        } else {
            Toast.makeText(activity, "Login Failed: Wrong user name or password", Toast.LENGTH_SHORT).show();
            cursor.close();
            dbHelper.close();
            return null;
        }
    }

    private void saveMyAdvertToLocalDatabase(String jsonResult) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            if(jsonArray.length() > 0) {
                JSONObject innerJsonObject = jsonArray.getJSONObject(0);
                Object[] columnValues = new Object[]{innerJsonObject.getLong("_id"), innerJsonObject.getLong("sellerId"),
                        innerJsonObject.getLong("createDate"), innerJsonObject.getInt("isSold"), innerJsonObject.getInt("viewCount"),
                        innerJsonObject.getString("category"), innerJsonObject.getString("type"), innerJsonObject.getString("title"),
                        innerJsonObject.getDouble("price"), innerJsonObject.getString("details"), innerJsonObject.getString("country"),
                        innerJsonObject.getString("city"), innerJsonObject.getString("email"), innerJsonObject.getString("phone"),
                        innerJsonObject.getString("petType"), innerJsonObject.getString("petBreed"), innerJsonObject.getLong("petBirthDate"),
                        innerJsonObject.getString("petGender"), innerJsonObject.getString("isPetNeutered"),
                        innerJsonObject.getString("isPetMicrochipped"), innerJsonObject.getString("isPetVaccinated")};
                MyDBHelper dbHelper = new MyDBHelper(activity);
                dbHelper.open();
                String[] columnNames = dbHelper.getColumnNames(MyPetFriendContract.UserOfferedAdvertsEntry.TABLE_NAME);
                dbHelper.insetRecord(MyPetFriendContract.UserOfferedAdvertsEntry.TABLE_NAME, columnNames, columnValues);
                dbHelper.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error: Cannot Get User Information :" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<PetCareProvider> getPetCareProvidersFromJson(String jsonResult){
        ArrayList<PetCareProvider> petCareProviders = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            jsonArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Error: Data Cannot Be Retrieved", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < (jsonArray != null ? jsonArray.length() : 0); i++) {
            try {
            JSONObject innerJsonObject = jsonArray.getJSONObject(i);
            petCareProviders.add(new PetCareProvider(innerJsonObject.getInt("_id"), innerJsonObject.getLong("createDate"),
                    innerJsonObject.getString("userType"), innerJsonObject.getString("firstName"), innerJsonObject.getString("lastName"),
                    innerJsonObject.getString("email"), innerJsonObject.getString("password"), innerJsonObject.getLong("birthDate"),
                    innerJsonObject.getString("gender"), innerJsonObject.getString("country"), innerJsonObject.getString("city"),
                    innerJsonObject.getString("phone"), innerJsonObject.getString("profilePhoto"),
                    innerJsonObject.getString("profileDescription"), innerJsonObject.getString("availability"),
                    innerJsonObject.getString("yearsOfExperience"), innerJsonObject.getString("averageRatePerHour"),
                    innerJsonObject.getString("serviceProvidedFor"), innerJsonObject.getString("serviceProvided"),
                    innerJsonObject.getDouble("latitude"), innerJsonObject.getDouble("longitude")));
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Error: Some Data Cannot Be Retrieved", Toast.LENGTH_SHORT).show();
            }
        }
        return petCareProviders;
    }

    private ArrayList<Advert> getAdvertsFromJson(String jsonResult) {
        ArrayList<Advert> adverts = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            jsonArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
                e.printStackTrace();
        }
        for (int i = 0; i < (jsonArray != null ? jsonArray.length() : 0); i++) {
            try {
            JSONObject innerJsonObject = jsonArray.getJSONObject(i);
            adverts.add(new Advert(innerJsonObject.getLong("_id"), Long.parseLong(innerJsonObject.getString("sellerId")),
                    innerJsonObject.getLong("createDate"), (innerJsonObject.getString("isSold").equals("1")),
                    innerJsonObject.getInt("viewCount"), innerJsonObject.getString("category"),
                    innerJsonObject.getString("type"), innerJsonObject.getString("title"),
                    Double.parseDouble(innerJsonObject.getString("price")), innerJsonObject.getString("details"),
                    innerJsonObject.getString("country"), innerJsonObject.getString("city"), innerJsonObject.getString("email"),
                    innerJsonObject.getString("phone"), innerJsonObject.getString("petType"),
                    innerJsonObject.getString("petBreed"), innerJsonObject.getLong("petBirthDate"),
                    innerJsonObject.getString("petGender"), (innerJsonObject.getString("isPetNetuered").equals("1")),
                    (innerJsonObject.getString("isPetMicrochipped").equals("1")),
                    (innerJsonObject.getString("isPetVaccinated").equals("1")), innerJsonObject.getString("photo")));
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Error: Some Data Cannot Be Retrieved", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(activity, "Data Conversion Error", Toast.LENGTH_SHORT).show();
            }
        }

        return adverts;
    }
}

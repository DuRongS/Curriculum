package com.example.curriculum;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    //日志消息标签
    public static final String LOG_TAG = CourseActivity.class.getSimpleName();
    //用于获取course数据的URL
    private static String COURSE_REQUEST_URL = "http://192.168.1.5:8080/Web-01/mycourse.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        CourseAsyncTack task = new CourseAsyncTack();
        task.execute();

    }

    private void updateUi(ArrayList<Course> courses) {
        ArrayList<Course> courseArrayList = courses;


        ListView courseListView = (ListView) findViewById(R.id.list);

        CourseAdapter adapter = new CourseAdapter(this, courses);

        courseListView.setAdapter(adapter);
    }

    private class CourseAsyncTack extends AsyncTask<URL, Void, ArrayList<Course>> {

        @Override
        protected ArrayList<Course> doInBackground(URL... urls) {
            URL url = createUrl(COURSE_REQUEST_URL);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Course> courses = extractFeatureFromJson(jsonResponse);
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses) {
            if (courses == null) {
                return;
            }
            updateUi(courses);
        }

        /**
         * 从给定字符串返回新的URL对象
         *
         * @param stringUrl
         * @return
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error with creating URL", e);
                return null;
            }
            return url;
        }

        /**
         * 向给定的URL发出HTTP请求,并返回一个String作为响应
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            //如果url为空,返回jsonResponse
            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                //设置HTTP请求
                urlConnection = (HttpURLConnection) url.openConnection();
                //设置请求方法
                urlConnection.setRequestMethod("GET");
                //设置读取超时
                urlConnection.setReadTimeout(10000 /*milliseconds*/);
                //设置链接超时
                urlConnection.setConnectTimeout(15000 /*milliseconds*/);
                //建立连接
                urlConnection.connect();
                //检查接收的响应码是不是200
                if (urlConnection.getResponseCode() == 200) {
                    //获取输入流
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    //断开连接
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    //断开输入流
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        //将输入流转换为一个字符串,其中包含来自服务器的整个JSON响应
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * 返回通过解析JSON响应而构建的{@link Course}对象的列表。
         */
        public ArrayList<Course> extractFeatureFromJson(String courseJSON) {
            ArrayList<Course> courses = new ArrayList<>();
            try {

                JSONObject baseJsonResponse = new JSONObject(courseJSON);
                JSONArray courseArray = baseJsonResponse.getJSONArray("week");

                for (int i = 0; i < courseArray.length(); i++) {
                    JSONObject currentCourse = courseArray.getJSONObject(i);
                    String time = currentCourse.getString("time");
                    String course = currentCourse.getString("course");
                    String address = currentCourse.getString("address");
                    Course c = new Course(time, course, address);
                    courses.add(c);
                }

            } catch (JSONException e) {
                //解析错误打印日志
                Log.e("QueryUtils", "Problem parsing the course JSON results", e);
            }
            // 返回课表list
            return courses;
        }
    }

}
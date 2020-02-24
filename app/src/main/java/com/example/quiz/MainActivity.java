package com.example.quiz;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Random;
import java.util.ArrayList;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


    Activity context;
    TextView txtView;
    ArrayList<String> stringList = new ArrayList<String>();

    static int questionNum = 0;
    static int rightans =0;
    static int count=0;

    private RadioGroup radioQuestions;
    private RadioButton radioButton;

    RatingBar rb;
    final Random ran=new Random();
    Long startTime;
    ImageView image;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb=findViewById(R.id.ratingBar);
        rb.setVisibility(View.GONE);
        context = this;


        BackgroundTask bt = new BackgroundTask();
        bt.execute("http://www.papademas.net:81/sample.txt"); }
        //grab url
        // end of on create
    //Background process to download file from internet

    private class BackgroundTask extends AsyncTask<String, Integer, Void> {

        protected void onPreExecute() { }
        protected Void doInBackground(String... params) {
            URL url; String StringBuffer = null;
            try {
                //create url object to point to the file location on internet
                url = new URL(params[0]);
//make a request to the server
HttpURLConnection con = (HttpURLConnection)url.openConnection();
//get InputStream instance
InputStream is = con.getInputStream();
//create buffered reader object
BufferedReader br = new BufferedReader(new InputStreamReader(is));
//read content of the file lin eby lie and add to stringBuffer
while ((StringBuffer = br.readLine()) != null) {

                stringList.add(StringBuffer);
                //add to Arraylist
    }
br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            txtView = findViewById(R.id.textView1);
            //display read text in TextView
            questionNum = ran.nextInt(stringList.size());
            txtView.setText(stringList.get(0));
            startQuiz();
        }
    }//end BackgroundTask class
    public void startQuiz() {
        startTime= System.currentTimeMillis();
        buttonListener();
    }
    public void buttonListener() {
        Button btnDisplay;

        radioQuestions = findViewById(R.id.radioQuestions);
        btnDisplay = findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//get selected radio button from radioGroup
int selectId = radioQuestions.getCheckedRadioButtonId();
//find the radiobutton by returned id
radioButton = findViewById(selectId);

                switch (questionNum) {
                    case 0:
//verify if result matches the right button selection //i.e (True or False)
                        if (radioButton.getText().equals("True")){
                            Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();

                            rightans++;
                        } else Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//verify if result matches the right button selection //i.e(True of False)
if (radioButton.getText().equals("False")) {
                        Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                        rightans++;
                } else
                    Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                break;

                case 2:
                if(radioButton.getText().equals("True")){
                    Toast.makeText(MainActivity.this,"Right!", Toast.LENGTH_LONG).show();
                    rightans++; } else
                    Toast.makeText(context,"Wrong",Toast.LENGTH_LONG).show();
                break;
                case 3:
                if(radioButton.getText().equals("True")){
                    Toast.makeText(MainActivity.this,"Right!",Toast.LENGTH_LONG).show();
                    rightans++; } else
                    Toast.makeText(MainActivity.this,"Wrong!",Toast.LENGTH_LONG).show();
                break; case 4:
                if(radioButton.getText().equals("True")){
                    Toast.makeText(MainActivity.this,"Right!",Toast.LENGTH_LONG).show();
                    rightans++; } else
                    Toast.makeText(MainActivity.this,"Wrong",Toast.LENGTH_LONG).show();
                break;
//end switch
}
if(count==4) {
                rb.setVisibility(View.VISIBLE);
                rb.setRating(rightans);
                Long diff= System.currentTimeMillis()-startTime; diff=diff/1000;
                String timeTakenToast ="You completed quiz In: "+diff+ "seconds";
                Toast.makeText(MainActivity.this,timeTakenToast,Toast.LENGTH_LONG).show();
                startTime= System.currentTimeMillis();
}
            }
        });
        imageListener();
    }
    public void imageListener () {
    image = findViewById(R.id.imageView1);
    image.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
//get new question for viewing
if (count == 4) {
            count = -1;
            rightans = 0;
}
if (count != 4) {
            rb.setVisibility(View.GONE);
}
if (questionNum == 4){
//reset count to -1 to start first question again
questionNum = -1; }
            count++; txtView.setText(stringList.get(++questionNum)); //reset radio button(radioTrue) to default
                radioQuestions.check(R.id.radioTrue);
}
        });
    }
    }//END


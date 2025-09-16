package com.example.gemenipromtex;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gemenipromtex.gemini.GeminiCallback;
import com.example.gemenipromtex.gemini.GeminiManager;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private EditText etQuestion;
    private Button btnAsk;
    private TextView tvAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// //////////////////////////////////////////////////////////////
        //  add
        //  implementation("com.google.ai.client.generativeai:generativeai:0.8.0")
        // to build.gradle.kts
        //
        //https://aistudio.google.com/app/apikey
        /// //////////////////////////////////////////////////////////////

        etQuestion = findViewById(R.id.etQuestion);
        tvAnswer = findViewById(R.id.tvAnswer);
        btnAsk = findViewById(R.id.btnAsk);
        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String q = etQuestion.getText().toString();
                if(q.equals(""))
                    q = "מה השם הכי נפוץ בישראל";

                String prompt = q + "תשובה עד 30 מילים";
                //String prompt = "What is the capital of France?";
                GeminiManager.getInstance().sendMessage(prompt, new GeminiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() ->
                                {
                                    tvAnswer.setText(response);
                                }
                        );
                    }

/*                    @Override
                    public void onError(Throwable e) {
                        //runOnUiThread(() ->System.out.println("שגיאה: " + e.getMessage()));
                        runOnUiThread(() ->Log.e(TAG, "שגיאה: " + e.getMessage()));
                        //Toast.makeText(MainActivity.this, "שגיאה: " + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }*/

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Gemini error", e); // prints full stack trace, not just message
                        Toast.makeText(MainActivity.this,
                                "Error: " + e.getClass().getName() + " / " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onError(Exception e) {
                        //runOnUiThread(() -> System.out.println("שגיאה: " + e.getMessage()));
                        runOnUiThread(() ->Log.e(TAG, "שגיאה: " + e.getMessage()));

                        //Toast.makeText(MainActivity.this, "שגיאה: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
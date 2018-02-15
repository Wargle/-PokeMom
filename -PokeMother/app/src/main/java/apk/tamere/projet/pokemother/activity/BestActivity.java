package apk.tamere.projet.pokemother.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

import apk.tamere.projet.pokemother.R;

public class BestActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        inputText = (EditText) findViewById(R.id.textBestInput);

        Button b = (Button) findViewById(R.id.buttonBestSpeak);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.toast_speak, Toast.LENGTH_SHORT).show();
                String toSpeak = inputText.getText().toString();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Button bC = (Button) findViewById(R.id.buttonClear);
        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.getText().clear();
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rBFr:
                if (checked)
                    textToSpeech.setLanguage(Locale.FRANCE);
                    break;
            case R.id.rBGer:
                if (checked)
                    textToSpeech.setLanguage(Locale.GERMANY);
                    break;
            case R.id.rBUk:
                if (checked)
                    textToSpeech.setLanguage(Locale.UK);
                break;
            case R.id.rBIt:
                if (checked)
                    textToSpeech.setLanguage(Locale.ITALY);
                break;
            case R.id.rBJap:
                if (checked)
                    textToSpeech.setLanguage(Locale.JAPAN);
                break;
            case R.id.rBChi:
                if (checked)
                    textToSpeech.setLanguage(Locale.CHINA);
                break;
        }
    }
}

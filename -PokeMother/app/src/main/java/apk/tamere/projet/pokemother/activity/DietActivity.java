package apk.tamere.projet.pokemother.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import apk.tamere.projet.pokemother.R;

public class DietActivity extends AppCompatActivity {

    private String DIET_URL = "https://www.marmiton.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        Spinner spinner = (Spinner) findViewById(R.id.cb_difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cb_diet, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.text_name);
                String nameText = name.getText().toString();

                if(!nameText.equals("")) {
                    nameText.replaceAll(" ", "-");
                    DIET_URL += "/recettes/recherche.aspx?type=all&aqt=" + nameText;
                }

                Uri uri = Uri.parse(DIET_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}

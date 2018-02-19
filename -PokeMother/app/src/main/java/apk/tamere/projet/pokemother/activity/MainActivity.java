package apk.tamere.projet.pokemother.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.adaptateur.MessageAdpatateur;
import apk.tamere.projet.pokemother.metier.Conversation;
import apk.tamere.projet.pokemother.metier.Message;

/**
 * Created by Alexis Arnould on 08/02/2018.
 */
public class MainActivity extends AppCompatActivity {

    private MessageAdpatateur adapter;
    private RecyclerView conv;

    private Conversation mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        setContentView(R.layout.activity_main);
        conv = findViewById(R.id.conver);

        mess = ViewModelProviders.of(this).get(Conversation.class);

        adapter = new MessageAdpatateur(mess.getMessages().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        conv.setLayoutManager(layoutManager);
        conv.setAdapter(adapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(!menuItem.isChecked());
                ((DrawerLayout) findViewById(R.id.mainActivity)).closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.nav_meteo:
                        Toast.makeText(getApplicationContext(), R.string.toast_meteo, Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_alarme:
                        Toast.makeText(getApplicationContext(), R.string.toast_alarme, Toast.LENGTH_SHORT).show();

                        Intent intentAlarm = new Intent(getApplicationContext(), AlarmActivity.class);
                        startActivity(intentAlarm);
                        return true;
                    case R.id.nav_dress:
                        Toast.makeText(getApplicationContext(), R.string.toast_dress, Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_diet:
                        Toast.makeText(getApplicationContext(), R.string.toast_food, Toast.LENGTH_SHORT).show();

                        Intent intentDiet = new Intent(getApplicationContext(), DietActivity.class);
                        startActivity(intentDiet);
                        return true;
                    case R.id.nav_best:
                        Toast.makeText(getApplicationContext(), R.string.toast_best, Toast.LENGTH_SHORT).show();

                        Intent intentBest = new Intent(getApplicationContext(), BestActivity.class);
                        startActivity(intentBest);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), R.string.toast_smth_wrong, Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        ImageButton sendButton = findViewById(R.id.buttonSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new Message(((EditText) findViewById(R.id.textSend)).getText().toString(), false));
                conv.scrollToPosition(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mess.saveMessages(Conversation.FILE_SAVE_NAME);

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mess.saveMessages(Conversation.FILE_SAVE_NAME);

        super.onStop();
    }
}
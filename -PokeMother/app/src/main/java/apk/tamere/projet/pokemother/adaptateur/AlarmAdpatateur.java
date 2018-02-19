package apk.tamere.projet.pokemother.adaptateur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.metier.Alarm;
import apk.tamere.projet.pokemother.metier.Message;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */
public class AlarmAdpatateur extends AbstractAdaptateur<Alarm> {

    public AlarmAdpatateur(List<Alarm> myData) {
        super(myData);
    }

    @Override
    public AlarmAdpatateur.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_alarm, parent, false);

        return new AlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbstractAdaptateur.ViewHolder holder, int position) {
        AlarmViewHolder aHolder = (AlarmViewHolder) holder;
        Alarm current = data.get(position);

        aHolder.time.setText(current.getTime());
        aHolder.date.setText(current.getDate());
        aHolder.active.setChecked(current.isActive());
    }

    public class AlarmViewHolder extends AbstractAdaptateur.ViewHolder {

        private TextView date, time;
        private ToggleButton active;
        private Button bSuppr;

        private AlarmViewHolder(View v) {
            super(v);

            date = v.findViewById(R.id.alarm_date);
            time = v.findViewById(R.id.alarm_time);
            active = v.findViewById(R.id.tB_ring);
            bSuppr = v.findViewById(R.id.b_supprAlarm);

            active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    getItem(getLayoutPosition()).setActive(b);
                }
            });
            bSuppr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(getLayoutPosition());
                }
            });
        }
    }
}



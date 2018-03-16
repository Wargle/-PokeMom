package apk.tamere.projet.pokemother.adaptateur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.metier.Message;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */
public class MessageAdpatateur extends AbstractAdaptateur<Message> {

    public MessageAdpatateur(List<Message> myData) { super(myData); }

    @Override
    public MessageAdpatateur.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_message, parent, false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbstractAdaptateur.ViewHolder holder, int position) {
        MessageViewHolder mHolder = (MessageViewHolder) holder;
        Message current = data.get(position);
        if (current.isMom()) {
            mHolder.right.setVisibility(LinearLayout.GONE);
            mHolder.leftT.setText(data.get(position).getMessage());
        } else {
            mHolder.left.setVisibility(LinearLayout.GONE);
            mHolder.rightT.setText(data.get(position).getMessage());
        }

        holder.itemView.setSelected(selectedItem == position);
    }


    public class MessageViewHolder extends AbstractAdaptateur.ViewHolder {

        private TextView leftT, rightT;
        private LinearLayout right;
        private RelativeLayout left;

        private MessageViewHolder(View v) {
            super(v);

            left = v.findViewById(R.id.cell_left);
            right = v.findViewById(R.id.cell_right);
            leftT = v.findViewById(R.id.cell_left_text);
            rightT = v.findViewById(R.id.cell_right_text);
        }
    }
}



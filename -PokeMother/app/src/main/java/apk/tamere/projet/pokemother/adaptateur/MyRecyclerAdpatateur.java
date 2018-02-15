package apk.tamere.projet.pokemother.adaptateur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.metier.Message;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */
public class MyRecyclerAdpatateur extends RecyclerView.Adapter<MyRecyclerAdpatateur.ViewHolder> {

    private List<Message> data;
    private int selectedItem = 0;

    public MyRecyclerAdpatateur(List<Message> myData) {
        data = myData;
    }

    public void add(Message item) {
        if(!data.contains(item))
        {
            item.setMessage(item.getMessage() + " :: " + data.size());
            data.add(0, item);
            notifyItemInserted(data.lastIndexOf(item));
        }
    }

    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyRecyclerAdpatateur.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_recyclerview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdpatateur.ViewHolder holder, int position) {
        Message current = data.get(position);
        if(current.isMom()) {
            holder.right.setVisibility(LinearLayout.GONE);
            holder.leftT.setText(data.get(position).getMessage());
        }
        else {
            holder.left.setVisibility(LinearLayout.GONE);
            holder.rightT.setText(data.get(position).getMessage());
        }

        holder.itemView.setSelected(selectedItem == position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView leftT, rightT;
        public LinearLayout left, right;

        private ViewHolder(View v) {
            super(v);

            left = v.findViewById(R.id.cell_left);
            right = v.findViewById(R.id.cell_right);
            leftT = v.findViewById(R.id.cell_left_text);
            rightT = v.findViewById(R.id.cell_right_text);
        }
    }
}



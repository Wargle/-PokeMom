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
public abstract class AbstractAdaptateur<T> extends RecyclerView.Adapter<AbstractAdaptateur.ViewHolder> {

    protected List<T> data;
    protected int selectedItem = 0;

    public AbstractAdaptateur(List<T> myData) {
        data = myData;
    }

    public void add(T item, int pos) {
        if(!data.contains(item))
        {
            data.add(pos, item);
            notifyItemInserted(data.lastIndexOf(item));
        }
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void add(T item) {
        add(item, 0);
    }

    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        protected ViewHolder(View v) {
            super(v);
        }
    }

    public interface CallBack {
        void onItemClicked(int position);
    }
}



package local.hal.st31.android.managementapp.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import local.hal.st31.android.managementapp.models.Graphs;

public class GraphAdapter{
    ArrayList<Graphs> list;
    Context context;

    public GraphAdapter(ArrayList<Graphs> list, Context context){
        this.list = list;
        this.context = context;
    }
    public int getItemCount(){
        return list.size();
    }
}

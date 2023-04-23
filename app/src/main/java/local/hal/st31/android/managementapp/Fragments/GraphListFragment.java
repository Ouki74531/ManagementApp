package local.hal.st31.android.managementapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import local.hal.st31.android.managementapp.Adapter.TaskAdapter;
import local.hal.st31.android.managementapp.R;
import local.hal.st31.android.managementapp.databinding.FragmentGraphBinding;
import local.hal.st31.android.managementapp.databinding.FragmentGraphListBinding;
import local.hal.st31.android.managementapp.models.Tasks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphListFragment#} factory method to
 * create an instance of this fragment.
 */
public class GraphListFragment extends Fragment {

    public GraphListFragment() {

    }
    FragmentGraphListBinding binding;
    ArrayList<Tasks> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentGraphListBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        TaskAdapter adapter = new TaskAdapter(list, getContext());
        binding.graphListRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.graphListRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tasks tasks = dataSnapshot.getValue(Tasks.class);
                    list.add(tasks);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}
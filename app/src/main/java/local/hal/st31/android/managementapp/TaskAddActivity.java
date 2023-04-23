package local.hal.st31.android.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import local.hal.st31.android.managementapp.Adapter.TaskAdapter;
import local.hal.st31.android.managementapp.databinding.ActivityTaskAddBinding;
import local.hal.st31.android.managementapp.models.Tasks;

public class TaskAddActivity extends AppCompatActivity {

    ActivityTaskAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        binding.btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = binding.etTaskName.getText().toString();
                Tasks tasks = new Tasks(taskName, "0");
                database.getReference().child("tasks").child(taskName).setValue(tasks).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(TaskAddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
package local.hal.st31.android.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import local.hal.st31.android.managementapp.databinding.ActivityTaskEditBinding;
import local.hal.st31.android.managementapp.models.Tasks;

public class TaskEditActivity extends AppCompatActivity {

    ActivityTaskEditBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String taskId = getIntent().getStringExtra("taskId");
        String taskName = getIntent().getStringExtra("taskName");
        String taskValue = getIntent().getStringExtra("taskValue");

        binding.tvTaskEditName.setText(taskName);
        binding.tvTaskName.setText(taskName);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        binding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = binding.etValue.getText().toString();
                if (Integer.parseInt(value) < 0 || Integer.parseInt(value) > 100){
                    Toast.makeText(TaskEditActivity.this, "タスク進捗は0〜100の間で入力してください。", Toast.LENGTH_SHORT).show();
                }else{
                    Tasks tasks = new Tasks(taskId, taskName, value);
                    database.getReference().child("tasks").child(taskName).setValue(tasks).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Tasks tasks1 = new Tasks(taskName, value);
                            Intent intent = new Intent(TaskEditActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
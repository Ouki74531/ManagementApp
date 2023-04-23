package local.hal.st31.android.managementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import local.hal.st31.android.managementapp.Adapter.ChatAdapter;
import local.hal.st31.android.managementapp.databinding.ActivityPrivateChatBinding;
import local.hal.st31.android.managementapp.models.MessageModel;

public class PrivateChatActivity extends AppCompatActivity {

    ActivityPrivateChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivateChatBinding.inflate(getLayoutInflater());//どのレイアウトのファイルを使用するか
        setContentView(binding.getRoot());

        getSupportActionBar().hide();//アクションバーをかくす
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        String recieveId = getIntent().getStringExtra("userId");
        String profilePicture = getIntent().getStringExtra("profilePicture");
        String userName = getIntent().getStringExtra("userName");

        binding.tvPersonName.setText(userName);
        Picasso.get().load(profilePicture).placeholder(R.drawable.person).into(binding.ivProfile);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrivateChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, recieveId);

        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

         database.getReference().child("chats")
                 .child(senderRoom)
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         messageModels.clear();
                         for(DataSnapshot snapshot1 : snapshot.getChildren()){
                             MessageModel model = snapshot1.getValue(MessageModel.class);
                             model.setMessageId(snapshot1.getKey());
                             messageModels.add(model);
                         }
                         chatAdapter.notifyDataSetChanged();
                     }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        binding.ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId,message);
                model.setuId(senderId);
                model.setTimeStamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {//値挿入
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

                    }
                });
            }
        });
    }
}
package local.hal.st31.android.managementapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import local.hal.st31.android.managementapp.PrivateChatActivity;
import local.hal.st31.android.managementapp.R;
import local.hal.st31.android.managementapp.models.Users;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{
     ArrayList<Users> list;
     Context context;

     public UsersAdapter(ArrayList<Users> list, Context context){//一つの要素のデザインを決める
          this.list = list;
          this.context = context;
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//チャット相手のユーザー一覧
          View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
          return new ViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          Users users = list.get(position);
          holder.userName.setText(users.getName());

          FirebaseDatabase.getInstance().getReference().child("chats")
                          .child(FirebaseAuth.getInstance().getUid() + users.getUserId()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {//データセット
                            if(snapshot.hasChildren()){
                                 for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                      holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                                 }
                            }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                  });

          holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Intent intent = new Intent(context, PrivateChatActivity.class);
                    intent.putExtra("userId",users.getUserId());
                    intent.putExtra("profilePicture",users.getPicture());
                    intent.putExtra("userName", users.getName());
                    context.startActivity(intent);
               }
          });
     }

     @Override
     public int getItemCount() {//DMに表示する項目の数
          return list.size();
     }

     public class ViewHolder extends RecyclerView.ViewHolder{
          ImageView image;
          TextView userName, lastMessage;
          public ViewHolder(@NonNull View itemView){
               super(itemView);
               image = itemView.findViewById(R.id.profileImage);
               userName = itemView.findViewById(R.id.userNameList);
               lastMessage = itemView.findViewById(R.id.lastMessage);
          }
     }
}

package me.riddhimanadib.bottomnavbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PreviousAdapter extends BaseAdapter {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("user").child("recent");

    private ArrayList<PreviousList> previousList = new ArrayList<>();

    private ChildEventListener removeData = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            dataSnapshot.getRef().removeValue();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public int getCount() {
        return previousList.size();
    }

    @Override
    public PreviousList getItem(int i) {
        return previousList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.previous_list, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView food = (TextView) view.findViewById(R.id.previousText);
        Button delete = (Button) view.findViewById(R.id.deleteButton);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        //ListViewItem listViewItem = listViewItemList.get(position);
        final PreviousList previous = previousList.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        food.setText(previous.getFood());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.orderByValue().equalTo(previousList.get(pos).getFood()).addChildEventListener(removeData);
                //databaseReference.push().setValue(previousList.get(pos).getFood());
            }
        });

        return view;
    }

    public void add(String a){
        PreviousList previous = new PreviousList();
        previous.setFood(a);

        previousList.add(previous);
    }

    public void clear(){
        previousList.clear();
    }

    public void removeListener(){
        databaseReference.removeEventListener(removeData);
    }
}

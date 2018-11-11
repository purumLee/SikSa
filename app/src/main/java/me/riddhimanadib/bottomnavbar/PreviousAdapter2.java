package me.riddhimanadib.bottomnavbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousAdapter2 extends ArrayAdapter implements View.OnClickListener{

    public interface ListBtnClickListener{
        void onListBtnClick(int position) ;
    }

    int resourceId ;
    private ListBtnClickListener listBtnClickListener ;

    public PreviousAdapter2(Context context, int resource, ArrayList<PreviousList> list, ListBtnClickListener clickListener) {
        super(context, resource, list);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resourceId, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView food = (TextView) view.findViewById(R.id.previousText);
        Button delete = (Button) view.findViewById(R.id.deleteButton);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        //final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);
        //PreviousList previous = (PreviousList) getItem(i);

        // 아이템 내 각 위젯에 데이터 반영
        //food.setText(previous.getFood());

        delete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

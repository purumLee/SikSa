package me.riddhimanadib.bottomnavbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class FirstFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    EditText edit;
    Button button;
    Button delete;
    ListView searchList;
    ListView recentList;
    ArrayAdapter adapter;
    PreviousAdapter recent;
    ArrayList<String> AL = new ArrayList<>();
    ArrayList<String> Desc = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    String key="Gtop56GhMRShnw3G5lfZpROYqvlj9Impr08eSCr6AXj%2BbqIGzCSS6vNipej1RmsDS5PepYa1TDj0h3s5jPQbnA%3D%3D";
    String queryUrl = null;

    boolean inDesc = false, inServ = false, inCont1 = false, inCont2 = false, inCont3 = false, inCont4 = false,
            inCont5 = false,  inCont6 = false,  inCont7 = false,  inCont8 = false,  inCont9 = false, inPlant = false;
    String desc = null, serv = null, cont1 = null, cont2 = null, cont3 = null, cont4 = null,
            cont5 = null, cont6 = null, cont7 = null, cont8 = null, cont9 = null, plant = null;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StrictMode.enableDefaults();

        View view = inflater.inflate(R.layout.fragment_first, null) ;
        edit = (EditText) view.findViewById(R.id.edit);
        button = (Button) view.findViewById(R.id.button);
        delete = (Button) view.findViewById(R.id.delete_all);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
        //recent = new PreviousAdapter2(getContext(), R.layout.previous_list, new ArrayList<PreviousList>(), this);
        recent = new PreviousAdapter();
        searchList = (ListView) view.findViewById(R.id.searchList);
        recentList = (ListView) view.findViewById(R.id.previousList);
        //listview.setAdapter(adapter);
        searchList.setAdapter(adapter);
        recentList.setAdapter(recent);

        final FrameLayout frame = (FrameLayout) view.findViewById(R.id.frame);
        final LinearLayout defaultView = (LinearLayout) view.findViewById(R.id.default_state);
        frame.removeView(searchList);

        //recent.add("1");
        //databaseReference.push().setValue("1");

        databaseReference.child("user").child("recent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recent.clear();
                for (DataSnapshot testData : dataSnapshot.getChildren()){

                    //String test = testData.child("food").child("user").child("20181101").getValue().toString();
                    String test = testData.getValue().toString();
                    recent.add(test);
                }
                recent.notifyDataSetChanged();
                recentList.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final ChildEventListener removeData = new ChildEventListener() {
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

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String str = edit.toString();
                if (str.length() > 0){
                    //button.setEnabled(true);
                    //AL.clear();
                    //Desc.clear();
                    //searchList.setAdapter(adapter);
                    frame.removeAllViews();
                    frame.addView(searchList);
                    button.callOnClick();
                    //getData();
                }
                else {
                    /*adapter.clear();
                    AL.clear();
                    Desc.clear();
                    //listview.setAdapter(adapter) ;
                    adapter.notifyDataSetChanged();
                    //button.setEnabled(false);*/

                    //searchList.setAdapter(recent);
                    frame.removeAllViews();
                    frame.addView(defaultView);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        };

        edit.addTextChangedListener(textWatcher);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //edit.setText(Desc.get(position));
                getDetailData(position);
                databaseReference.child("user").child("recent").push().setValue(Desc.get(position));
                recent.removeListener();
                //edit.setText(Integer.toString(position));
                //edit.setText(serv);
                Intent intent = new Intent(getActivity(), Information.class);
                intent.putExtra("food", Desc.get(position));
                intent.putExtra("serv", serv);
                intent.putExtra("cont1", cont1);
                intent.putExtra("cont2", cont2);
                intent.putExtra("cont3", cont3);
                intent.putExtra("cont4", cont4);
                intent.putExtra("cont5", cont5);
                intent.putExtra("cont6", cont6);
                intent.putExtra("cont7", cont7);
                intent.putExtra("cont8", cont8);
                intent.putExtra("cont9", cont9);
                intent.putExtra("plant", plant);
                startActivity(intent);
            }
        }) ;

        recentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edit.setText(recent.getItem(i).getFood());
                edit.setSelection(edit.getText().length());
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adapter.clear();
                AL.clear();
                Desc.clear();
                getData();
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                /*new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {// 스레드에게 수행시킬 동작들 구현
                            Thread.sleep(2000); // 1초간 Thread를 잠재운다
                            } catch (InterruptedException e) {e.printStackTrace();}
                        getData();
                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                //listview.setAdapter(adapter) ;
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();*/
                adapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseReference.child("user").child("recent").addChildEventListener(removeData);
                databaseReference.child("user").child("recent").removeValue();
            }
        });

        //return inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }

    /*public void mOnClick(View v){
        switch( v.getId() ){
            case R.id.button:

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                        AL.clear();
                        getData();

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                //text.setText(data); //TextView에 문자열  data 출력
                                listview.setAdapter(adapter) ;
                            }
                        });
                    }
                }).start();
                break;
        }
    }//mOnClick method..*/


    //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    //String getXmlData()
    void getData(){
        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = null;//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        try {
            location = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //location = URLEncoder.encode(str);
        //String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";
        //String query = "%EB%B0%94%EB%82%98%EB%82%98%EC%B9%A9";

        queryUrl="http://apis.data.go.kr/1470000/FoodNtrIrdntInfoService/getFoodNtrItdntList?"//요청 URL
                //+"&pageNo=1&numOfRows=1000&ServiceKey=" + key
                +"&pageNo=1&numOfRows=100&ServiceKey=" + key
                +"&desc_kor="+location;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            //xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            xpp.setInput( new InputStreamReader(is));


            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기
                        if (tag.equals("item")) ;// 첫번째 검색결과
                        if (tag.equals("DESC_KOR")) {
                            inDesc = true;
                        }
                        if (tag.equals("ANIMAL_PLANT")) {
                            inPlant = true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (inDesc) {
                            desc = xpp.getText();
                            Desc.add(desc);
                            inDesc = false;
                        }
                        if (inPlant) {
                            plant = xpp.getText();
                            inPlant = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기
                        if (tag.equals("item")) {
                            if (plant.equals("\n"))
                                //AL.add(desc);
                                adapter.add(desc);
                            else
                                //AL.add("[" + plant + "] " + desc);
                                adapter.add("[" + plant + "] " + desc);
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }

    void getDetailData(int a){
        int count = 0;
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            //xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            xpp.setInput( new InputStreamReader(is));


            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("item")) ;// 첫번째 검색결과
                        if(tag.equals("SERVING_WT") && (count == a)){
                            inServ = true;
                        }
                        if(tag.equals("NUTR_CONT1") && (count == a)){
                            inCont1 = true;
                        }
                        if(tag.equals("NUTR_CONT2") && (count == a)){
                            inCont2 = true;
                        }
                        if(tag.equals("NUTR_CONT3") && (count == a)){
                            inCont3 = true;
                        }
                        if(tag.equals("NUTR_CONT4") && (count == a)){
                            inCont4 = true;
                        }
                        if(tag.equals("NUTR_CONT5") && (count == a)){
                            inCont5 = true;
                        }
                        if(tag.equals("NUTR_CONT6") && (count == a)){
                            inCont6 = true;
                        }
                        if(tag.equals("NUTR_CONT7") && (count == a)){
                            inCont7 = true;
                        }
                        if(tag.equals("NUTR_CONT8") && (count == a)){
                            inCont8 = true;
                        }
                        if(tag.equals("NUTR_CONT9") && (count == a)){
                            inCont9 = true;
                        }
                        if(tag.equals("ANIMAL_PLANT") && (count == a)){
                            inPlant = true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if(inServ){
                            serv = xpp.getText();
                            inServ = false;
                        }
                        if(inCont1){
                            cont1 = xpp.getText();
                            inCont1 = false;
                        }
                        if(inCont2){
                            cont2 = xpp.getText();
                            inCont2 = false;
                        }
                        if(inCont3){
                            cont3 = xpp.getText();
                            inCont3 = false;
                        }
                        if(inCont4){
                            cont4 = xpp.getText();
                            inCont4 = false;
                        }
                        if(inCont5){
                            cont5 = xpp.getText();
                            inCont5 = false;
                        }
                        if(inCont6){
                            cont6 = xpp.getText();
                            inCont6 = false;
                        }
                        if(inCont7){
                            cont7 = xpp.getText();
                            inCont7 = false;
                        }
                        if(inCont8){
                            cont8 = xpp.getText();
                            inCont8 = false;
                        }
                        if(inCont9){
                            cont9 = xpp.getText();
                            inCont9 = false;
                        }
                        if(inPlant){
                            plant = xpp.getText();
                            inPlant = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")){
                            count++;
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }
}

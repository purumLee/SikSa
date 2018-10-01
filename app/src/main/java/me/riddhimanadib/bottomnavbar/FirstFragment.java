package me.riddhimanadib.bottomnavbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class FirstFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    EditText edit;
    Button button;
    ListView listview;
    ArrayAdapter adapter;
    ArrayList<String> AL = new ArrayList<>();
    ArrayList<String> Desc = new ArrayList<>();

    String key="Gtop56GhMRShnw3G5lfZpROYqvlj9Impr08eSCr6AXj%2BbqIGzCSS6vNipej1RmsDS5PepYa1TDj0h3s5jPQbnA%3D%3D";

    boolean inDesc = false, inPlant = false;
    String desc = null, plant = null;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, null) ;
        edit = (EditText) view.findViewById(R.id.edit);
        button = (Button) view.findViewById(R.id.button);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, AL);
        listview = (ListView) view.findViewById(R.id.listview1) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                edit.setText(Desc.get(position));
            }
        }) ;

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AL.clear();
                        Desc.clear();
                        getData();

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                listview.setAdapter(adapter) ;
                            }
                        });
                    }
                }).start();
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

        String queryUrl="http://apis.data.go.kr/1470000/FoodNtrIrdntInfoService/getFoodNtrItdntList?"//요청 URL
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

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("item")) ;// 첫번째 검색결과
                        if(tag.equals("DESC_KOR")){
                            inDesc = true;
                        }
                        if(tag.equals("ANIMAL_PLANT")){
                            inPlant = true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if(inDesc){
                            desc = xpp.getText();
                            Desc.add(desc);
                            inDesc = false;
                        }
                        if(inPlant){
                            plant = xpp.getText();
                            inPlant = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")){
                            if(plant.equals("\n"))
                                AL.add(desc);
                            else
                                AL.add("["+plant+"] "+desc);
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

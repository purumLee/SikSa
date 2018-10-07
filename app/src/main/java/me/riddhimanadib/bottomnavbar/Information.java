package me.riddhimanadib.bottomnavbar;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class Information extends AppCompatActivity {
    TextView food, serv, cont1, cont2, cont3, cont4, cont5, cont6, cont7, cont8, cont9, plant;
    String foodName;
    String desc_str = null, serv_str = null, cont1_str = null, cont2_str = null, cont3_str = null, cont4_str = null,
            cont5_str = null, cont6_str = null, cont7_str = null, cont8_str = null, cont9_str = null, plant_str = null;

    String key="Gtop56GhMRShnw3G5lfZpROYqvlj9Impr08eSCr6AXj%2BbqIGzCSS6vNipej1RmsDS5PepYa1TDj0h3s5jPQbnA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        StrictMode.enableDefaults();

        Intent intent = getIntent();
        foodName = intent.getStringExtra("food");
        serv_str = intent.getStringExtra("serv");
        cont1_str = intent.getStringExtra("cont1");
        cont2_str = intent.getStringExtra("cont2");
        cont3_str = intent.getStringExtra("cont3");
        cont4_str = intent.getStringExtra("cont4");
        cont5_str = intent.getStringExtra("cont5");
        cont6_str = intent.getStringExtra("cont6");
        cont7_str = intent.getStringExtra("cont7");
        cont8_str = intent.getStringExtra("cont8");
        cont9_str = intent.getStringExtra("cont9");
        plant_str = intent.getStringExtra("plant");

        food = (TextView)findViewById(R.id.food_name);
        serv = (TextView)findViewById(R.id.serving_wt_data);
        cont1 = (TextView)findViewById(R.id.nutr_cont1_data);
        cont2 = (TextView)findViewById(R.id.nutr_cont2_data);
        cont3 = (TextView)findViewById(R.id.nutr_cont3_data);
        cont4 = (TextView)findViewById(R.id.nutr_cont4_data);
        cont5 = (TextView)findViewById(R.id.nutr_cont5_data);
        cont6 = (TextView)findViewById(R.id.nutr_cont6_data);
        cont7 = (TextView)findViewById(R.id.nutr_cont7_data);
        cont8 = (TextView)findViewById(R.id.nutr_cont8_data);
        cont9 = (TextView)findViewById(R.id.nutr_cont9_data);
        plant = (TextView)findViewById(R.id.animal_plant_data);

        //getData();

        food.setText(foodName);
        serv.setText(serv_str+" g");
        cont1.setText(cont1_str+" kcal");
        cont2.setText(cont2_str+" g");
        cont3.setText(cont3_str+" g");
        cont4.setText(cont4_str+" g");
        cont5.setText(cont5_str+" g");
        cont6.setText(cont6_str+" mg");
        cont7.setText(cont7_str+" mg");
        cont8.setText(cont8_str+" g");
        cont9.setText(cont9_str+" g");
        plant.setText(plant_str);
    }

    /*void getData(){
        String location = null;//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        try {
            location = URLEncoder.encode(foodName, "UTF-8");
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
                            //desc = xpp.getText();
                            //Desc.add(desc);
                            inDesc = false;
                        }
                        if(inPlant){
                            plant_str = xpp.getText();
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
    }*/
}

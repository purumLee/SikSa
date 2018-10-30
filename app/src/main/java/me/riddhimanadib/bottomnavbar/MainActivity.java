package me.riddhimanadib.bottomnavbar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.library.BottomBarHolderActivity;
import me.riddhimanadib.library.NavigationPage;

public class MainActivity extends BottomBarHolderActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationPage page1 = new NavigationPage("검색", ContextCompat.getDrawable(this, R.drawable.ic_search), FirstFragment.newInstance());
        NavigationPage page2 = new NavigationPage("카테고리", ContextCompat.getDrawable(this, R.drawable.ic_category), SecondFragment.newInstance());
        NavigationPage page3 = new NavigationPage("캘린더", ContextCompat.getDrawable(this, R.drawable.ic_calendar), SampleFragment.newInstance());
        NavigationPage page4 = new NavigationPage("마이페이지", ContextCompat.getDrawable(this, R.drawable.ic_my), FourthFragment.newInstance());

        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);

        super.setupBottomBarHolderActivity(navigationPages);
    }

    @Override
    public void onClicked() {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    }
}
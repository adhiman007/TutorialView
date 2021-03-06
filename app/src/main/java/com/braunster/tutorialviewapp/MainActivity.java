package com.braunster.tutorialviewapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.braunster.tutorialview.object.Tutorial;
import com.braunster.tutorialview.object.TutorialBuilder;
import com.braunster.tutorialview.object.TutorialIntentBuilder;
import com.braunster.tutorialview.view.TutorialView;

import java.util.Random;


public class MainActivity extends Activity implements View.OnClickListener {

    private TutorialView tutorialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For testing with no status bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // For testing with no action bar
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        tutorialView = (TutorialView) findViewById(R.id.tutorial_view);

        findViewById(R.id.view_bottom_left).setOnClickListener(this);
        findViewById(R.id.view_top_right).setOnClickListener(this);
        findViewById(R.id.view_almost_top_right).setOnClickListener(this);
        findViewById(R.id.view_top_left).setOnClickListener(this);
        findViewById(R.id.view_center).setOnClickListener(this);

        if (getActionBar() != null)
            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        //Using the tutorial view
        // This is used for the tutorial view that should be in your root view.
        // This may lead to problems when used inside LinearLayout and maybe other view.
        // The best thing to do is to use the TutorialActivity.
        tutorialView.setActionBarRestoreColor(Color.DKGRAY);
        tutorialView.changeActionBarColor(true);
        tutorialView.setActionBar(getActionBar());
        tutorialView.setHasActionBar(true);
        tutorialView.setTutorialTextTypeFace("fonts/roboto_light.ttf");
        tutorialView.setHasStatusBar(true);
        tutorialView.setTutorialText("This is some general text that is not that long but also not so short.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private int randomColor(){
        Random rnd = new Random();
        return  Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onBackPressed() {
        if (tutorialView.isShowing())
            tutorialView.closeTutorial();
        else
            super.onBackPressed();
    }

    private TutorialBuilder getBasicBuilderForTest(View v){
        TutorialBuilder tBuilder = new TutorialBuilder();

        tBuilder.setTitle("The Title")
                .setViewToSurround(v)
                .setInfoText("This is the explanation about the view.")
                .setBackgroundColor(randomColor())
                .setTutorialTextColor(Color.WHITE)
                .setTutorialTextTypeFaceName("fonts/olivier.ttf")
                .setTutorialTextSize(30)
                .setAnimationDuration(500);
        
        return tBuilder;        
    }

    @Override
    public void onClick(View v) {

        TutorialIntentBuilder builder = new TutorialIntentBuilder(MainActivity.this);

        TutorialBuilder tBuilder = getBasicBuilderForTest(v);
        
        switch (v.getId())
        {
            case R.id.view_bottom_left:

                tBuilder.setTutorialInfoTextPosition(Tutorial.InfoPosition.ABOVE);
                tBuilder.setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM);

                break;

            case R.id.view_top_right:
                
                tBuilder.setTutorialInfoTextPosition(Tutorial.InfoPosition.LEFT_OF);

                break;

            case R.id.view_almost_top_right:

                tBuilder.setTutorialInfoTextPosition(Tutorial.InfoPosition.RIGHT_OF);

                break;

            case R.id.view_top_left:

                tBuilder.setTutorialInfoTextPosition(Tutorial.InfoPosition.BELOW);

                break;

            case R.id.view_center:

                tBuilder.setTutorialInfoTextPosition(Tutorial.InfoPosition.BELOW);
                tBuilder.setTutorialGotItPosition(Tutorial.GotItPosition.BOTTOM);
                
                break;
        }

        builder.setTutorial(tBuilder.build());

        startActivity(builder.getIntent());

        // Override the default animation of the entering activity.
        // This will allow the nice wrapping of the view by the tutorial activity.
        overridePendingTransition(R.anim.dummy, R.anim.dummy);
    }
}

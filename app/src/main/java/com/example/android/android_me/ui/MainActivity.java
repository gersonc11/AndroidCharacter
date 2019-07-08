package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.onItemClickListener {

    int headIndex =0, bodyIndex =0, legIndex =0;

    boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.linear_layout_tablet)!=null) {
            mTwoPane = true;
            GridView gridView = findViewById(R.id.master_list_grid_view);
            gridView.setNumColumns(2);

            findViewById(R.id.nextbtn).setVisibility(View.GONE);

            populateFrameLayouts(savedInstanceState);
        } else {
            mTwoPane = false;
        }
    }

    private void populateFrameLayouts(Bundle savedInstanceState) {
        if(savedInstanceState==null) {
            BodyPartFragment headFragment = new BodyPartFragment();

            headFragment.setmImageIds(AndroidImageAssets.getHeads());
            headFragment.setmListIndex(1);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.head_container, headFragment).commit();

            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
            fragmentManager.beginTransaction().replace(R.id.body_container, bodyFragment).commit();


            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setmImageIds(AndroidImageAssets.getLegs());
            fragmentManager.beginTransaction().replace(R.id.leg_container, legFragment).commit();
        }
    }

    public void onItemClick (int position) {
        Toast.makeText(this, "position clicked " + position, Toast.LENGTH_SHORT).show();

        int indexOfBodyPart = position/12;

        int indexPartClicked = position - 12*indexOfBodyPart;

        if(mTwoPane) {
            BodyPartFragment bodyPartFragment = new BodyPartFragment();
            switch (indexOfBodyPart) {
                case 0:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getHeads());

                    bodyPartFragment.setmListIndex(indexPartClicked );

                    getSupportFragmentManager().beginTransaction().replace(R.id.head_container, bodyPartFragment).commit();
                    break;
                case 1:

                    bodyPartFragment.setmImageIds(AndroidImageAssets.getBodies());

                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, bodyPartFragment).commit();

                    break;
                case 2:

                    bodyPartFragment.setmImageIds(AndroidImageAssets.getLegs());

                    getSupportFragmentManager().beginTransaction().replace(R.id.leg_container, bodyPartFragment).commit();

                    break;
            }

        } else {
            switch (indexOfBodyPart) {
                case 0:
                    headIndex = indexPartClicked;
                    break;
                case 1:
                    bodyIndex = indexPartClicked;
                    break;
                case 2:
                    legIndex = indexPartClicked;
                    break;
            }
        }

        Bundle b = new Bundle();
        b.putInt("headIndex", headIndex);
        b.putInt("bodyIndex", bodyIndex);
        b.putInt("legIndex", legIndex);

        final Intent i = new Intent(this, AndroidMeActivity.class);
        i.putExtras(b);

        findViewById(R.id.nextbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }
}

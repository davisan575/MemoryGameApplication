package com.example.project_one;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    private Vector<Pair<Integer, Boolean>> v;
    private Vector<Drawable> availableDrawables;
    private Vector<Pair<Integer,Integer>> matches;
    private Vector<Drawable> inOrderDrawables;
    private Integer numClicks = 0;
    private Integer remainingPairs;
    private Boolean pause = false;
    private Integer numOpenImages = 0;
    private ImageView previousImageView;
    private Integer prevIVal;
    private HashMap<Integer,Integer> drawableReferenceLookUp = new HashMap<Integer,Integer>();
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        c = this;
        GridLayout gridLayout = findViewById(R.id.rooLayout);
        int column = getIntent().getIntExtra("column", 4);
        int row = getIntent().getIntExtra("row",4);
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        remainingPairs = (row * column) / 2;
        v = new Vector<Pair<Integer, Boolean>>(row*column);
        availableDrawables = new Vector<Drawable>(row*column);
        inOrderDrawables = new Vector<Drawable>(row*column);
        int[] setNums = packRandomNumbers((row * column), (row * column) - 1);
        SetDrawables();
        for(int i = 0; i < (row*column); i++)
        {
            inOrderDrawables.add(null);
        }
        previousImageView = new ImageView(this);

        Integer drawableRef = 0;
        Log.d("onCreate", Integer.toString(inOrderDrawables.size()));

        for(Integer iter = 0; iter < v.size(); iter+=2)
        {
//            System.out.println(v.get(iter).first);
//            System.out.println(v.get(iter).second);
//            Log.d("onCreate",Integer.toString(v.get(iter).first));
//            Log.d("onCreate",Integer.toString(setNums[iter]));
            inOrderDrawables.setElementAt(availableDrawables.get(drawableRef),v.get(iter).first);
            inOrderDrawables.setElementAt(availableDrawables.get(drawableRef),v.get(iter+1).first);
            drawableReferenceLookUp.put(v.get(iter).first,drawableRef);
            drawableReferenceLookUp.put(v.get(iter+1).first,drawableRef);
            drawableRef++;
        }
        for (int i = 0; i < column * row; i++) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            final ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(param);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.paper_plane));
            imageView.setBackground(getResources().getDrawable(R.drawable.boarder));
            gridLayout.addView(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!pause)
                    {
                        //Toast.makeText(GameActivity.this, "i=" + finalI, Toast.LENGTH_SHORT).show();
                        imageView.setImageDrawable(inOrderDrawables.get(finalI));
                        numOpenImages++;
                        if (numOpenImages == 1)
                        {
                            previousImageView = imageView;
                            prevIVal = finalI;
                            previousImageView.setClickable(false);
                        }
                        if (numOpenImages == 2)
                        {
                            pause = true;
                            numClicks++;
                            Integer prevImage = drawableReferenceLookUp.get(prevIVal);
                            Integer currImage = drawableReferenceLookUp.get(finalI);
                            if(prevImage == currImage)
                            {
                                imageView.setClickable(false);
                                previousImageView.setClickable(false);
                                remainingPairs--; // one less pair remaining to find
                                pause = false;
                            }
                            else
                            {
                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        previousImageView.setImageDrawable(getResources().getDrawable(R.drawable.paper_plane));
                                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.paper_plane));
                                        previousImageView.setClickable(true);
                                        pause = false;
                                    }
                                }, 1000);
                            }
                            numOpenImages = 0;
                            if (remainingPairs == 0)
                            {
                                Intent intent = new Intent(c, ScoreActivity.class);
                                intent.putExtra("score", numClicks);
                                startActivity(intent);
                            }
                        }
                        TextView sText = (TextView)findViewById(R.id.scoreText);
                        sText.setText("Score: " + Integer.toString(numClicks));
                    } // end pause check

                } // end onClick
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "in onStart method.");
    }

    public static final Random gen = new Random();
    public int[] packRandomNumbers(int n, int maxRange) {
        Log.d("packRandomNumbers", "running pack random numbers");
        assert n <= maxRange : "cannot get more unique numbers than the size of the range";

        int[] result = new int[n];
        Set<Integer> used = new HashSet<Integer>();

        for (int i = 0; i < n; i++) {

            int newRandom;
            do {
                newRandom = gen.nextInt(maxRange+1);
            } while (used.contains(newRandom));
            v.add(new Pair<Integer, Boolean>(newRandom, true));
            result[i] = newRandom;
            used.add(newRandom);
        }
        return result;
    }

    public void SetDrawables() {
        availableDrawables.add(getResources().getDrawable(R.drawable.fishy));
        availableDrawables.add(getResources().getDrawable(R.drawable.dog));
        availableDrawables.add(getResources().getDrawable(R.drawable.dolphin));
        availableDrawables.add(getResources().getDrawable(R.drawable.donkey));
        availableDrawables.add(getResources().getDrawable(R.drawable.dragon));
        availableDrawables.add(getResources().getDrawable(R.drawable.duck));
        availableDrawables.add(getResources().getDrawable(R.drawable.elephant));
        availableDrawables.add(getResources().getDrawable(R.drawable.flamingo));
        availableDrawables.add(getResources().getDrawable(R.drawable.kangaroo));
        availableDrawables.add(getResources().getDrawable(R.drawable.kitty));
        availableDrawables.add(getResources().getDrawable(R.drawable.rabbit));
        availableDrawables.add(getResources().getDrawable(R.drawable.sheep));
        availableDrawables.add(getResources().getDrawable(R.drawable.turtle));
        availableDrawables.add(getResources().getDrawable(R.drawable.chicken));
        availableDrawables.add(getResources().getDrawable(R.drawable.frog));
        availableDrawables.add(getResources().getDrawable(R.drawable.giraffe));
        availableDrawables.add(getResources().getDrawable(R.drawable.jellyfish));
        availableDrawables.add(getResources().getDrawable(R.drawable.gorilla));
        availableDrawables.add(getResources().getDrawable(R.drawable.petfish));
        availableDrawables.add(getResources().getDrawable(R.drawable.seahorse));
        availableDrawables.add(getResources().getDrawable(R.drawable.seal));
        availableDrawables.add(getResources().getDrawable(R.drawable.whale));
        availableDrawables.add(getResources().getDrawable(R.drawable.shark));
        availableDrawables.add(getResources().getDrawable(R.drawable.peacock));
    }

}




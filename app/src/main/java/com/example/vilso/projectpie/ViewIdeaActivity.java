package com.example.vilso.projectpie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import models.RateAndComment;


public class ViewIdeaActivity extends ActionBarActivity implements CommentAdapter.CommentViewHolder.ClickListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private CommentAdapter itemAdapter;
    private Toolbar toolbar;
    private RelativeLayout layout_question;
    private TextView tv_question;
    private Button btn_yes;
    private Button btn_no;
    private String[] questions = new String[]{"Was it a real problem?",
            "Did the idea solve the problem?",
            "Does the idea have a potential for success?",
            "Was the message impacting and convincing?",
            "Was the information simple enough?"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_idea);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        layout_question = (RelativeLayout)findViewById(R.id.layout_question);
        tv_question = (TextView)findViewById(R.id.tv_question);

        itemAdapter = new CommentAdapter(this, getData());
        itemAdapter.setClickListener(this);

        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_yes = (Button)findViewById(R.id.btn_yes);
        btn_no = (Button)findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        YoYo.with(Techniques.FadeOutLeft).duration(500).playOn(tv_question);
        YoYo.YoYoString x= YoYo.with(Techniques.FadeInRight).delay(500).playOn(tv_question);

        while(!x.isStarted()){};

        tv_question.setText("Boom");

        if(v.equals(btn_yes)){

        }else if(v.equals(btn_no)){

        }
    }

    @Override
    public void itemClicked(View view, int position) {
    }

    public static List<RateAndComment> getData(){
        List<RateAndComment> data = new ArrayList();

        for(int i = 0; i < 20; i++){
            data.add(new RateAndComment("username", 3, "no comment"));
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_idea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

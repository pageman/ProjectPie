package com.example.vilso.projectpie;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.example.vilso.projectpie.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import models.IdeaContext;
import models.IdeaItem;
import models.RateAndComment;


public class ViewIdeaActivity extends ActionBarActivity implements CommentAdapter.CommentViewHolder.ClickListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private CommentAdapter itemAdapter;
    private Toolbar toolbar;
    private RelativeLayout layout_question;
    private LayoutInflater layoutInflater;
    private TextView tv_question;
    private TextView tv_title;
    private View sliderView;
    private FloatingActionButton btn_fab;
    private Button btn_yes;
    private Button btn_no;
    private Button btnLike;
    private IdeaItem ideaItem;
    private ParseObject ideaParse;
    private String[] questions = new String[]{"Was it a real problem?",
            "Did the idea solve the problem?",
            "Does the idea have a potential for success?",
            "Was the message impacting and convincing?",
            "Was the information simple enough?"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_idea);

        String objectID = getIntent().getStringExtra("ideaID");
        //Log.e("Object ID", objectID);
        IdeaContext ideaContext = new IdeaContext();
        ideaParse = ideaContext.getIdea(objectID);

        ideaItem = new IdeaItem(ideaParse.getString("title"), 0, ideaContext.getLikeCount(ideaParse), ideaParse.getString("youtube"), ideaParse.getString("status"));
        ideaItem.setObjectId(ideaParse.getObjectId());

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        layout_question = (RelativeLayout)findViewById(R.id.layout_question);


        itemAdapter = new CommentAdapter(getData());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        layoutInflater = LayoutInflater.from(this);
        sliderView = layoutInflater.inflate(R.layout.view_app_idea_header, recyclerView, false);

        itemAdapter = new CommentAdapter(getData());

        itemAdapter.setContext(this);
        itemAdapter.setClickListener(this);
        itemAdapter.setParallaxHeader(sliderView, recyclerView);
//        itemAdapter.setData(getData());
        itemAdapter.implementMethods();

        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_question = (TextView)sliderView.findViewById(R.id.tv_question);
        tv_title = (TextView) sliderView.findViewById(R.id.tv_title);
        tv_title.setText(ideaItem.getTitle());

        btnLike = (Button) sliderView.findViewById(R.id.btn_like);
        btnLike.setText("Like: "+ Integer.toString(ideaContext.getLikeCount(ideaParse)));
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdeaContext ideaContext = new IdeaContext();
                ideaContext.likeIdea(ideaParse, ParseUser.getCurrentUser());
                btnLike.setText("Like: " + Integer.toString(ideaContext.getLikeCount(ideaParse)));
            }
        });

        btn_yes = (Button)sliderView.findViewById(R.id.btn_yes);
        btn_no = (Button)sliderView.findViewById(R.id.btn_no);
        btn_fab = (FloatingActionButton)findViewById(R.id.btn_fab);

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        btn_fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        YoYo.YoYoString x = YoYo.with(Techniques.FadeOutLeft).duration(500).playOn(tv_question);
        YoYo.with(Techniques.FadeInRight).delay(500).playOn(tv_question);

        while(x.isRunning()){};
        tv_question.setText("Boom");

        if(v.equals(btn_yes)){

        }else if(v.equals(btn_no)){

        }else if(v.equals(btn_fab)){
            popup();
        }
    }

    private void popup(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.et_comment);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    @Override
    public void itemClicked(View view, int position) {
    }

    public List<RateAndComment> getData(){
        List<ParseObject> comments = new IdeaContext().getComments(ideaParse);
        List<RateAndComment> data = new ArrayList();

        for(ParseObject comment: comments){
            try {
                data.add(new RateAndComment(ParseUser.getQuery().get(comment.getString("user")).getUsername(), 3, comment.getString("message")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

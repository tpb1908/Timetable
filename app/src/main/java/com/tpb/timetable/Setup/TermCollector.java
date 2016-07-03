package com.tpb.timetable.Setup;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.tpb.timetable.Data.DBHelper;
import com.tpb.timetable.Data.Templates.Term;
import com.tpb.timetable.R;
import com.tpb.timetable.Setup.Adapters.TermAdapter;
import com.tpb.timetable.Setup.Input.TermInput;
import com.tpb.timetable.Utils.UIHelper;

/**
 * Created by theo on 03/07/16.
 */
public class TermCollector extends AppCompatActivity implements DBHelper.ArrayChangeListener<Term> {
    private TermAdapter mTermAdapter;
    private RecyclerView mTermRecycler;
    private StaggeredGridLayoutManager mLayoutManager;
    private int mCurrentRotation;
    private FloatingActionButton mAddTermFAB;
    private FloatingActionButton mDoneFAB;

    private boolean mShouldFinishWhenDone;
    private Class mNextWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_collector);
        final Intent launchIntent = getIntent();
        try {
            mNextWindow = (Class) launchIntent.getSerializableExtra("nextWindow");
            mShouldFinishWhenDone = false;
        } catch(Exception e) {
            mShouldFinishWhenDone = true;
        }

        mTermRecycler = (RecyclerView) findViewById(R.id.recycler_terms);
        mTermAdapter = new TermAdapter(getApplicationContext());
        mTermRecycler.setAdapter(mTermAdapter);
        mCurrentRotation = getResources().getConfiguration().orientation;
        setupLayoutManager();

        mAddTermFAB = (FloatingActionButton) findViewById(R.id.fab_add_term);
        mDoneFAB = (FloatingActionButton) findViewById(R.id.fab_term_add_finish);

        mDoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO- Prompt if no terms entered
                if(mShouldFinishWhenDone) {
                    finish();
                } else {
                    final Intent next = new Intent(TermCollector.this, mNextWindow);
                    startActivity(next);
                }
            }
        });

        mAddTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent newTerm = new Intent(TermCollector.this, TermInput.class);
                UIHelper.setExpandLocation(v, newTerm);
                startActivity(newTerm);
            }
        });
    }

    public void openTerm(Term term, View v) {
        final Intent editTerm = new Intent(TermCollector.this, TermInput.class);
        editTerm.putExtra("term", term);
        UIHelper.setExpandLocation(v, editTerm);
        startActivity(editTerm);
    }

    private void setupLayoutManager() {
        if(mLayoutManager == null) {
            if(mCurrentRotation == Configuration.ORIENTATION_LANDSCAPE && mTermAdapter.numTerms() > 0) {
                mLayoutManager  = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                mLayoutManager =  new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            }
        }
        if(mCurrentRotation == Configuration.ORIENTATION_LANDSCAPE && mTermAdapter.numTerms() > 0) {
            mLayoutManager.setSpanCount(2);
        } else {
            mLayoutManager.setSpanCount(1);
        }
    }

    @Override
    public void removed(int index, final Term term) {
        final CoordinatorLayout snackBarLayout = (CoordinatorLayout) findViewById(R.id.snackbarPosition);
        final Snackbar snackbar = Snackbar
                .make(snackBarLayout, term.getName() + " deleted",Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBHelper.getInstance(getApplicationContext()).getAllTerms().addToPosition(term);
                    }
                });
        snackbar.show();
    }

   //Unused arraychangelistener methods
    @Override
    public void dataSetChanged() {}

    @Override
    public void dataSorted() {}

    @Override
    public void add(Term term) {}

    @Override
    public void add(int index, Term term) {}

    @Override
    public void set(int index, Term term) {}

    @Override
    public void moved(int oldIndex, int newIndex) {}

    @Override
    public void updated(int index, Term term) {}

    @Override
    public void cleared() {}
}

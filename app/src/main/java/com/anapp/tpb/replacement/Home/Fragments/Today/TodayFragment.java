package com.anapp.tpb.replacement.Home.Fragments.Today;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anapp.tpb.replacement.Home.Adapters.TodayClassAdapter;
import com.anapp.tpb.replacement.Home.Interfaces.ClassOpener;
import com.anapp.tpb.replacement.Home.Interfaces.TaskOpener;
import com.anapp.tpb.replacement.R;
import com.anapp.tpb.replacement.Storage.DataHelper;
import com.anapp.tpb.replacement.Storage.TableTemplates.ClassTime;
import com.anapp.tpb.replacement.Storage.TableTemplates.Task;
import com.anapp.tpb.replacement.Storage.TableTemplates.Term;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodayFragment extends Fragment implements TaskOpener, ClassOpener {
    private static final String TAG = "TodayFragment";
    private TaskOpener mTaskInterface;
    private ClassOpener mClassInterface;
    private TodayClassAdapter mClassAdapter;
    private RecyclerView mClassRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private BroadcastReceiver mBroadcastReceiver;
    private DataHelper mDataHelper;
    private TextView mDayTermText;
    private int mCurrentDay;


    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        //Nothing currently needs to be passed
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive (Context context, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    //Simplest way of causing timer bar to update
                    //TODO- Just update the correct viewholder/s
                    mClassAdapter.notifyDataSetChanged();
                }
            }
        };
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mClassInterface = (ClassOpener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ClassOpener interface");
        }

        try {
            mTaskInterface = (TaskOpener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TaskOpener interface");
        }

    }

    @Override
    public void openClass(ClassTime c) {
        mClassInterface.openClass(c);
    }

    @Override
    public void openTask(Task t) {
        mTaskInterface.openTask(t);
    }

    @Override
    public void openReminder(Task r) {
        mTaskInterface.openReminder(r);
    }

    @Override
    public void openHomework(Task h) {
        mTaskInterface.openHomework(h);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.fragment_today_classes, container, false);
        //DataHelper is created here so that the app doesn't force close when it is restarted
        mDataHelper = new DataHelper(getContext());
        mClassRecycler = (RecyclerView) inflated.findViewById(R.id.recycler_class_today);
        mClassAdapter = new TodayClassAdapter(getContext(), this, mDataHelper);
        mLayoutManager = new LinearLayoutManager(getContext());
        mClassRecycler.setAdapter(mClassAdapter);
        mClassRecycler.setLayoutManager(mLayoutManager);
        mDayTermText = (TextView) inflated.findViewById(R.id.text_today_term);
        setDayTermText();
        mCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        return inflated;
    }

    /**
     * Sets the title mMessage to the current day and term
     */
    private void setDayTermText() {
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        SimpleDateFormat date = new SimpleDateFormat("dd-MMMM");
        Date d = new Date();
        String dayTerm = day.format(d) + "  " + date.format(d);
        Term currentTerm = mDataHelper.getCurrentTerm();
        if(currentTerm.getName() != null) {
            dayTerm += " " + currentTerm.getName();
            mDayTermText.setText(dayTerm);
        } else {
            dayTerm += " Holiday";
            mDayTermText.setText(dayTerm);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(mClassAdapter == null) {
            mClassAdapter = new TodayClassAdapter(getContext(), this, mDataHelper);
        }
        mClassAdapter.resume(mDataHelper);
        if(mCurrentDay != today) { //The app has been left overnight
            mCurrentDay = today;
            setDayTermText();
            mClassAdapter.collectData();
        }
        //Updating mClassAdapter and reregistering receiver
        mClassAdapter.notifyDataSetChanged();
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    @Override
    public void onPause() {
        //Broadcast reciever must be unregistered
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }
}
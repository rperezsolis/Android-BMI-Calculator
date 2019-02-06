package com.example.rafael_perez.mi_peso_ideal.Progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafael_perez.mi_peso_ideal.R;

import java.util.ArrayList;
import java.util.Date;

public class ProgressListTabFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListDataCallback listDataCallback;
    private int resultType;

    public static ProgressListTabFragment newInstance(){
    ProgressListTabFragment fragment = new ProgressListTabFragment();
    return fragment;
    }

    public void setListDataCallback(ListDataCallback listDataCallback){
        this.listDataCallback = listDataCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.progress_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listDataCallback.setListData(resultType);
        return view;
    }

    public void setResultType(int resultType){
        this.resultType = resultType;
    }

    public void setProgressData(Context context, ArrayList<Double> values, ArrayList<Date> values_dates) {
        ProgressListAdapter adapter = new ProgressListAdapter(values, values_dates);
        recyclerView.setAdapter(adapter);
    }

    public interface ListDataCallback{
        void setListData(int resultType);
    }
}

package com.example.eamon.hihealth.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.CreateTargetOneActivity;
import com.example.eamon.hihealth.adapter.TargetAdapter;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.TargetInfoMessage;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TargetFirstFragment extends Fragment {

    private List<Target> targetList = new ArrayList<>();

    private RecyclerView targetRecyView;

    private SharedPreferences.Editor editor;

    private Button createBtn;

    private UserInfo userInfo;


    private View view;

    private SharedPreferences pref;

    private Gson gson = new Gson();

    private ProgressDialog progressDialog;


    public TargetFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化我的目标信息
        view = inflater.inflate(R.layout.fragment_target_first, container, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        progressDialog = ProgressDialog.show(getActivity(), "我的目标", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postFindTarget(userInfo);
            }
        }).start();
        createBtn = (Button) view.findViewById(R.id.create_target);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
                Intent intent = new Intent(getContext(), CreateTargetOneActivity.class);
                intent.putExtra("user_data", userInfo);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void postFindTarget(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/info", userInfo, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getActivity(), "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                progressDialog.dismiss();
                Gson gson = new Gson();
                TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                if (("success").equals(targetInfoMessage.responseMessage.result)) {
                    editor = pref.edit();
                    editor.remove("target");
                    editor.putString("target",gson.toJson(targetInfoMessage.ongoingtarget));
                    editor.apply();
                    targetList = targetInfoMessage.targetList;
                    TargetAdapter targetAdapter = new TargetAdapter(targetList);
                    targetRecyView = (RecyclerView) view.findViewById(R.id.target_recycler_view);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    targetRecyView.setLayoutManager(layoutManager);
                    targetRecyView.setAdapter(targetAdapter);
                    Toast.makeText(getActivity(), targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                } else if (("fail").equals(targetInfoMessage.responseMessage.result)) {
                    Toast.makeText(getActivity(), targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    TargetAdapter targetAdapter = new TargetAdapter(targetList);
                    targetRecyView = (RecyclerView) view.findViewById(R.id.target_recycler_view);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    targetRecyView.setLayoutManager(layoutManager);
                    targetRecyView.setAdapter(targetAdapter);
                }
            }
        });

    }

}

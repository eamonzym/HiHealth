package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.CreateTargetFourActivity;
import com.example.eamon.hihealth.activity.TargetActivity;
import com.example.eamon.hihealth.activity.TargetInfoActivity;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.TargetInfoMessage;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/11.
 */

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> {

    private Context mContext;

    private List<Target> mTargetList;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;
    static class ViewHolder extends RecyclerView.ViewHolder {
        SwipeMenuLayout swipeMenuLayout;

        LinearLayout linearLayout;
        TextView targetName;
        TextView targetState;
        TextView targetTime;
        Button deleteBtn;
        Button startBtn;
        Button abandonBtn;
//        Button sureDeleteBtn;

        public ViewHolder(View view) {
            super(view);
            swipeMenuLayout = (SwipeMenuLayout) view;
            linearLayout = (LinearLayout) view.findViewById(R.id.content);
            targetName = (TextView) view.findViewById(R.id.target_name);
            targetState = (TextView) view.findViewById(R.id.target_state);
            targetTime = (TextView) view.findViewById(R.id.target_time);
            deleteBtn = (Button) view.findViewById(R.id.btnDelete);
//            sureDeleteBtn = (Button) view.findViewById(R.id.btnSureDelete);
            startBtn = (Button) view.findViewById(R.id.btnStart);
            abandonBtn = (Button) view.findViewById(R.id.btnAbandon);
        }
    }

    public TargetAdapter(List<Target> targetList) {
        mTargetList = targetList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.target_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Target target = mTargetList.get(position);
                Intent intent = new Intent(mContext, TargetInfoActivity.class);
                intent.putExtra("targetinfo_data", target);
                mContext.startActivity(intent);
            }
        });
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
                postIsStart(userInfo, holder);

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Target target = mTargetList.get(position);
                postDelete(target);
                if ("ongoing".equals(target.getTargetstate())) {
                    editor = pref.edit();
                    editor.remove("target");
                    editor.apply();
                }
                Toast.makeText(mContext, "delete", Toast.LENGTH_LONG).show();
            }
        });
        holder.abandonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Target target = mTargetList.get(position);
                postAbandon(target);
            }
        });
        return holder;
    }

    private void postAbandon(Target target) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/abandon", target, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(mContext, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                if ("success".equals(targetInfoMessage.responseMessage.result)) {
                    Toast.makeText(mContext, targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, TargetActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }
    private void postDelete(Target target) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/delete", target,
                new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(mContext, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                if ("success".equals(targetInfoMessage.responseMessage.result)) {
                    Toast.makeText(mContext, targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, TargetActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }
    private void postIsStart(UserInfo userInfo, final ViewHolder holder) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/isStart", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(mContext, "连接出错" , Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                        if ("success".equals(targetInfoMessage.responseMessage.result)) {
                            Toast.makeText(mContext, targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            int position = holder.getAdapterPosition();
                            Target target = mTargetList.get(position);
                            Intent intent = new Intent(mContext, CreateTargetFourActivity.class);
                            intent.putExtra("targetinfo_data", target);
                            mContext.startActivity(intent);
                        } else if ("fail".equals(targetInfoMessage.responseMessage.result)) {
                            Toast.makeText(mContext, targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Target target  = mTargetList.get(position);
        if ("ongoing".equals(target.getTargetstate())) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.abandonBtn.setVisibility(View.VISIBLE);
            holder.startBtn.setVisibility(View.GONE);
            holder.targetState.setTextColor(Color.parseColor("#00a483"));
            holder.targetState.setText("进行中");
        } else if ("disongoing".equals(target.getTargetstate())) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.abandonBtn.setVisibility(View.GONE);
            holder.startBtn.setVisibility(View.VISIBLE);
            holder.targetState.setTextColor(Color.parseColor("#d1ab57"));
            holder.targetState.setText("未激活");
        } else if ("abandon".equals(target.getTargetstate())) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.abandonBtn.setVisibility(View.GONE);
            holder.startBtn.setVisibility(View.VISIBLE);
            holder.targetState.setTextColor(Color.parseColor("#ff4a57"));
            holder.targetState.setText("已放弃");
        }
        holder.targetName.setText(target.getTargetname());
        holder.targetTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetcreatetime()));

    }

    @Override
    public int getItemCount() {
        return mTargetList.size();
    }

}

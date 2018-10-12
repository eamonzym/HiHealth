package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.ExerciseActivity;
import com.example.eamon.hihealth.db.ExerciseLog;
import com.example.eamon.hihealth.gson.ExerciseLogMessage;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/1.
 */

public class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogAdapter.ViewHolder> {

    private Context mContext;

    private List<ExerciseLog> mExerciseLogList;



    static class ViewHolder extends RecyclerView.ViewHolder {
        SwipeMenuLayout layout;
        TextView exerciseName;
        TextView exerciseCal;
        TextView exerciseTime;
        Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            layout = (SwipeMenuLayout) view;
            exerciseName = (TextView) view.findViewById(R.id.exercise_name);
            exerciseCal = (TextView) view.findViewById(R.id.exercise_cal);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);
            exerciseTime = (TextView) view.findViewById(R.id.exercise_exerciseeffecttime);
        }
    }

    public ExerciseLogAdapter(List<ExerciseLog> exerciseLogList) {
        mExerciseLogList = exerciseLogList;
    }


    @Override
    public ExerciseLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.exercise_log_item,
                parent, false);

        final ExerciseLogAdapter.ViewHolder holder = new ExerciseLogAdapter.ViewHolder(view);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ExerciseLog exerciseLog = mExerciseLogList.get(position);
                postDelete(exerciseLog);
                Toast.makeText(mContext, "delete", Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    private void postDelete(ExerciseLog exerciseLog) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/exercise/delete", exerciseLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(mContext, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        ExerciseLogMessage exerciseLogMessage = gson.fromJson(result, ExerciseLogMessage.class);
                        if ("success".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(mContext, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext, ExerciseActivity.class);
                            mContext.startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(ExerciseLogAdapter.ViewHolder holder, int position) {
        ExerciseLog exerciseLog = mExerciseLogList.get(position);
        holder.exerciseName.setText(exerciseLog.getExercise().getExercisename());
        holder.exerciseCal.setText(exerciseLog.getExercisetotalcalorie().toString());
        holder.exerciseTime.setText(  "千卡 / " + exerciseLog.getExerciseduration() + "分钟");

    }

    @Override
    public int getItemCount() {
        return mExerciseLogList.size();
    }
}

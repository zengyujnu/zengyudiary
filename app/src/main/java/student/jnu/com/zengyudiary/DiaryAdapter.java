package student.jnu.com.zengyudiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends BaseAdapter {
    private List<Diary> diaryList;
    private Context context;

    DiaryAdapter(ArrayList<Diary> diaryList, Context context) {
        this.diaryList=diaryList;
        this.context=context;
    }


    public int getCount() {
        return diaryList.size();
    }


    public int getViewTypeCount() {
        return 3;
    }


    public int getItemViewType(int position) {
        Diary bean = diaryList.get(position);
        return bean.getType();
    }


    public Object getItem(int i) {
        return diaryList.get(i);
    }


    public long getItemId(int i) {
        return i;
    }


    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent){
        final Diary diary=diaryList.get(position);//获取当前项的实例
        ViewHolder1 holder1;
        ViewHolder2 holder2;
        ViewHolder3 holder3;
        if(diary.getDiary_content().length()!=0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.wenbenkuang, null);
            holder1 = new ViewHolder1();
            //AbsListView.LayoutParams param = new AbsListView.LayoutParams(300,68);
            //v.setLayoutParams(param);
            holder1.day_in_week2 = (TextView) convertView.findViewById(R.id.day_in_week);
            holder1.day_in_month2 = (TextView) convertView.findViewById(R.id.day_in_month);
            holder1.diary_content2 = (TextView) convertView.findViewById(R.id.diary_content);
            convertView.setTag(holder1);
            holder1.day_in_week2.setText(diary.getDay_in_week());
            holder1.day_in_month2.setText(Integer.toString(diary.getDay_in_month()));
            holder1.diary_content2.setText(diary.getDiary_content());
            if (diary.getDay_in_week().equals("SUN")) {
                holder1.day_in_month2.setTextColor(Color.RED);
            }
        }
        else if(diary.getDiary_content().length()==0&&diary.getDay_in_week().equals("SUN")) {
            holder2 = new ViewHolder2();
            convertView = LayoutInflater.from(context).inflate(R.layout.hongdian, null);

            holder2.heidian=(ImageView) convertView.findViewById(R.id.heidian);
            convertView.setTag(holder2);
        }
        else {
            holder3=new ViewHolder3();
            convertView=LayoutInflater.from(context).inflate(R.layout.heidian,null);

            holder3.hongdian=(ImageView) convertView.findViewById(R.id.hongdian);
            convertView.setTag(holder3);
        }

        return convertView;
    }

    static class ViewHolder1 {
        TextView day_in_week2;
        TextView day_in_month2;
        TextView diary_content2;
    }

    private class ViewHolder2 {
        ImageView heidian;
    }

    private class ViewHolder3 {
        ImageView hongdian;
    }
}
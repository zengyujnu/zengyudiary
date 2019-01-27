package student.jnu.com.zengyudiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListTextAdapter extends BaseAdapter {
    private List<Diary> diaryList;
    private Context context;

    public ListTextAdapter(ArrayList<Diary> diaryList, Context context) {
        this.diaryList=diaryList;
        this.context=context;
    }


    public int getCount() {
        return diaryList.size();
    }


    public Object getItem(int i) {
        return diaryList.get(i);
    }


    public long getItemId(int i) {
        return i;
    }


    @SuppressLint({"SetTextI18n", "ViewHolder"})
    public View getView(int position, View convertView, ViewGroup parent){
        final Diary diary=diaryList.get(position);//获取当前项的实例
        ViewHolder1 holder1;
            convertView = LayoutInflater.from(context).inflate(R.layout.list_text, null);
            holder1 = new ViewHolder1();
            //AbsListView.LayoutParams param = new AbsListView.LayoutParams(300,68);
            //v.setLayoutParams(param);
            holder1.day_in_week2 = (TextView) convertView.findViewById(R.id.list_text_week);
            holder1.day_in_month2 = (TextView) convertView.findViewById(R.id.list_text_day);
            holder1.diary_content2 = (TextView) convertView.findViewById(R.id.list_text_content);
            convertView.setTag(holder1);
            holder1.day_in_week2.setText(diary.getDay_in_week());
            holder1.day_in_month2.setText(Integer.toString(diary.getDay_in_month()));
            holder1.diary_content2.setText(diary.getDiary_content());
            if (diary.getDay_in_week().equals("SUN")) {
                holder1.day_in_week2.setTextColor(Color.RED);
            }

        return convertView;
    }

    static class ViewHolder1 {
        TextView day_in_week2;
        TextView day_in_month2;
        TextView diary_content2;
    }
}

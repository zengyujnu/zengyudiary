package student.jnu.com.zengyudiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SecondActivity extends AppCompatActivity {

    private Button btn1,btn2;
    private Diary bean;
    private String content = "";
    String str1="";
    String str2="";
    Calendar calendar = Calendar.getInstance();
    //获取系统的日期
//年
    int year = calendar.get(Calendar.YEAR);
    //月
    int months = calendar.get(Calendar.MONTH)+1;
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int week=calendar.get(Calendar.DAY_OF_WEEK);
    //获取系统时间
//小时
    int hour = calendar.get(Calendar.HOUR_OF_DAY)-16;
    //分钟
    int minute = calendar.get(Calendar.MINUTE);
/*//秒
        int second = calendar.get(Calendar.SECOND);
*/

    private EditText edit;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new click1());
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new click2());

        //time1.setText("Calendar获取当前日期"+year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second);
        if(0>hour){
            hour+=24;
        }
        if(8>hour) {
            day++;
            week++;
        }

        String []month ={"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};;
        /*if (months == 1) {
            month= "JANUARY";
        }
        if (months == 2) {
            month= "FEBRUARY";
        }
        if (months == 3) {
            month= "MARCH";
        }
        if (months == 4) {
            month= "APRIL";
        }
        if (months == 5) {
            month= "MAY";
        }
        if (months == 6) {
            month= "JUNE";
        }
        if (months == 7) {
            month= "JULY";
        }
        if (months == 8) {
            month= "AUGUST";
        }
        if (months == 9) {
            month= "SEPTEMBER";
        }
        if (months == 10) {
            month= "OCTOBER";
        }
        if (months == 11) {
            month= "NOVEMBER";
        }
        if (months == 12) {
            month= "DECEMBER";
        }*/

        bean = new Diary();
        bean= (Diary) getIntent().getSerializableExtra("text1");
        String Week = "";
        switch (bean.getDay_in_week()){
            case "SUN":
                Week = "SUNDAY";
                break;
            case "MON":
                Week = "MONDAY";
                break;
            case "TUE":
                Week = "TUESDAY";
                break;
            case "WED":
                Week = "WEDNESDAY";
                break;
            case "THU":
                Week = "THURSDAY";
                break;
            case "FRI":
                Week = "FRIDAY";
                break;
            case "SAT":
                Week = "SATURDAY";
                break;
        }
        TextView time1 = (TextView) findViewById(R.id.time);
        SpannableStringBuilder builder = new SpannableStringBuilder(Week+"/"+bean.getMONTH()+" "+bean.getDay_in_month()+"/"+bean.getYEAR());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        if(Week.equals("SUNDAY"))
            builder.setSpan(redSpan,0,Week.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        time1.setText(builder);//输出系统时间
        //time1.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );//下划线
        edit=(EditText) findViewById(R.id.diary_content_editor);
        TextView editText= (TextView)  findViewById(R.id.diary_content_editor );
        editText.setText(bean.getDiary_content());
    }

   /* @Override
    protected void onDestroy(){
        super.onDestroy();
        String inputText=edit.getText().toString();
        xuliehua.save(inputText);
    }*/

    class click2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            content = edit.getText().toString();
            if (content.length()!=0) {

                System.out.println(content);

                bean.setDiary_content(content);
                bean.setType(1);
            }else {
                bean.setDiary_content("");
                bean.setType(0);
            }
            ArrayList<Diary> s  = (ArrayList<Diary>) xuliehua.load(SecondActivity.this,bean.getYEAR()+bean.getMONTH()+".dat");
            assert s != null;
            s.set(bean.getDay_in_month()-1,bean);
            xuliehua.save(SecondActivity.this,s,bean.getYEAR()+bean.getMONTH()+".dat");
            Intent intent=new Intent();
            intent.setClass(SecondActivity.this,MainActivity.class);
            /*Bundle bundle=new Bundle();
            bundle.putString("t1", bean.getYEAR());
            bundle.putString("t2", bean.getMONTH());
            intent.putExtras(bundle);*/
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    class click1 implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {
           EditText txt=(EditText)findViewById(R.id.diary_content_editor);
            Bundle bundle=new Bundle();
            bundle.putString("text1",txt.getText().toString());
            str2=bundle.getString("text1");
            if(minute<10){
                str1=hour+":0"+minute+"   ";//获取当前时间
            }
            else {
                str1 = hour + ":" + minute + "   ";//获取当前时间
            }
           txt.setText(str2+str1);
           txt.setSelection((str2+str1).length());
        }
    }
}
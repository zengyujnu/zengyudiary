package student.jnu.com.zengyudiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_ITEM = 10;
    public int flag = 0;
    public ArrayList<Diary> diaryList=new ArrayList<Diary>();
    List<Day> dayList;
    DiaryAdapter diaryAdapter;
    ListTextAdapter listTextAdapter;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    int j,n,m;
    RecyclerView recyclerView;
    ListView list;
    Calendar calendar = Calendar.getInstance();
    //获取系统的日期
//年
    int year = calendar.get(Calendar.YEAR);
    //月
    int months = calendar.get(Calendar.MONTH)+1;
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int week=calendar.get(Calendar.DAY_OF_WEEK);
    //小时
    int hour = calendar.get(Calendar.HOUR_OF_DAY)-16;

    //获取系统时间
    String[] Week={"MON","TUE","WED","THU","FRI","SAT","SUN"};
    int[] l={31,28,31,30,31,30,31,31,30,31,30,31};
    String[] Month1={"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    String[] Month={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    //String[] Year={"2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050"};
    //int[] year1={2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039,2040,2041,2042,2043,2044,2045,2046,2047,2048,2049,2050};
    LinearLayout allbutton;

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if(!diaryList.get(((AdapterView.AdapterContextMenuInfo) menuInfo).position).getDiary_content().equals("")) {
            menu.setHeaderTitle("Diary");
            for (m = 0; m < 12; ) {
                if (!Month1[m].equals(String.valueOf(btn1.getText())))
                    m++;
                else {
                    m++;
                    break;
                }
            }
            menu.add(0, 1, 0, "删除" + String.valueOf(btn2.getText()) + "年" + m + "月" + diaryList.get(((AdapterView.AdapterContextMenuInfo) menuInfo).position).getDay_in_month() + "日的日记");
            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                ArrayList<Diary> s  = (ArrayList<Diary>) xuliehua.load(MainActivity.this,String.valueOf(btn2.getText())+String.valueOf(btn1.getText())+".dat");
                assert s != null;
                int i=diaryList.get(itemInfo.position).getDay_in_month();
                s.get(i-1).setDiary_content("");
                xuliehua.save(MainActivity.this,s,String.valueOf(btn2.getText())+String.valueOf(btn1.getText())+".dat");
                //diaryAdapter.removeItem(itemInfo.position);
                if (flag == 1) {
                    // 第一次单击触发的事件
                    diaryList.clear();
                    diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                    listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                    listTextAdapter.notifyDataSetChanged();
                    list.setAdapter(listTextAdapter);
                } else {
                    // 第二次单击buttont改变触发的事件
                    diaryList.clear();
                    diaryList.addAll(getdata(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                    diaryAdapter.notifyDataSetChanged();
                    list.setAdapter(diaryAdapter);
                }
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(0>hour){
            hour+=24;
        }
        if(8>hour) {
            day++;
            week++;
        }
        allbutton = (LinearLayout)findViewById(R.id.allbutton);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new click1());
        btn1.setText(Month1[months-1]);
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new click2());
        btn2.setText(String.valueOf(year));
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new click3());
        btn4=(Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new click4());
        /*for(n=0;n<12;){
            if(("||"+Month1[n])!=btn1.getText())
                n++;
        }*/
        diaryList= getdata(String.valueOf(year),Month1[months-1]);
        //xuliehua operater=new xuliehua();
       // diaryList=operater.load(MainActivity.this,year+months+".dat");
        list=(ListView)findViewById(R.id.diary);
        //initDiary();
        list.setOnItemClickListener(new mItemClick());
        diaryAdapter=new DiaryAdapter(diaryList,MainActivity.this);
        diaryAdapter.notifyDataSetChanged();
        list.setAdapter(diaryAdapter);
        recyclerView=(RecyclerView) findViewById(R.id.recycle);
//        initDay1();//初始化
        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        MonthAdapter monthadapter=new MonthAdapter(dayList);
        recyclerView.setAdapter(monthadapter);
        recyclerView.setVisibility(View.GONE);
        //diaryList.clear();
        //diaryList.addAll(diaryList);
        //diaryAdapter.notifyDataSetChanged();
        registerForContextMenu(list);
    }

    public int getWeek(String YEAR, String MONTH, int day) {
        int a,c,w;
        /*for(n=0;n<12;){
            if(!Year[n].equals(YEAR))
                n++;
            else {
                n+=2009;
                break;
            }
        }*/
        n=Integer.parseInt(YEAR);
        for(m=0;m<12;){
            if(!Month1[m].equals(MONTH))
                m++;
            else {
                m++;
                break;
            }
        }
        if(m>=3) {
            a = n % 100;
            c = n/ 100;
        }
        else{
            m+=12;
            a=(n-1) % 100;
            c = (n-1)/ 100;
        }
        w = a + (a / 4) + (c / 4) - 2 * c + (26 * (m + 1) / 10) + day - 1;
        if(w<0)
            j=7 - (-w) % 7;
        else
            j=w%7;
        if(j==7)
            j-=7;
        return j;
    }

    public ArrayList<Diary> getdata1(String YEAR,String MONTH) {
        ArrayList<Diary> data =   new ArrayList<Diary>();
        Diary data1;
        ArrayList<Diary> s  = (ArrayList<Diary>)xuliehua.load(MainActivity.this,YEAR+MONTH+".dat");
        if(s!=null) {
            for (int i = 0; i < s.size(); i++) {
                data1 = s.get(i);
                if (!data1.getDiary_content().equals("")) {
                    data.add(data1);
                }
            }
        }
        return data;
    }

    public ArrayList<Diary> getdata(String YEAR,String MONTH) {
        ArrayList<Diary> data =   new ArrayList<Diary>();
        Diary data1;
        int u;
        for(u=0;u<12;){
            if(!Month1[u].equals(MONTH))
                u++;
            else {
                u++;
                break;
            }
        }
        ArrayList<Diary> s  = (ArrayList<Diary>)xuliehua.load(MainActivity.this,YEAR+MONTH+".dat");
        if(YEAR.equals(String.valueOf(year))&&MONTH.equals(Month1[months-1])) {
            if (null != s) {
            data = s;
            //return data;
        }
        for (int i = data.size(); i < day; i++) {
            data1 = new Diary();
            j=getWeek(YEAR,MONTH,i);
            data1.setType(0);
            data1.setYEAR(YEAR);
            data1.setMONTH(MONTH);
            data1.setDay_in_week(Week[j]);
            data1.setDay_in_month(i + 1);
            data1.setDiary_content("");
            data.add(data1);
        }
    }
        else if(YEAR.equals(String.valueOf(year))&&u<months&&u!=2) {
            if (null != s) {
                data = s;
                //return data;
            }
            for (int i = data.size(); i < l[u-1]; i++) {
                data1 = new Diary();
                j=getWeek(YEAR,MONTH,i);
                data1.setType(0);
                data1.setYEAR(YEAR);
                data1.setMONTH(MONTH);
                data1.setDay_in_week(Week[j]);
                data1.setDay_in_month(i + 1);
                data1.setDiary_content("");
                data.add(data1);
            }
        }
        else if(!YEAR.equals(String.valueOf(year))&&u!=2){
            if (null != s) {
                data = s;
                //return data;
            }
            for (int i = data.size(); i < l[u-1]; i++) {
                data1 = new Diary();
                j=getWeek(YEAR,MONTH,i);
                data1.setType(0);
                data1.setYEAR(YEAR);
                data1.setMONTH(MONTH);
                data1.setDay_in_week(Week[j]);
                data1.setDay_in_month(i + 1);
                data1.setDiary_content("");
                data.add(data1);
            }
        }
        else if(YEAR.equals(String.valueOf(year))&&u<months&&u==2){
            if(n%4==0){
                if (null != s) {
                    data = s;
                    //return data;
                }
                for (int i = data.size(); i < 29; i++) {
                    data1 = new Diary();
                    j=getWeek(YEAR,MONTH,i);
                    data1.setType(0);
                    data1.setYEAR(YEAR);
                    data1.setMONTH(MONTH);
                    data1.setDay_in_week(Week[j]);
                    data1.setDay_in_month(i + 1);
                    data1.setDiary_content("");
                    data.add(data1);
                }
            }
            else{
                if (null != s) {
                    data = s;
                    //return data;
                }
                for (int i = data.size(); i < 28; i++) {
                    data1 = new Diary();
                    j=getWeek(YEAR,MONTH,i);
                    data1.setType(0);
                    data1.setYEAR(YEAR);
                    data1.setMONTH(MONTH);
                    data1.setDay_in_week(Week[j]);
                    data1.setDay_in_month(i + 1);
                    data1.setDiary_content("");
                    data.add(data1);
                }
            }
        }
        else if(!YEAR.equals(String.valueOf(year))&&u==2){
            if(n%4==0){
                if (null != s) {
                    data = s;
                    //return data;
                }
                for (int i = data.size(); i < 29; i++) {
                    data1 = new Diary();
                    j=getWeek(YEAR,MONTH,i);
                    data1.setType(0);
                    data1.setYEAR(YEAR);
                    data1.setMONTH(MONTH);
                    data1.setDay_in_week(Week[j]);
                    data1.setDay_in_month(i + 1);
                    data1.setDiary_content("");
                    data.add(data1);
                }
            }
            else{
                if (null != s) {
                    data = s;
                    //return data;
                }
                for (int i = data.size(); i < 28; i++) {
                    data1 = new Diary();
                    j=getWeek(YEAR,MONTH,i);
                    data1.setType(0);
                    data1.setYEAR(YEAR);
                    data1.setMONTH(MONTH);
                    data1.setDay_in_week(Week[j]);
                    data1.setDay_in_month(i + 1);
                    data1.setDiary_content("");
                    data.add(data1);
                }
            }
        }
        //diaryAdapter.notifyDataSetChanged();
        xuliehua.save(this,data,YEAR+MONTH+".dat");
        //diaryAdapter.notifyDataSetChanged();
        return data;
    }

    private void initDay1(){
        for(int i=0;i<12;i++){
            Day day=new Day(Month[i]);
            dayList.add(day);
        }
    }

    private void initDay2(){
        for(int i=0;(2009+i)<=year;i++){
            Day day1=new Day(String.valueOf(2009+i));
            dayList.add(day1);
        }
    }

    /*private void initDiary() {
        for(int i=0;i<day;i++){
            j=week-(day-i)%7;
            if(j<=0) {
                j += 7;
            }
           //Diary diary1=new Diary(Week[j-1],i+1,"");
           //diaryList.add(diary1);
        }
    }*/

    class mItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,SecondActivity.class);
            Bundle bundle =new Bundle();
            bundle.putSerializable("text1",diaryList.get(i));
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
        }
    }

    class click1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dayList=new ArrayList<>();
            recyclerView=(RecyclerView) findViewById(R.id.recycle);
            initDay1();//初始化
            LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            MonthAdapter monthadapter=new MonthAdapter(dayList);
            recyclerView.setAdapter(monthadapter);
            allbutton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class click2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dayList=new ArrayList<>();
            recyclerView=(RecyclerView) findViewById(R.id.recycle);
            initDay2();//初始化
            LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            YearAdapter yearadapter=new YearAdapter(dayList);
            recyclerView.setAdapter(yearadapter);
            allbutton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class click3 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,SecondActivity.class);
            Bundle bundle =new Bundle();
            ArrayList<Diary> s  = (ArrayList<Diary>) xuliehua.load(MainActivity.this,String.valueOf(year)+Month1[months-1]+".dat");
            assert s != null;
            bundle.putSerializable("text1",s.get(day-1));
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
        }
    }

    class click4 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (flag == 0) {
                // 第一次单击触发的事件
                flag = 1;
                diaryList.clear();
                diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                listTextAdapter.notifyDataSetChanged();
                list.setAdapter(listTextAdapter);
            } else {
                // 第二次单击buttont改变触发的事件
                flag = 0;
                diaryList.clear();
                diaryList.addAll(getdata(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                diaryAdapter.notifyDataSetChanged();
                list.setAdapter(diaryAdapter);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK){
                    /*Bundle bundle=this.getIntent().getExtras();
                    assert bundle != null;
                    String txt1=bundle.getString("t1");
                    String txt2=bundle.getString("t2");*/
                    if (flag == 1) {
                        // 第一次单击触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                        listTextAdapter.notifyDataSetChanged();
                        list.setAdapter(listTextAdapter);
                    } else {
                        // 第二次单击buttont改变触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        diaryAdapter.notifyDataSetChanged();
                        list.setAdapter(diaryAdapter);
                    }
                    /*diaryList.clear();
                    diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                    listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                    listTextAdapter.notifyDataSetChanged();*/
                }
                break;
        }
    }

    public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

        private List<Day> mdayList;
        int adapterPosition;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView day;

            public ViewHolder(View view){
                super(view);
                day =(TextView)view.findViewById(R.id.day);
            }
        }
        public MonthAdapter(List<Day> dayList){
            mdayList =dayList;
        }
        @NonNull
        @Override
        public MonthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day,parent,false);//这里设置parent可能会出现单个item占满一个页面的情况
            final MonthAdapter.ViewHolder holder=new MonthAdapter.ViewHolder(view);
            //设置子项的点击事件
            holder.day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回此ViewHolder表示的项目的适配器位置
                    adapterPosition = holder.getAdapterPosition();
                    //Day day = mdayList.get(adapterPosition);
                    btn1.setText(Month1[adapterPosition]);
                    recyclerView.setVisibility(View.GONE);
                    allbutton.setVisibility(View.VISIBLE);
                    if (flag == 1) {
                        // 第一次单击触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                        listTextAdapter.notifyDataSetChanged();
                        list.setAdapter(listTextAdapter);
                    } else {
                        // 第二次单击buttont改变触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        diaryAdapter.notifyDataSetChanged();
                        list.setAdapter(diaryAdapter);
                    }
                    //MainActivity.diaryList= MainActivity.getdata(String.valueOf(MainActivity.btn1.getText()),String.valueOf(MainActivity.Month1[adapterPosition]));
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MonthAdapter.ViewHolder holder, int position) {
            Day day= mdayList.get(position);
            holder.day.setText(day.getDay());
        }

        @Override
        public int getItemCount() {
            return mdayList.size();
        }
    }

    public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

        private List<Day> mdayList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView day;

            public ViewHolder(View view){
                super(view);
                day =(TextView)view.findViewById(R.id.day);
            }
        }
        public YearAdapter(List<Day> dayList){
            mdayList =dayList;
        }
        @Override
        public YearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day,parent,false);//这里设置parent可能会出现单个item占满一个页面的情况
            final YearAdapter.ViewHolder holder=new YearAdapter.ViewHolder(view);
            //设置子项的点击事件
            holder.day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回此ViewHolder表示的项目的适配器位置
                    int adapterPosition = holder.getAdapterPosition();
                    //Day day = mdayList.get(adapterPosition);
                    btn2.setText(String.valueOf(2009+adapterPosition));
                    recyclerView.setVisibility(View.GONE);
                    allbutton.setVisibility(View.VISIBLE);
                    if (flag == 1) {
                        // 第一次单击触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata1(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        listTextAdapter=new ListTextAdapter(diaryList,MainActivity.this);
                        listTextAdapter.notifyDataSetChanged();
                        list.setAdapter(listTextAdapter);
                    } else {
                        // 第二次单击buttont改变触发的事件
                        diaryList.clear();
                        diaryList.addAll(getdata(String.valueOf(btn2.getText()),String.valueOf(btn1.getText())));
                        diaryAdapter.notifyDataSetChanged();
                        list.setAdapter(diaryAdapter);
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(YearAdapter.ViewHolder holder, int position) {
            Day day= mdayList.get(position);
            holder.day.setText(day.getDay());
        }

        @Override
        public int getItemCount() {
            return mdayList.size();
        }
    }
}
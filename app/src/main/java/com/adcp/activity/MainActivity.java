package com.adcp.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adcp.R;

import java.util.Date;
/*
通过 给定的uri访问，数据库
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insert,
            query,
            querys,
            modify,
            delete;
    Uri uri = Uri.parse("content://hb.android.contentProvider/teacher");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        Init();
        setListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.insert:
                insertToContentProvider();
                break;
            case R.id.query:
                queryFromContentProvider();
                break;
            case R.id.querys:
                querysFromContentProvider();
                break;
            case R.id.modify:
                updateToContentProvider();
                break;
            case R.id.delete:
                deleteFromContentProvider();
                break;
        }
    }

    private void queryFromContentProvider(){
        ContentResolver cr = getContentResolver();
        // 查找id为1的数据
        Cursor c = cr.query(uri, null, "_ID=?", new String[] { "1" }, null);
        //这里必须要调用 c.moveToFirst将游标移动到第一条数据,不然会出现index -1 requested , with a size of 1错误；cr.query返回的是一个结果集。
        if (c.moveToFirst() == false) {
            // 为空的Cursor
            return;
        }
        int name = c.getColumnIndex("name");
        System.out.println(c.getString(name));
        //输出jiaoshi这代表查询数据成功
        c.close();
    }
    private void insertToContentProvider(){
        ContentResolver cr = getContentResolver();

        ContentValues cv = new ContentValues();
        cv.put("title", "jiaoshou");
        cv.put("name", "jiaoshi");
        cv.put("sex", true);
        Uri uri2 = cr.insert(uri, cv);
        System.out.println(uri2.toString());
        //若返回content://hb.android.contentProvider/teacher/1 这代表插入数据库成功
    }
    private void querysFromContentProvider(){
        ContentResolver cr = getContentResolver();
        // 查找id为1的数据
        Cursor c = cr.query(uri, null, null,null, null);
        System.out.println(c.getCount());
        //输出1这代表查询数据成功
        c.close();
    }
    private void deleteFromContentProvider(){
        ContentResolver cr = getContentResolver();
        //删除指定id的记录 返回0代表数据库中没有这条记录 返回1代表存在这条数据
         int count = cr.delete(uri, "_ID=?", new String[]{"2"});
        System.out.println("delete"+":"+count);
    }
    /*插入数据向ContentProvider*/
    private void updateToContentProvider(){
        ContentResolver cr = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("name", "huangbiao");
        cv.put("date_added", (new Date()).toString());
        //更新指定id的name字段的值 返回0代表数据库中没有这条记录 返回1代表存在这条数据
        int uri2 = cr.update(uri, cv, "_ID=?", new String[]{"1"});
        System.out.println("updated"+":"+uri2);
    }
    /*界面的初始化工作*/
    private void Init() {

    }

    /*为控件设置事件监听*/
    private void setListener() {
         insert.setOnClickListener(this);
         query.setOnClickListener(this);
        querys.setOnClickListener(this);
        modify.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    /*实例化布局文件的控件*/
    private void findViewById() {
        insert=(Button)findViewById(R.id.insert);
        query=(Button)findViewById(R.id.query);
        querys=(Button)findViewById(R.id.querys);
        modify=(Button)findViewById(R.id.modify);
        delete=(Button)findViewById(R.id.delete);
    }
}

package com.anhthi.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongviec;
    ArrayList<Congviec> congviecArrayList;
    CongviecAdapter congviecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Tao bang database
        database.QueryData("CREATE TABLE IF NOT EXISTS Congviec(id INTEGER PRIMARY KEY AUTOINCREMENT, tenCV VARCHAR(200))");

        // insert data
        //database.QueryData("INSERT INTO Congviec VALUES(null, 'Viet ung dung ghi chu')");

        // select data
        getDataCongviec();

    }

    private void getDataCongviec() {
        Cursor dataCongviec = database.getData("SELECT * FROM Congviec");
        congviecArrayList.clear();
        while (dataCongviec.moveToNext()){
            int id = dataCongviec.getInt(0);
            String ten = dataCongviec.getString(1);
            congviecArrayList.add(new Congviec(id, ten));
        }
        congviecAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            dialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogXoa(final int id, final String ten) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắn chắn xóa công việc "+ ten +" không?");

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM Congviec WHERE id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã xóa " + ten, Toast.LENGTH_SHORT).show();
                getDataCongviec();
            }
        });

        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogXoa.show();
    }

    public void dialogSua(final int id, final String ten){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suacongviec);

        final EditText edtTen = dialog.findViewById(R.id.edtSuaCongViec);
        Button btnXacnhan = dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        edtTen.setText(ten);

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTen.getText().toString().trim();
                database.QueryData("UPDATE Congviec SET tenCV = '"+ tenMoi +"' WHERE id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongviec();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dialogThem() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themcongviec);

        final EditText edtTen = dialog.findViewById(R.id.edtThemCongViec);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                if(tencv.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO Congviec VALUES(null, '" + tencv + "')");
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataCongviec();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void init() {
        database = new Database(MainActivity.this, "ghichu.sqlite", null, 1);
        lvCongviec = findViewById(R.id.lvCongviec);
        congviecArrayList = new ArrayList<>();
        congviecAdapter = new CongviecAdapter(MainActivity.this, congviecArrayList);
        lvCongviec.setAdapter(congviecAdapter);
    }

}

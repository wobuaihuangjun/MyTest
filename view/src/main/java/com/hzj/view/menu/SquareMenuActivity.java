package com.hzj.view.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hzj.view.R;

public class SquareMenuActivity extends Activity implements OnMenuClickListener {

    private static final String TAG = "SquareMenu-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_menu);

        SquareMenu mSquareMenu = (SquareMenu) findViewById(R.id.sm);
        mSquareMenu.setOnMenuClickListener(this);
    }

    @Override
    public void onMenuOpen() {
        Toast.makeText(this, "Open", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuClose() {
        Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMenu1() {
        Toast.makeText(this, "Menu1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMenu2() {
        Toast.makeText(this, "Menu2", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMenu3() {
        Toast.makeText(this, "Menu3", Toast.LENGTH_SHORT).show();
    }

}

package com.pro516.thrifttogether.ui.home.activity.movie;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.widget.MovieSeatTable;

/**
 * 选取电影座位
 */
public class ChooseMovieSeatTableActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_movie_seat_table;
    }

    @Override
    protected void init() {
        initToolbar();
        initSeatView();
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        AppCompatTextView confirmTv = findViewById(R.id.common_toolbar_function_right);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("环球时代影城");
        backImgBtn.setOnClickListener(this);
        confirmTv.setOnClickListener(this);
    }

    private void initSeatView() {
        MovieSeatTable movieSeatTable = findViewById(R.id.movie_seat_view);
        movieSeatTable.setScreenName("1号厅");
        movieSeatTable.setMaxSelected(3);

        movieSeatTable.setSeatChecker(new MovieSeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                return column != 2;
            }

            @Override
            public boolean isSold(int row, int column) {
                return row == 6 && column == 6;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        movieSeatTable.setData(8, 15);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                finish();
                break;
            default:
                break;
        }
    }
}

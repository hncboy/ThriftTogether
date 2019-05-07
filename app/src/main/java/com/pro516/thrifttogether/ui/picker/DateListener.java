package com.pro516.thrifttogether.ui.picker;

public interface DateListener {
    void setYear(String year);

    void setMonth(String month);

    void setDay(String day);

    void setMouthDate(String year, String month);

    void setYearDate(String year, String month, String day);

}
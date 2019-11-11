package com.arnesfield.school.mp10;

/**
 * Created by User on 06/07.
 */

public final class TaskConfig {
    public static final String HTTP_HOST = "192.168.1.10";
    public static final String DIR_URL = "http://" + HTTP_HOST + "/excluded/sites/android/fetch-activity/";
    public static final String FETCH_URL = DIR_URL + "data.php";
    public static final String CHART_URL = DIR_URL + "chart.php";
    public static final String ADD_URL = DIR_URL + "add.php";
    public static final String UPDATE_URL = DIR_URL + "update.php";
    public static final String DELETE_URL = DIR_URL + "delete.php";
}

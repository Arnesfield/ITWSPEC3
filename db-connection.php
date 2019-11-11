<?php
$host = "localhost";
$username = "root";
$password = "";
$dbname = "android_db_fetch_activity";

$conn = mysqli_connect($host, $username, $password, $dbname);
$conn_obj = new mysqli($host, $username, $password, $dbname);
?>
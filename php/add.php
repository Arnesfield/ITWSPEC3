<?php
require_once('db-connection.php');

$query = "
  INSERT INTO items(name, description, price, rating)
  VALUES(?, ?, ?, ?);
";

$stmt = $conn_obj->prepare($query);
$stmt->bind_param("ssdd", $name, $desc, $price, $rating);

$name = $_POST['name'];
$desc = $_POST['desc'];
$price = $_POST['price'];
$rating = $_POST['rating'];

$stmt->execute();
$stmt->close();

mysqli_close($conn);
$conn_obj->close();

?>
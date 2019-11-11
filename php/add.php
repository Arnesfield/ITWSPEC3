<?php
require_once('db-connection.php');

$query = "
  INSERT INTO items(name, description, price)
  VALUES(?, ?, ?);
";

$stmt = $conn->prepare($query);
$stmt->bind_param("ssd", $name, $desc, $price);

$name = $_POST['name'];
$desc = $_POST['desc'];
$price = $_POST['price'];

$stmt->execute();
$stmt->close();

$conn->close();

?>
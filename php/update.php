<?php
require_once('db-connection.php');

$query = "
  UPDATE items
  SET
    name = ?,
    description = ?,
    price = ?,
    rating = ?
  WHERE id = ?;
";

$stmt = $conn_obj->prepare($query);
$stmt->bind_param("ssddi", $name, $desc, $price, $rating, $id);

$name = $_POST['name'];
$desc = $_POST['desc'];
$price = $_POST['price'];
$rating = $_POST['rating'];
$id = $_POST['id'];

$stmt->execute();
$stmt->close();

mysqli_close($conn);
$conn_obj->close();

?>
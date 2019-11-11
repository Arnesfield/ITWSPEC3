<?php
require_once('db-connection.php');

$query = "
  UPDATE items
  SET
    name = ?,
    description = ?,
    price = ?
  WHERE id = ?;
";

$stmt = $conn->prepare($query);
$stmt->bind_param("ssdi", $name, $desc, $price, $id);

$name = $_POST['name'];
$desc = $_POST['desc'];
$price = $_POST['price'];
$id = $_POST['id'];

$stmt->execute();
$stmt->close();

$conn->close();

?>
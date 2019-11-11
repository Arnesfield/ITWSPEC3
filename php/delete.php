<?php
require_once('db-connection.php');

$query = "DELETE FROM items WHERE id = ?;";

$stmt = $conn->prepare($query);
$stmt->bind_param("i", $id);

$id = $_POST['id'];

$stmt->execute();
$stmt->close();

$conn->close();

?>
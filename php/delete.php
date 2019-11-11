<?php
require_once('db-connection.php');

$query = "DELETE FROM items WHERE id = ?;";

$stmt = $conn_obj->prepare($query);
$stmt->bind_param("i", $id);

$id = $_POST['id'];

$stmt->execute();
$stmt->close();

mysqli_close($conn);
$conn_obj->close();

?>
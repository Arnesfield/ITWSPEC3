<?php
require_once('db-connection.php');

$query = "SELECT * FROM items";
$result = $conn->query($query);

$array['items'] = array();
while ($row = mysqli_fetch_array($result)) {
  array_push($array['items'], array(
    'id' => $row[0],
    'name' => $row[1],
    'desc' => $row[2],
    'price' => $row[3],
    'image_path' => $row[4]
  ));
}

echo json_encode($array);
$conn->close();
?>
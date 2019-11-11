<?php
require_once('db-connection.php');

$sql = "SELECT * FROM items";
$result = mysqli_query($conn, $sql);

$array['items'] = array();
while ($row = mysqli_fetch_array($result)) {
  array_push($array['items'], array(
    'id' => $row[0],
    'name' => $row[1],
    'desc' => $row[2],
    'price' => $row[3],
    'rating' => $row[4],
    'image_resource' => $row[5]
  ));
}

echo json_encode($array);

mysqli_close($conn);
?>
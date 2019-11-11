<?php
require_once('db-connection.php');

// get largest
$sql = "
  SELECT name, price FROM items
  ORDER BY price DESC
";

$result = mysqli_query($conn, $sql);

$array['items'] = array();
while ($row = mysqli_fetch_array($result)) {
  array_push($array['items'], array(
    'name' => $row[0],
    'price' => $row[1]
  ));
}

?>

<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!--Load the AJAX API-->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

  // Load the Visualization API and the corechart package.
  google.charts.load('current', {'packages':['corechart']});

  // Set a callback to run when the Google Visualization API is loaded.
  google.charts.setOnLoadCallback(drawChart);

  // Callback that creates and populates a data table,
  // instantiates the pie chart, passes in the data and
  // draws it.
  function drawChart() {

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Item Name');
    data.addColumn('number', 'Prices');
    data.addRows([
      <?php
        foreach ($array['items'] as $item)
          echo '[\'' . $item['name'] . '\', ' . $item['price'] . '],';
      ?>
    ]);

    // Set chart options
    var options = {
      'title':'Most to Least Expensive Items'
    };
    
    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
    chart.draw(data, options);
    
    $('rect').attr('fill', '#eee');
  }
</script>

<style>
body {
  background-color: #eee;
}
</style>

</head>
<body>

  <!--Div that will hold the pie chart-->
  <div id="chart_div"></div>
  
  
</body>
</html>
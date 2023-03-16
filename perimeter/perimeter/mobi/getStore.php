<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM store order by reg_date desc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
//id,category,type,image,quantity,price,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['image'] = $row['image'];
            $index['quantity'] = $row['quantity'];
            $index['price'] = $row['price'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No posted bids";
        echo json_encode($results);
    }
    echo json_encode($results);
}
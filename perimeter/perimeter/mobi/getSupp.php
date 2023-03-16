<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM stock where status='Pending' order by reg_date desc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
//stock_id,pur_id,category,type,quantity,price,description,image,supplier,status,pay,reg_date//getSupp
        while ($row = mysqli_fetch_array($response)) {
            $index['stock_id'] = $row['stock_id'];
            $index['pur_id'] = $row['pur_id'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['quantity'] = $row['quantity'];
            $index['price'] = $row['price'];
            $index['description'] = $row['description'];
            $index['image'] = $row['image'];
            $index['supplier'] = $row['supplier'];
            $index['status'] = $row['status'];
            $index['pay'] = $row['pay'];
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
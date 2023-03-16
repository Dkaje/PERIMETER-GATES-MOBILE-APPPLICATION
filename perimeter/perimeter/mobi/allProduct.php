<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM product order by reg_date desc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //product_id,category,description,product_image,price,quantity,reg_date,product_id
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['description'] = $row['description'];
            $index['image'] = $row['image'];
            $index['qty'] = $row['qty'];
            $index['quantity'] = $row['quantity'];
            $index['price'] = $row['price'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No Product";
        echo json_encode($results);
    }
    echo json_encode($results);
}
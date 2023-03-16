<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $serial = $_POST["serial"];
    $query = "SELECT * FROM cart where serial='$serial' order by reg_date desc";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //reg,serial,cust_id,product,category,price,quantity,image,status,reg_date
        while ($row = mysqli_fetch_array($response)) {
            $index['reg'] = $row['reg'];
            $index['serial'] = $row['serial'];
            $index['cust_id'] = $row['cust_id'];
            $index['product'] = $row['product'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['price'] = $row['price'];
            $index['quantity'] = $row['quantity'];
            $index['image'] = $row['image'];
            $index['status'] = $row['status'];
            $index['reg_date'] = $row['reg_date'];
            array_push($results['victory'], $index);
        }
    } else {

        $results['trust'] = 0;
        $results['mine'] = "No Item";
        echo json_encode($results);
    }
    echo json_encode($results);
}
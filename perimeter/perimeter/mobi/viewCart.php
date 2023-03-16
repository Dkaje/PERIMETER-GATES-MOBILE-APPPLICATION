<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $cust_id = $_POST["cust_id"];
    $query = "SELECT * FROM cart where status='Pending' and serial='Pending' and cust_id='$cust_id' order by reg_date desc";

    $response = mysqli_query($db, $query);

    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
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
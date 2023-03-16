<?php
include("../database/config.php");
$cust_id = $_POST['cust_id'];
$query = "SELECT * FROM cart WHERE cust_id='$cust_id' and status='Pending'";
$response = mysqli_query($db, $query);
if (mysqli_num_rows($response) > 0) {
    $cool = mysqli_query($db, "SELECT SUM(quantity*price) as orders from cart where cust_id='$cust_id' and status='Pending'");
    $total = mysqli_fetch_assoc($cool);
    $results['trust'] = 1;
    $results['victory'] = array();
    $index['orders'] = $total['orders'];
    $index['shipping'] = '500';
    $index['total'] = $total['orders'] + 500;
    array_push($results['victory'], $index);
    echo json_encode($results);
} else {
    $results['trust'] = 0;
    $results['mine'] = "No items";
    echo json_encode($results);
}
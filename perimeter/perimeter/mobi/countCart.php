<?php
include("../database/config.php");
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $cust_id = $_POST["cust_id"];
    $count = mysqli_query($db, "SELECT count(*) as jobb from cart where status='Pending' and serial='Pending' and cust_id='$cust_id'");
    $purpose = mysqli_fetch_assoc($count);
    if (($purpose['jobb']) >= 1) {
        $results['trust'] = 1;
        $results['victory'] = array();
        $index['counter'] = $purpose['jobb'];
        array_push($results['victory'], $index);
    } else {
        $results['trust'] = 0;
        $results['message'] = "No item";
        echo json_encode($results);
    }
    echo json_encode($results);
}
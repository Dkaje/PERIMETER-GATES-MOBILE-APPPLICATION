<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM purchase where quantity!=0 order by reg_date desc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        //pur_id,category,type,quantity,reg_date,purchase
        while ($row = mysqli_fetch_array($response)) {
            $index['pur_id'] = $row['pur_id'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['quantity'] = $row['quantity'];
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
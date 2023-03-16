<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $customer=$_POST["customer"];
    $query = "SELECT * FROM reply where customer='$customer'order by reply_date asc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;//id,customer,phone,name,reply,reply_date
        $results['victory'] = array();
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['customer'] = $row['customer'];
            $index['phone'] = $row['phone'];
            $index['name'] = $row['name'];
            $index['reply'] = $row['reply'];
            $index['reply_date'] = $row['reply_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No message";
        echo json_encode($results);
    }
    echo json_encode($results);
}
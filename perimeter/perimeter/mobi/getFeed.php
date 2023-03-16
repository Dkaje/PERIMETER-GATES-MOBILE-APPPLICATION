<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM feedback order by reg_date asc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;//id,payid,customer,phone,name,rating,message,reg_date,getFeed
        $results['victory'] = array();
        while ($row = mysqli_fetch_array($response)) {
            $index['id'] = $row['id'];
            $index['customer'] = $row['customer'];
            $index['phone'] = $row['phone'];
            $index['name'] = $row['name'];
            $index['rating'] = $row['rating'];
            $index['message'] = $row['message'];
            $index['reg_date'] = $row['reg_date'];
            $index['reply'] = $row['reply'];
            $index['reply_date'] = $row['reply_date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No feedback";
        echo json_encode($results);
    }
    echo json_encode($results);
}
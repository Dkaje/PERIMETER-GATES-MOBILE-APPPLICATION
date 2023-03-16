<?php
include("../database/config.php");
$try=mysqli_query($db,"SELECT distinct supplier as sup from stock where status='Approved'");
$y=mysqli_fetch_assoc($try);
            $huge=mysqli_query($db,"SELECT sum(quantity*price) as tot from stock where supplier='$y[sup]' and status='Approved'");
            $yes=mysqli_fetch_assoc($huge);
            $blog=mysqli_query($db,"SELECT pay as dis from stock where supplier='$y[sup]' and status='Approved'");
            $lg=mysqli_fetch_assoc($blog);
    $response=mysqli_query($db,"SELECT distinct supplier from stock where status='Approved'");
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();//supplier,payment,disburse
        while ($row = mysqli_fetch_array($response)) {
            $index['supplier'] = $row['supplier'];
            $index['payment'] = $yes['tot'];
            $index['disburse'] = $lg['dis'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No record was Found";
        echo json_encode($results);
    }
    echo json_encode($results);
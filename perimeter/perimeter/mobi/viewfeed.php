<?php
include("../database/config.php");
    $query = "SELECT distinct customer FROM feedback order by reg_date asc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        while ($row = mysqli_fetch_array($response)) {
            $index['customer'] = $row['customer'];
            $rom = mysqli_query($db, "SELECT count(*) as mes from feedback where customer='$row[customer]' and reply='Pending'");
            $push = mysqli_fetch_assoc($rom);
            $index['counter'] = $push['mes'];
            if($push['mes']!=0){
                $ma = mysqli_query($db, "SELECT max(reg_date) as mes from feedback where customer='$row[customer]' and reply='Pending'");
                $hm = mysqli_fetch_assoc($ma);
                $index['date'] = $hm['mes'];
                $em = mysqli_query($db, "SELECT message as mes from feedback where customer='$row[customer]' and reg_date='$hm[mes]' and reply='Pending'");
                $ai = mysqli_fetch_assoc($em);
                $index['message'] = $ai['mes'];
            }else{
                $ma = mysqli_query($db, "SELECT max(reg_date) as mes from feedback where customer='$row[customer]'");
                $hm = mysqli_fetch_assoc($ma);
                $index['date'] = $hm['mes'];
                $em = mysqli_query($db, "SELECT message as mes from feedback where customer='$row[customer]'");
                $ai = mysqli_fetch_assoc($em);
                $index['message'] = $ai['mes'];
            }
            
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No Record";
    }
    echo json_encode($results);
<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $query = "SELECT * FROM orders where finance='Approved' and blacksmith!='Pending' and mode='Shipped' and dispatcher='Assigned' order by date asc";
    $response = mysqli_query($db, $query);
    if (mysqli_num_rows($response) > 0) {
        $results['trust'] = 1;
        $results['victory'] = array();
        while ($row = mysqli_fetch_array($response)) {
            //order_id,mpesa,amount,ship,cost,quantity,unit,cust_id,name,phone,location,category,type,material,coating,dimension,set_date,mode,
//finance,remarks,blacksmith,dispatcher,driver,drive,customer,date
            $index['order_id'] = $row['order_id'];
            $index['mpesa'] = $row['mpesa'];
            $index['amount'] = $row['amount'];
            $index['ship'] = $row['ship'];
            $index['cost'] = $row['cost'];
            $index['quantity'] = $row['quantity'];
            $index['unit'] = $row['unit'];
            $index['cust_id'] = $row['cust_id'];
            $index['name'] = $row['name'];
            $index['phone'] = $row['phone'];
            $index['mode'] = $row['mode'];
            $index['location'] = $row['location'];
            $index['category'] = $row['category'];
            $index['type'] = $row['type'];
            $index['material'] = $row['material'];
            $index['coating'] = $row['coating'];
            $index['dimension'] = $row['dimension'];
            $index['set_date'] = $row['set_date'];
            $index['mode'] = $row['mode'];
            $index['finance'] = $row['finance'];
            $index['remarks'] = $row['remarks'];
            $index['blacksmith'] = $row['blacksmith'];
            $index['dispatcher'] = $row['dispatcher'];
            $index['driver'] = $row['driver'];
            $index['drive'] = $row['drive'];
            $index['customer'] = $row['customer'];
            $index['date'] = $row['date'];
            array_push($results['victory'], $index);
        }
    } else {
        $results['trust'] = 0;
        $results['mine'] = "No Order Record";
    }
    echo json_encode($results);
}
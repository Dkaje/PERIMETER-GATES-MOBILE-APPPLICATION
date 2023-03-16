<?php
include("../database/config.php");
$supplier = $_POST['supplier'];
$query = "SELECT * FROM disburse WHERE supplier='$supplier'";
$response = mysqli_query($db, $query);
if (mysqli_num_rows($response) > 0) {
    $results['trust'] = 1;
    $results['victory'] = array();
    while ($row = mysqli_fetch_array($response)) {
        $index['id'] = $row['id'];
        $index['ind'] = $row['ind'];
        $index['mpesa'] = $row['mpesa'];
        $index['supplier'] = $row['supplier'];
        $index['amount'] = $row['amount'];
        $index['reg_date'] = $row['reg_date'];
        array_push($results['victory'], $index);
    } //id,ind,mpesa,supplier,amount,reg_date
} else {
    $results['trust'] = 0;
    $results['mine'] = "No Record Found";
    echo json_encode($results);
}
echo json_encode($results);
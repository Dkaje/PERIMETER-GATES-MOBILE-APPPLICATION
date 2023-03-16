<?php
include("../database/config.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
     //id,ind,bank,account,cheque,supplier,amount,reg_date//disburse
    $supplier = $_POST["supplier"];
    $mpesa = $_POST["mpesa"];
    $amount = $_POST["amount"];
    $currentDateTime = date('Y-m-d H:i:s');
    $per = '4DEFGHIJKL56789ABCMNWXYZOPQR0123STUV';
    $ind = substr(str_shuffle($per), 0, 10);
    $naming = "/^[A-Z0-9]{10,}$/";
    $caps = "/^[A-Z]{10,}$/";
    $nums = "/^[0-9]{10,}$/";
    if (preg_match($caps, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have Alphabet characters alone";
    } elseif (preg_match($nums, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "A valid mpesa cannot have numerical digits alone";
    } else {
    $select = "SELECT mpesa FROM disburse WHERE mpesa='$mpesa'";
    $query = mysqli_query($db, $select);
    if (mysqli_num_rows($query) > 0) {
        $response["success"] = 0;
        $response["message"] = "MPESA not Accepted";
    } else {
            $insert = mysqli_query($db, "INSERT into disburse(ind,mpesa,supplier,amount,reg_date)
    values('$ind','$mpesa','$supplier','$amount','$currentDateTime')");
            if ($insert) {
                $sql = "UPDATE stock set pay='Paid' where supplier='$supplier' and status='Approved' and pay='Pending'";
                if (mysqli_query($db, $sql)) {
                    $response["success"] = 1;
                    $response["message"] = "Successfully Disbursed";
                } else {
                    $response["success"] = 0;
                    $response["message"] = "  Failed to render";
                }
            } else {
                $response["success"] = 0;
                $response["message"] = "An error";
            }
        }
    }
    echo json_encode($response);
    mysqli_close($db);
}
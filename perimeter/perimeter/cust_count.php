<?php
include('sessionadm.php');
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {
    $sql1 = "SELECT * from customer where status='0'";
    $query1 = $con->prepare($sql1);
    $query1->execute();
    $results1 = $query1->fetchAll(PDO::FETCH_OBJ);
    $newWriters = $query1->rowCount();
    echo $newWriters;
}
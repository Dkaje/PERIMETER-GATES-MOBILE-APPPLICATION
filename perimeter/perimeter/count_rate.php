<?php
include('sessionadm.php');
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {
    $sql2 ="SELECT id from feedback";
    $query2 = $con -> prepare($sql2);
    $query2->execute();
    $results2=$query2->fetchAll(PDO::FETCH_OBJ);
    $pendingJob=$query2->rowCount();
echo $pendingJob;
}
 ?>
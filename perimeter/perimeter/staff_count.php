<?php
include('sessionadm.php');
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {
    $status=0;
    $sql2 ="SELECT serial from staff where role!='Driver' and role!='Support Team' and  status=:status";
    $query2 = $con -> prepare($sql2);
    $query2->bindParam(':status',$status,PDO::PARAM_STR);
    $query2->execute();
    $results2=$query2->fetchAll(PDO::FETCH_OBJ);
    $pendingJob=$query2->rowCount();
echo $pendingJob;
}
 ?>
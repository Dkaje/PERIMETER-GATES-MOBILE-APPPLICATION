<?php
include("../database/config.php");
$sql = "SELECT * from driver";
if (!$db->query($sql)) {
    echo "Faied to create connection";
} else {
    $result = $db->query($sql);
    if ($result->num_rows > 0) {
        $return_arr['driver'] = array();
        while ($row = $result->fetch_array()) {
            array_push($return_arr['driver'], array(
                'driver_id' => $row['driver_id']
            ));
        }
        echo json_encode($return_arr);
    }
}
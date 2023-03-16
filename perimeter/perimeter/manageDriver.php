<?php
include('sessionadm.php');
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {
    if (isset($_GET['inid'])) {
        $id = $_GET['inid'];
        $status = 2;
        $sql = "update driver set status=:status WHERE driver_id=:id";
        $query = $con->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_STR);
        $query->bindParam(':status', $status, PDO::PARAM_STR);
        $query->execute();
        header('location:manageDriver');
    }
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $status = 1;
        $sql = "update driver set status=:status,remarks='' WHERE driver_id=:id";
        $query = $con->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_STR);
        $query->bindParam(':status', $status, PDO::PARAM_STR);
        $query->execute();
        header('location:manageDriver');
    }

    if (isset($_GET['idi'])) {
        $idi = $_GET['idi'];
        $status = 1;
        $sql = "update driver set status=:status,remarks='' WHERE driver_id=:idi";
        $query = $con->prepare($sql);
        $query->bindParam(':idi', $idi, PDO::PARAM_STR);
        $query->bindParam(':status', $status, PDO::PARAM_STR);
        $query->execute();
        header('location:manageDriver');
    }
    if (isset($_POST['rejectBtn'])) {
        $customerid = $_POST['identity'];
        $remarks = $_POST['remarks'];
        $status = 2;
        $sql = "update driver set status=:status,remarks=:remarks WHERE driver_id=:idi";
        $query = $con->prepare($sql);
        $query->bindParam(':idi', $customerid, PDO::PARAM_STR);
        $query->bindParam(':remarks', $remarks, PDO::PARAM_STR);
        $query->bindParam(':status', $status, PDO::PARAM_STR);
        $query->execute();
        header('location:manageDriver');
    }
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Perimeter Gates Bypass</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <link href="assets/css/style.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body style="height: 100%;" class="bg-primary">
    <?php include('includes/header.php'); ?>
    <div class="content-wrapper">
        <div class="container">
            <div class="row pad-botm">
                <div class="col-md-12">
                    <h5>
                        <a href="manageDriver"> <button class="btn btn-wraning"> Manage Drivers</button></a>
                        <a href="drivPending"> <button class="btn btn-secondary"> Pending</button></a>
                        <a href="drivApproved"> <button class="btn btn-success"> Approved</button></a>
                        <a href="drivRejected"> <button class="btn btn-danger"> Rejected</button></a>
                    </h5>
                </div>


            </div>
            <div class="row" style="color: #000;">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>driver_id</th>
                                            <th>Fname</th>
                                            <th>Lname </th>
                                            <th>Email</th>
                                            <th>Contact</th>
                                            <th>Remarks</th>
                                            <th>Reg Date</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php $sql = "SELECT * from driver";
                                            $query = $con->prepare($sql);
                                            $query->execute();
                                            $results = $query->fetchAll(PDO::FETCH_OBJ);
                                            $cnt = 1;
                                            if ($query->rowCount() > 0) {
                                                foreach ($results as $result) {               ?>
                                        <tr class="odd gradeX">
                                            <td class="center"><?php echo htmlentities($cnt); ?></td>
                                            <td class="center"><?php echo htmlentities($result->driver_id); ?></td>
                                            <td class="center"><?php echo htmlentities($result->fname); ?></td>
                                            <td class="center"><?php echo htmlentities($result->lname); ?></td>
                                            <td class="center"><?php echo htmlentities($result->email); ?></td>
                                            <td class="center"><?php echo htmlentities($result->contact); ?></td>
                                            <td class="center"><?php echo htmlentities($result->remarks); ?></td>
                                            <td class="center"><?php echo htmlentities($result->date); ?></td>
                                            <td class="center"><?php if ($result->status == 0) {
                                                                                echo htmlentities("Pending");
                                                                            } elseif ($result->status == 1) {
                                                                                echo htmlentities("Active");
                                                                            } else {
                                                                                echo htmlentities("Blocked");
                                                                            }
                                                                            ?></td>


                                            <td class="center">

                                                <?php if ($result->status == 1) { ?>
                                                <div class="dropdown" style="float: right;">
                                                    <button class="btn btn-danger" type="button"
                                                        data-toggle="dropdown">Deactivate</button>
                                                    <ul class="dropdown-menu alert panel-footer">
                                                        <li>
                                                            <form method="post">
                                                                <input type="hidden" name="identity"
                                                                    value="<?php echo htmlentities($result->driver_id); ?>" />
                                                                <textarea class="form-control" rows="5" name="remarks"
                                                                    placeholder="Why you are blocking this user"
                                                                    required></textarea>
                                                                <input type="submit" name="rejectBtn" value="Deactivate"
                                                                    class="btn btn-danger" />
                                                            </form>
                                                        </li>
                                                    </ul>
                                                </div>
                                                <?php } elseif ($result->status == 0) { ?>
                                                <a href="manageDriver?idi=<?php echo htmlentities($result->driver_id); ?>"
                                                    onclick="return confirm('Are you sure you want to Accept this driver?');"" >  <button class="
                                                    btn btn-primary"> Approve</button></a>
                                                <div class="dropdown" style="float: right;">
                                                    <button class="btn btn-danger" type="button"
                                                        data-toggle="dropdown">Deactivate</button>
                                                    <ul class="dropdown-menu alert panel-footer">
                                                        <li>
                                                            <form method="post">
                                                                <input type="hidden" name="identity"
                                                                    value="<?php echo htmlentities($result->driver_id); ?>" />
                                                                <textarea class="form-control" rows="5" name="remarks"
                                                                    placeholder="Why you are blocking this user"
                                                                    required></textarea>
                                                                <input type="submit" name="rejectBtn" value="Deactivate"
                                                                    class="btn btn-danger" />
                                                            </form>
                                                        </li>
                                                    </ul>
                                                </div>
                                                <?php } else { ?>

                                                <a href="manageDriver?id=<?php echo htmlentities($result->driver_id); ?>"
                                                    onclick="return confirm('Are you sure you want to activate this driver?');"><button
                                                        class="btn btn-primary"> Activate</button></a>
                                                <?php } ?>

                                            </td>
                                        </tr>
                                        <?php $cnt = $cnt + 1;
                                                }
                                            } ?>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
            </div>



        </div>
    </div>
    <script src="assets/js/jquery-1.10.2.js"></script>
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
    <script src="assets/js/custom.js"></script>
</body>

</html>
<?php } ?>
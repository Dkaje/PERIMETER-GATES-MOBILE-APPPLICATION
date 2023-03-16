<?php
include('sessionadm.php');
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {

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
                <a href="product"> <button class="btn btn-wraning"> Products</button></a>
                        <a href="stock"> <button class="btn btn-secondary"> Stock</button></a>
                        <a href="purchase"> <button class="btn btn-success"> Purchases</button></a>
                        <a href="order"> <button class="btn btn-danger"> Orders(Shipped)</button></a>
                        <a href="orderN"> <button class="btn btn-danger"> Orders(Not Shipped)</button></a>
                        <a href="cart"> <button class="btn btn-wraning"> Cart</button></a>
                        <a href="payment"> <button class="btn btn-success"> Payment(Shipped)</button></a>
                        <a href="paymentN"> <button class="btn btn-danger"> Payment(Not Shipped)</button></a>
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
                                            <th>PurchaseRef</th>
                                            <th>category</th>
                                            <th>type</th>
                                            <th>quantity</th>
                                            <th>Reg Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php $sql = "SELECT * from purchase";
                                            $query = $con->prepare($sql);
                                            $query->execute();
                                            $results = $query->fetchAll(PDO::FETCH_OBJ);
                                            $cnt = 1;
                                            if ($query->rowCount() > 0) {
                                                foreach ($results as $result) {               ?>
                                        <tr class="odd gradeX">
                                            <td class="center"><?php echo htmlentities($cnt); ?></td>
                                            <td class="center"><?php echo htmlentities($result->pur_id); ?></td>
                                            <td class="center"><?php echo htmlentities($result->category); ?></td>
                                            <td class="center"><?php echo htmlentities($result->type); ?></td>
                                            <td class="center"><?php echo htmlentities($result->quantity); ?> units</td>
                                            <td class="center"><?php echo htmlentities($result->reg_date); ?></td>
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
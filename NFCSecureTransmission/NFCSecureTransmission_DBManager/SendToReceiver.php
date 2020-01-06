<?php
    include 'DBConnection.php';
    $id_device=$_POST["id_device"];
    if($id_device!=null)
        echo "true";
    else
        echo "false";
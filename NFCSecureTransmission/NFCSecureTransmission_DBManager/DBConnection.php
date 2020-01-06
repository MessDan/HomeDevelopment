<?php
    $host="127.0.0.1";
    $port=3306;
    $user="root";
    $password="";
    $dbname="nfc_secure_transmission";

    $con = mysql_connect($host, $user, $password)
            or die ('Could not connect to the database server' . mysqli_connect_error());
    //$con->close();

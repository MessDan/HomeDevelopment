<?php
    include 'DBConnection.php';
    mysql_select_db($dbname);
    $id_device=$_POST["id_device"];
    $access_name=$_POST["access_name"];
    $loc_city=$_POST["loc_city"];
    $loc_name=$_POST["loc_name"];
    $querySel="SELECT id_tkn FROM token WHERE device_code='$id_device'";
    $res=mysql_query($querySel);
    while($row=mysql_fetch_array($res))
        $id_tkn=$row["id_tkn"];
    $querySel="SELECT id_account,account_name,account_surname,id_authorization,first_access FROM account WHERE id_token=$id_tkn";
    $res=mysql_query($querySel);
    while($row=mysql_fetch_array($res)){
        $id_account=$row["id_account"];
        $id_authorization=$row["id_authorization"];
        $name=$row["account_name"];
        $surname=$row["account_surname"];
        $first_access=$row["first_access"];
    }
    $querySel="SELECT id_group FROM user_group_list WHERE id_account=$id_account";
    $res=mysql_query($querySel);
    $user_group=array();
    for($i=0;$row=mysql_fetch_array($res);$i++)
        $user_group[$i]=$row["id_group"];
    $querySel="SELECT id_access,min_auth FROM access WHERE access_name='$access_name' AND id_location=(SELECT id_location FROM location WHERE location_name='$loc_name' AND location_city='$loc_city')";
    $res=mysql_query($querySel);
    while($row=mysql_fetch_array($res)){
        $id_access=$row["id_access"];
        $min_auth=$row["min_auth"];
    }
    $querySel="SELECT id_group FROM access_group_list WHERE id_access=$id_access";
    $res=mysql_query($querySel);
    $access_group=array();
    for($i=0;$row=mysql_fetch_array($res);$i++)
        $access_group[$i]=$row["id_group"];
    $found=false;
    for($i=0;$i<count($user_group);$i++)
        for($j=0;$j<count($access_group);$j++)
            if($user_group[$i]==$access_group[$j])
                $found=true;
    if($found==true||$min_auth>=$id_authorization){
        $time=date('d-M-Y');
        $hour=date('H:i:s');
        $file=fopen('LogFile/Log-'.$time.'.txt', 'a+');
        fwrite($file,"\r\n[$hour] - L'utente n.($id_account): $surname $name ha effettuato l'accesso attraverso il varco n.($id_access): $access_name");
        fwrite($file,"\r\n_______________________________________________________________________________________________________________________\r\n");
        fclose($file);
        $access_time=date('d/m/Y H:i');
        $queryIns="UPDATE account SET last_access='$access_time' WHERE id_account=$id_account";
        mysql_query($queryIns);
        if($first_access=='--/--/---- --:--')
            $queryIns="UPDATE account SET first_access='$access_time' WHERE id_account=$id_account";
        mysql_query($queryIns);
        echo "true";
    }
    else
        echo "false";
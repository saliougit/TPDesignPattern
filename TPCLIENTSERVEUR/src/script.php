<?php
    $login = $_REQUEST['login'];
    $password = $_REQUEST['password'];

    if ($login == "admin" && $password == "mon_password"){
        echo "true";
    }
    else{
        echo "false";

    }

?>


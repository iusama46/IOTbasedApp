<?php
if($_SERVER['REQUEST_METHOD']=='POST')
{
 $conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');
 $name= $_POST['GetName'];
 //$password=md5($_POST['GetPassword']);
 $password=$_POST['GetPassword'];
 $email=$_POST['GetEmail'];
 $phone= $_POST['GetPhone'];
 $city=$_POST['GetCity'];
 $device_id=$_POST['GetDeviceID'];
 
 
 $password = password_hash($password, PASSWORD_DEFAULT);
 $q = "insert into App_Users values (null,'$name','$password','$email','$phone','$city','$device_id',2)";
 $q2 = "insert into loginAuth values (null,'$email','$password',2,'$device_id')";
 
 if(mysqli_query($conn,$q))
 {
     if(mysqli_query($conn,$q2)){
         echo 'done';
     }
 }
    else
    {
     echo 'failed';
     }
}
?>
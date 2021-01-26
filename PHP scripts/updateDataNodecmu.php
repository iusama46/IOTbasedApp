<html>
<body>

<?php

$dbname = 'id12123509_mirzashasan95';
$dbuser = 'id12123509_mirzashasan';  
$dbpass = '123hasan456'; 
$dbhost = 'localhost'; 

$connect = @mysqli_connect($dbhost,$dbuser,$dbpass,$dbname);

if(!$connect){
	echo "Error: " . mysqli_connect_error();
	exit();
}

echo "Connection Success!<br><br>";

$endTime = $_GET["end_time"];
$session_id=$_GET["session_id"];
$duration = $_GET["duration"];


$endTemperature = $_GET["end_temperature"];
$endExhaust = $_GET["end_exhaust"];
$endVibration = $_GET["end_vibration"];
$endFlow = $_GET["end_flow"];
$endCurrent = $_GET["end_current"];
$endVoltage = $_GET["end_voltage"];

$query = "UPDATE Usage_History SET end_temperature=$endTemperature, end_exhaust=$endExhaust, duration=$duration,
end_vibration=$endVibration,end_flow=$endFlow, end_current=$endCurrent, end_voltage=$endVoltage, end_time=$endTime WHERE BINARY session_id = $session_id";
$result = mysqli_query($connect,$query);

if($result){
    echo "Data Updated";
 } else{
         echo "Failed";
     }

?>
</body>
</html>
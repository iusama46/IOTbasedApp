
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

$startTime = $_GET["start_time"];
$session_id=$_GET["session_id"];
$deviceId = $_GET["device_id"];

$startVibration = $_GET["start_vibration"]; 
$startVoltage = $_GET["start_voltage"];
$startCurrent = $_GET["start_current"];
$startTemperature = $_GET["start_temperature"]; 
$startFlow = $_GET["start_flow"];
$startExhaust = $_GET["start_exhaust"];


$query = "INSERT INTO Usage_History (session_id,device_id,start_time,start_vibration,
		start_voltage,start_current,start_temperature,start_flow,start_exhaust)
		VALUES ($session_id,$deviceId,$startTime,$startVibration,$startVoltage,$startCurrent,
		$startTemperature,$startFlow,$startExhaust)";
$result = mysqli_query($connect,$query);

if($result){
    echo "Data Inserted";
 } else{
         echo "Failed";
     }

?>
</body>
</html>
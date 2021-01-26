<?php 

	$conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');
	$checkDeviceID= $_POST['device_id'];
	$result = array();
	$result['Usage_History'] = array();
	$select= "SELECT * from Usage_History where device_id='$checkDeviceID'";
	$responce = mysqli_query($conn,$select);
	
	while($row = mysqli_fetch_array($responce))
		{
			$index['session_id']  = $row['0'];
			$index['start_time']  = $row['2'];
			$index['end_time']  = $row['3'];
			$index['duration']  = $row['4'];
			$index['start_vibration']  = $row['6'];
			$index['end_vibration']  = $row['7'];
			$index['start_current']  = $row['14'];
			$index['end_current']  = $row['15'];
			$index['start_flow']  = $row['5'];
			$index['end_flow']  = $row['16'];
			$index['start_exhaust']  = $row['10'];
			$index['end_exhaust']  = $row['11'];
			$index['start_temperature']  = $row['12'];
			$index['end_temperature']  = $row['13'];
			$index['start_voltage']  = $row['8'];
			$index['end_voltage']  = $row['9'];
			array_push($result['Usage_History'], $index);
		}
			
			$result["success"]="1";
			echo json_encode($result);
			mysqli_close($conn);

 ?>
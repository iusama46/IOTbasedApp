<?php
 $conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');

    $checkEmail= $_POST['getEmail'];
   // $checkPassword = md5($_POST['getPassword']);
    $checkPassword = $_POST['getPassword'];

	$result = array();
	$result['loginAuth'] = array();
	

    $q = "select * from loginAuth where email = '$checkEmail'";// and password = '$checkPassword'";
    
     $response = mysqli_query($conn, $q);
    
    if ( mysqli_num_rows($response) == 1 ) {
        
        $row = mysqli_fetch_assoc($response);

        if ( password_verify($checkPassword, $row['password']) ) {
            $index['device_id'] = $row['device_id'];
            $index['role'] = $row['role'];
            array_push($result['loginAuth'], $index);
            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);
            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }

    } else{
          $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);
    }


?>
<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   = "mobiles";

$conn = new mysqli($host, $user, $pass, $db);

$usuario = $_POST['Usuario'];
$password = $_POST['Password'];

$sql = "SELECT * FROM Usuarios WHERE nombre = '$usuario' AND password = '$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Basic "Token" for your app
    echo "fake-jwt-token-for-$usuario";
} else {
    http_response_code(401);
    echo "Error";
}
?>

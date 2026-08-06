const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'mobiles'
});

app.post('/api/Usuarios/login', (req, res) => {
    const { Nombre, Password } = req.body;
    const query = "SELECT * FROM Usuario WHERE nombre = ? AND password = ?";
    db.query(query, [Nombre, Password], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) {
            res.json("fake-jwt-token-for-" + Nombre);
        } else {
            res.status(401).send("Error");
        }
    });
});

app.get('/api/Usuario', (req, res) => {
    const token = req.headers['authorization'].replace('Bearer ', '');
    const user = token.replace('fake-jwt-token-for-', '');

    db.query("SELECT * FROM Usuario WHERE nombre = ?", [user], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results[0]);
        else res.status(404).send("Not found");
    });
});

app.post('/api/Usuarios/crear', (req, res) => {
    const u = req.body;
    const query = "INSERT INTO Usuario SET ?";
    db.query(query, u, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (err) res.status(500).send(err);
        else res.json({ ...u, IDUser: results.insertId });
    });
});

app.listen(3000, () => console.log("Server running on port 3000"));
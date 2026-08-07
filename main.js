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

app.get('/api/Recordatorio', (req, res) => {
    const token = req.headers['authorization'].replace('Bearer ', '');
    const recordatorio = token.replace('fake-jwt-token-for-', '');

    db.query("SELECT * FROM Recordatorio WHERE IDRec = ?", [recordatorio], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results[0]);
        else res.status(404).send("Not found");
    });
});

app.get('/api/Receta', (req, res) => {
    const token = req.headers['authorization'].replace('Bearer ', '');
    const receta = token.replace('fake-jwt-token-for-', '');

    db.query("SELECT * FROM Receta WHERE IDReceta = ?", [receta], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results[0]);
        else res.status(404).send("Not found");
    });
});

app.get('/api/Medicamento', (req, res) => {
    const token = req.headers['authorization'].replace('Bearer ', '');
    const medicamento = token.replace('fake-jwt-token-for-', '');

    db.query("SELECT * FROM Medicamento WHERE IDRec = ?", [medicamento], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results[0]);
        else res.status(404).send("Not found");
    });
});

app.get('/api/Usuarios/editar', (req, res) => {
    const token = req.headers['authorization'].replace('Bearer ', '');
    const usuario = token.replace('fake-jwt-token-for-', '');

    db.query("SELECT * FROM Usuario WHERE IDRec = ?", [usuario], (err, results) => { 
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

app.post('/api/Recordatorio/crear', (req, res) => {
    const u = req.body;
    const query = "INSERT INTO Recordatorio SET ?";
    db.query(query, u, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (err) res.status(500).send(err);
        else res.json({ ...u, IDRec: results.insertId });
    });
});

app.post('/api/Receta/crear', (req, res) => {
    const u = req.body;
    const query = "INSERT INTO Receta SET ?";
    db.query(query, u, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (err) res.status(500).send(err);
        else res.json({ ...u, IDReceta: results.insertId });
    });
});

app.post('/api/Medicamento/crear', (req, res) => {
    const u = req.body;
    const query = "INSERT INTO Medicamento SET ?";
    db.query(query, u, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (err) res.status(500).send(err);
        else res.json({ ...u, IDMedicamento: results.insertId });
    });
});

app.listen(3000, () => console.log("Server running on port 3000"));
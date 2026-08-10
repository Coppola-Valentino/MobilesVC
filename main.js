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

app.get('/api/Usuarios', (req, res) => {
    db.query("SELECT * FROM Usuario", (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results);
        else res.status(404).send("Not found");

    });
});

app.get('/api/Recetas', (req, res) => {
    db.query("SELECT * FROM Receta", (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results);
        else res.status(404).send("Not found");
    });
});

app.get('/api/Recordatorios', (req, res) => {
    db.query("SELECT * FROM Recordatorios", (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results);
        else res.status(404).send("Not found");

    });
});

app.get('/api/Medicamentos', (req, res) => {
    db.query("SELECT * FROM Medicamentos", (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.length > 0) res.json(results);
        else res.status(404).send("Not found");

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

app.put('/api/Usuarios/editar', (req, res) => {
    const usuario = req.body;
    const id = usuario.IDUser;

    const { IDUser, ...updateData } = usuario;

    const query = "UPDATE Usuario SET ? WHERE IDUser = ?";

    db.query(query, [updateData, id], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.affectedRows > 0) {
            res.json({ message: "Usuario actualizado", ...usuario });
        } else {
            res.status(404).send("Usuario no encontrado");
        }
    });
});

app.put('/api/Recordatorio/editar', (req, res) => {
    const recordatorio = req.body;
    const id = recordatorio.IDRec;

    const { IDRec, ...updateData } = recordatorio;

    const query = "UPDATE Recordatorio SET ? WHERE IDRec = ?";

    db.query(query, [updateData, id], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.affectedRows > 0) {
            res.json({ message: "Recordatorio actualizado", ...recordatorio });
        } else {
            res.status(404).send("Recordatorio no encontrado");
        }
    });
});

app.put('/api/Receta/editar', (req, res) => {
    const receta = req.body;
    const id = receta.IDReceta;

    const { IDReceta, ...updateData } = receta;

    const query = "UPDATE Receta SET ? WHERE IDReceta = ?";

    db.query(query, [updateData, id], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.affectedRows > 0) {
            res.json({ message: "Receta actualizado", ...receta });
        } else {
            res.status(404).send("Receta no encontrado");
        }
    });
});

app.put('/api/Medicamento/editar', (req, res) => {
    const medicamento = req.body;
    const id = medicamento.IDMedicamento;

    const { IDMedicamento, ...updateData } = medicamento;

    const query = "UPDATE Medicamento SET ? WHERE IDMedicamento = ?";

    db.query(query, [updateData, id], (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).send("Database Error");
        }
        if (results.affectedRows > 0) {
            res.json({ message: "Medicamento actualizado", ...medicamento });
        } else {
            res.status(404).send("Medicamento no encontrado");
        }
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
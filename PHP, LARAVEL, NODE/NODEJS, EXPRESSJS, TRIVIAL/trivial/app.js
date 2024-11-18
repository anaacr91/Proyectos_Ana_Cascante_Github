const express = require('express');
const app = express();
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const axios = require('axios');

const connectDB = require('./db/db');
connectDB();

const User = require('./models/User');
const Pregunta = require('./models/Pregunta'); 

const authenticateToken = require('./middleware/authenticateToken');
const requireAuth = require('./middleware/requireAuth');

const SALT_ROUNDS = 10;

app.use(express.json()); 

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor escuchando en el puerto ${PORT}`);
});



// Endpoint de registro
app.post('/register', async (req, res) => {

    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ message: 'Faltan parámetros necesarios.' });
    }

    if (!username || typeof username !== 'string' || !/^[a-zA-Z0-9]{4,20}$/.test(username)) {
        return res.status(400).json({ message: 'El nombre de usuario debe ser alfanumérico y tener entre 4 y 20 caracteres.' });
    }

    if (
        !password ||
        typeof password !== 'string' ||
        !/^[a-zA-Z]\w{5,15}$/.test(password)
    ) {
        return res.status(400).json({
            message: 'La contraseña debe comenzar con una letra, tener entre 6 y 16 caracteres, y solo contener letras, números y guiones bajos.',
        });
    }

    const existingUser = await User.findOne({ $or: [{ username }] });

    if (existingUser) {
        return res.status(400).json({ message: 'El nombre de usuario  ya están en uso.' });
    }

    const hashedPassword = await bcrypt.hash(password, SALT_ROUNDS);
    const newUser = new User({ username, password: hashedPassword });


    try {
        await newUser.save();


        const token = jwt.sign(
            { userId: newUser._id, username: newUser.username },
            process.env.JWT_KEY,
            { expiresIn: '8h' }
        );

        return res.status(201).json({ token });
    } catch (error) {
        return res.status(500).json({ message: 'Error al crear el usuario.' + error });
    }
});
// Endpoint de login
app.post('/login', async (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ message: 'Faltan parámetros necesarios. Proporcione username y password.' });
    }

    const user = await User.findOne({ username });

    if (!user) {
        return res.status(401).json({ message: 'Credenciales inválidas. Usuario no encontrado.' });
    }

    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
        return res.status(401).json({ message: 'Credenciales inválidas. Contraseña incorrecta.' });
    }

    const token = jwt.sign(
        { userId: user._id, username: user.username },
        process.env.JWT_KEY,
        { expiresIn: '8h' } 
    );

    return res.status(200).json({ token });
});
// Endpoint para obtener numero de preguntas
app.get('/preguntas/:numero', authenticateToken, async (req, res) => {
    const { numero } = req.params;

    const numeroDePreguntas = parseInt(numero); 

    if (numeroDePreguntas <= 0) {
        return res.status(400).json({ message: 'El parámetro "numero" debe ser un entero positivo.' });
    }


    try {

        const response = await axios.get(`https://opentdb.com/api.php?amount=${numeroDePreguntas}`);

        if (req.isLoggedIn) {
            const preguntas = response.data.results;
            const preguntasGuardadas = [];

            for (const pregunta of preguntas) {
                const nuevaPregunta = new Pregunta({
                    type: pregunta.type,
                    difficulty: pregunta.difficulty,
                    category: pregunta.category,
                    question: pregunta.question,
                    correct_answer: pregunta.correct_answer,
                    incorrect_answers: pregunta.incorrect_answers,
                });

                await nuevaPregunta.save();
                preguntasGuardadas.push(nuevaPregunta);
            }


        }

        return res.status(200).json(response.data);
    } catch (error) {
        console.error('Error al obtener preguntas:', error);
        return res.status(500).json({ message: 'Error al obtener preguntas.' });
    }
});
// Endpoint para crear una nueva pregunta
app.post('/pregunta/create', requireAuth, async (req, res) => {
    const { type, difficulty, category, question, correct_answer, incorrect_answers } = req.body;

    if (!type || !difficulty || !category || !question || !correct_answer || !incorrect_answers || !Array.isArray(incorrect_answers)) {
        return res.status(400).json({ message: 'Faltan parámetros necesarios o incorrect_answers no es un array.' });
    }

    if (incorrect_answers.includes(correct_answer)) {
        return res.status(400).json({ message: 'correct_answer no puede estar en incorrect_answers.' });
    }

    const preguntaExistente = await Pregunta.findOne({ question });

    if (preguntaExistente) {
        return res.status(400).json({ message: 'Ya existe una pregunta con esa pregunta.' });
    }

    try {

        const nuevaPregunta = new Pregunta({
            type,
            difficulty,
            category,
            question,
            correct_answer,
            incorrect_answers,
        });

        await nuevaPregunta.save();

        return res.status(201).json({ message: 'Pregunta creada con éxito.', pregunta: nuevaPregunta });
    } catch (error) {
        console.error('Error al crear la pregunta:', error);
        return res.status(500).json({ message: 'Error al crear la pregunta.' });
    }
});
// Endpoint para actualizar una pregunta por su ID
app.put('/pregunta/:id', requireAuth, async (req, res) => {
    const { id } = req.params; 
    const { type, difficulty, category, question, correct_answer, incorrect_answers } = req.body;

    if (!type && !difficulty && !category && !question && !correct_answer && !incorrect_answers) {
        return res.status(400).json({ message: 'Debe proporcionar al menos un campo para actualizar.' });
    }

    try {
        const pregunta = await Pregunta.findById(id);

        if (!pregunta) {
            return res.status(404).json({ message: 'Pregunta no encontrada.' });
        }

        if (type) pregunta.type = type;
        if (difficulty) pregunta.difficulty = difficulty;
        if (category) pregunta.category = category;
        if (question) pregunta.question = question;
        if (correct_answer) pregunta.correct_answer = correct_answer;
        if (incorrect_answers && Array.isArray(incorrect_answers)) {
            pregunta.incorrect_answers = incorrect_answers;
        }

        await pregunta.save();

        return res.status(200).json({ message: 'Pregunta actualizada con éxito.', pregunta });
    } catch (error) {
        console.error('Error al actualizar la pregunta:', error);
        return res.status(500).json({ message: 'Error al actualizar la pregunta.' });
    }
});
// Endpoint para eliminar una pregunta por ID
app.delete('/pregunta/:id', requireAuth, async (req, res) => {
    const { id } = req.params; 

    try {
        const pregunta = await Pregunta.findByIdAndDelete(id);
        if (!pregunta) {
            return res.status(404).json({ message: 'Pregunta no encontrada.' });
        }

        return res.status(200).json({ message: 'Pregunta eliminada con éxito.' });
    } catch (error) {
        console.error('Error al eliminar la pregunta:', error);
        return res.status(500).json({ message: 'Error al eliminar la pregunta.' });
    }
});
// Endpoint para obtener preguntas con filtros
app.get('/preguntasDB/filtros', requireAuth, async (req, res) => {
    const { category, type, difficulty } = req.query;

    const filters = {};

    if (category) {
        filters.category = category;
    }

    if (type) {
        filters.type = type;
    }

    if (difficulty) {
        filters.difficulty = difficulty;
    }

    try {
        const preguntas = await Pregunta.find(filters); 
        return res.status(200).json({ preguntas });
    } catch (error) {
        console.error('Error al obtener preguntas:', error);
        return res.status(500).json({ message: 'Error al obtener preguntas.' });
    }
});
// Endpoint GET para obtener información de una pregunta por ID
app.get('/preguntasDB/:id', requireAuth, async (req, res) => {
    const { id } = req.params;

    try {
        const pregunta = await Pregunta.findById(id);

        if (!pregunta) {
            return res.status(404).json({ message: 'Pregunta no encontrada.' });
        }

        return res.status(200).json({ pregunta });
    } catch (error) {
        console.error('Error al obtener la pregunta:', error);
        return res.status(500).json({ message: 'Error al obtener la pregunta.' });
    }
});
//Endpoint para obtener todas las preguntas
app.get('/preguntasDB', async (req, res) => {
    try {
        const preguntas = await Pregunta.find(); 
        return res.status(200).json({ preguntas });
    } catch (error) {
        console.error('Error al obtener preguntas:', error);
        return res.status(500).json({ message: 'Error al obtener preguntas.' });
    }
});
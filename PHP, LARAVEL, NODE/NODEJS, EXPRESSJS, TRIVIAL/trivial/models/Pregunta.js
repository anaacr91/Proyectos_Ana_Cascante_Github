const mongoose = require('mongoose');

const preguntaSchema = new mongoose.Schema({
    type: { type: String, required: true },
    difficulty: { type: String, required: true },
    category: { type: String, required: true },
    question: { type: String, required: true, unique: true },
    correct_answer: { type: String, required: true },
    incorrect_answers: { type: [String], required: true },
    created_at: { type: Date, default: Date.now },
});

const Pregunta = mongoose.model('Pregunta', preguntaSchema);

module.exports = Pregunta;
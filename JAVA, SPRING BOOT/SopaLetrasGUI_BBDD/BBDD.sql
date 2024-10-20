CREATE TABLE IF NOT EXISTS partidas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tablero TEXT NOT NULL,
    palabras_a_encontrar TEXT NOT NULL,
    palabras_encontradas TEXT NOT NULL,
    terminado BOOLEAN NOT NULL

);
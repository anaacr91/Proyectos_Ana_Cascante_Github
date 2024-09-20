<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Temporadas;

class TemporadasController extends Controller{
    

    public function show(string $id){
        $temporada = Temporadas::find($id);

        return view('temporada', [
            'temporada' => $temporada,
        ]);
    }

    public function showAll(){
        $temporadas = Temporadas::all();

        return view('temporadas', [
            'temporadas' => $temporadas,
        ]);
    }

    public function partidos(string $id){
        $partidos = Temporadas::find($id)->partidos;


        return view('partidos', [
            'temporada' => Temporadas::find($id),
            'partidos' => $partidos,
        ]);
    }


}

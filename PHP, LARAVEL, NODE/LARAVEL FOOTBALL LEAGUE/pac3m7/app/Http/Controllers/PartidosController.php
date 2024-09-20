<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Temporadas;
use App\Models\Partidos;

class PartidosController extends Controller{
    

    public function index(){
        $partidos = Partidos::all();
        return view('partidos', ['partidos' => $partidos]);
    }

    public function show(string $id){
        $partido = Partidos::find($id);
        return view('partido', [
            'partido' => $partido,
        ]);
    }

    public function create($temporada){
        $temporadas = Temporadas::all();

        return view('createPartidos', [
            'temporadaObligatoria' => $temporada,
            'temporadas' => $temporadas,
        ]);
    }

    public function store(Request $request){

        if (!isset($request->temporada) || empty($request->temporada)) {
            return redirect()->back()->withErrors(['temporada' => 'El campo temporada es obligatorio.'])->withInput();
        }

        if (!isset($request->equipo_local) || empty($request->equipo_local)) {
            return redirect()->back()->withErrors(['equipo_local' => 'El campo equipo local es obligatorio.'])->withInput();
        }

        if (!isset($request->equipo_visitante) || empty($request->equipo_visitante)) {
            return redirect()->back()->withErrors(['equipo_visitante' => 'El campo equipo visitante es obligatorio.'])->withInput();
        }

        if (!isset($request->goles_local)) {
            return redirect()->back()->withErrors(['goles_local' => 'El campo goles local es obligatorio.'])->withInput();
        }

        if (!isset($request->goles_visitante)) {
            return redirect()->back()->withErrors(['goles_visitante' => 'El campo goles visitante es obligatorio.'])->withInput();
        }

        if (!isset($request->fecha) || empty($request->fecha)) {
            return redirect()->back()->withErrors(['fecha' => 'El campo fecha es obligatorio.'])->withInput();
        }


        $partido = new Partidos();
        $partido->temporadas_id = $request->temporada;
        $partido->equipo_local = $request->equipo_local;
        $partido->equipo_visitante = $request->equipo_visitante;
        $partido->goles_local = $request->goles_local;
        $partido->goles_visitante = $request->goles_visitante;
        $partido->fecha = $request->fecha;

        $partido->save();

        return redirect()->route('partidos.index');
    }


    public function edit(Partidos $partido){

        $temporadas = Temporadas::all();

        return view('editPartido', ['partido' => $partido, 'temporadas' => $temporadas]);
    }

    public function update(Request $request, Partidos $partido){

        if (!isset($request->temporada) || empty($request->temporada)) {
            return redirect()->back()->withErrors(['temporada' => 'El campo temporada es obligatorio.'])->withInput();
        }

        if (!isset($request->equipo_local) || empty($request->equipo_local)) {
            return redirect()->back()->withErrors(['equipo_local' => 'El campo equipo local es obligatorio.'])->withInput();
        }

        if (!isset($request->equipo_visitante) || empty($request->equipo_visitante)) {
            return redirect()->back()->withErrors(['equipo_visitante' => 'El campo equipo visitante es obligatorio.'])->withInput();
        }

        if (!isset($request->goles_local)) {
            return redirect()->back()->withErrors(['goles_local' => 'El campo goles local es obligatorio.'])->withInput();
        }

        if (!isset($request->goles_visitante)) {
            return redirect()->back()->withErrors(['goles_visitante' => 'El campo goles visitante es obligatorio.'])->withInput();
        }

        if (!isset($request->fecha) || empty($request->fecha)) {
            return redirect()->back()->withErrors(['fecha' => 'El campo fecha es obligatorio.'])->withInput();
        }

        $partido->update($request->all());
        return redirect()->route('partidos.index')->with('success', 'Partido creado exitosamente.');
    }


    public function destroy(Partidos $partido)
    {
        $partido->delete();
        return redirect()->route('partidos.index')->with('success', '¡El partido ha sido eliminado correctamente!');
    }
}

<?php

use App\Http\Controllers\PartidosController;
use App\Http\Controllers\TemporadasController;

use Illuminate\Support\Facades\Route;
use App\Models\Temporadas;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});




Route::get('/partidos/{id}', [PartidosController::class, 'show']);
Route::get('/temporadas/{id}', [TemporadasController::class, 'show']);
Route::get('/temporadas', [TemporadasController::class, 'showAll']);
Route::get('/temporadas/{id}/partidos', [TemporadasController::class, 'partidos']);
Route::get('/partidos/create/{temporada}', [PartidosController::class, 'create']);

Route::post('/partidos', [PartidosController::class, 'store'])->name('partidos.store');

Route::get('/partidos/{partido}/edit', [PartidosController::class, 'edit'])->name('partidos.edit');
Route::put('/partidos/{partido}', [PartidosController::class, 'update'])->name('partidos.update');

Route::delete('/partidos/{partido}', [PartidosController::class, 'destroy'])->name('partidos.destroy');



Route::get('/partidos', [PartidosController::class, 'index'])->name('partidos.index');

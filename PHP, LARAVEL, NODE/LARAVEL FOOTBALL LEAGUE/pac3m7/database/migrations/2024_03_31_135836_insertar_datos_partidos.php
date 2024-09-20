<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration{
    /**
     * Run the migrations.
     */
    public function up(): void
    {


        DB::table('partidos')->insert([
            'equipo_local' => 'Real Madrid',
            'equipo_visitante' => 'Barcelona',
            'goles_local' => 3,
            'goles_visitante' => 1,
            'fecha' => '2019-03-31 15:00:00',
            'temporadas_id' => 1,
        ]);

        DB::table('partidos')->insert([
            'equipo_local' => 'Sevilla',
            'equipo_visitante' => 'Betis',
            'goles_local' => 2,
            'goles_visitante' => 2,
            'fecha' => '2020-05-01 20:00:00',
            'temporadas_id' => 2,
        ]);

        DB::table('partidos')->insert([
            'equipo_local' => 'Atletico de Madrid',
            'equipo_visitante' => 'Valencia',
            'goles_local' => 1,
            'goles_visitante' => 1,
            'fecha' => '2022-10-15 18:00:00',
            'temporadas_id' => 3,
        ]);

        DB::table('partidos')->insert([
            'equipo_local' => 'Real Sociedad',
            'equipo_visitante' => 'Athletic de Bilbao',
            'goles_local' => 0,
            'goles_visitante' => 0,
            'fecha' => '2023-12-25 15:00:00',
            'temporadas_id' => 4,
        ]);
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void{
        //
    }
};

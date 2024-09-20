<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;



return new class extends Migration{
    /**
     * Run the migrations.
     */
    public function up(): void {
        Schema::table('partidos', function ($table) {
            $table->integer('temporadas_id');
        });

        // Insertar datos en la tabla temporadas
        DB::table('temporadas')->insert([
            'fecha_inicio' => 2019,
            'fecha_fin' => 2020,

        ]);

        DB::table('temporadas')->insert([
            'fecha_inicio' => 2020,
            'fecha_fin' => 2021,
        ]);

        DB::table('temporadas')->insert([
            'fecha_inicio' => 2022,
            'fecha_fin' => 2023,
        ]);

        DB::table('temporadas')->insert([
            'fecha_inicio' => 2023,
            'fecha_fin' => 2024,
        ]);
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        //
    }
};

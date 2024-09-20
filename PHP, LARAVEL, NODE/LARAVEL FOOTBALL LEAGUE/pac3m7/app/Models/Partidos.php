<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Partidos extends Model
{
    use HasFactory;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'equipo_local',
        'equipo_visitante',
        'goles_local',
        'goles_visitante',
        'fecha',
    ];

    public function temporada()
    {
        return $this->belongsTo(Temporadas::class);
    }
}

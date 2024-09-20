<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Temporadas extends Model
{
    use HasFactory;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'fecha_inicio',
        'fecha_fin',
    ];

    public function partidos()
    {
        return $this->hasMany(Partidos::class)->orderBy('fecha', 'asc');
    }
}

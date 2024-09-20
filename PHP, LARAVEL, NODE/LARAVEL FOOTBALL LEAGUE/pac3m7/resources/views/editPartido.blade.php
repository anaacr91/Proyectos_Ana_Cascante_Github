<body>
    <center>

        <form method="POST" action="{{ route('partidos.update', $partido->id) }}">
            @csrf
            @method('PUT')
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h1>Editar partido</h1>
                    </div>

                    <div class="col-12">
                        <label for="temporada">Temporada</label>
                        <select name="temporada" class="form-control">
                            @foreach ($temporadas as $temporada)
                            <option value="{{ $temporada->id }}" {{ $temporada->id == $partido->temporadas_id ? 'selected' : '' }}>
                                {{ $temporada->fecha_inicio }} / {{ $temporada->fecha_fin }}
                            </option>
                            @endforeach
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="equipo_local">Equipo Local</label>
                        <input type="text" name="equipo_local" value="{{ $partido->equipo_local }}" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="equipo_visitante">Equipo Visitante</label>
                        <input type="text" name="equipo_visitante" value="{{ $partido->equipo_visitante }}" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_local">Goles Local</label>
                        <input type="number" name="goles_local" value="{{ $partido->goles_local }}" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_visitante">Goles Visitante</label>
                        <input type="number" name="goles_visitante" value="{{ $partido->goles_visitante }}" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="fecha">Fecha</label>
                        <input type="datetime-local" name="fecha" value="{{ $partido->fecha }}" class="form-control">
                    </div>

                    <div class="col-12">
                        <br>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>

                    @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                    @endif
                </div>
            </div>
        </form>
    </center>
</body>
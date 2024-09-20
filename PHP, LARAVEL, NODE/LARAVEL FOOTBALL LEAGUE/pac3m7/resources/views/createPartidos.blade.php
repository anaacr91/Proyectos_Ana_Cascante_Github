<body>
    <center>
        <form method="POST" action="{{ route('partidos.store') }}">
            @csrf
            <div class=" container">
                <div class="row">
                    <div class="col-12">
                        <h1>Crear partidos</h1>
                    </div>

                    <div class="col-md-12">
                        <label for="temporada">Temporada</label>


                        <select name="temporadaselect" id="temporada" class="form-control" disabled>
                            @foreach ($temporadas as $tempo)
                            <option value="{{ $tempo->id }}" {{$tempo->id == $temporadaObligatoria ? 'selected' : ''}}>
                                {{ $tempo->fecha_inicio }} /
                                {{ $tempo->fecha_fin }}
                            </option>
                            @endforeach
                        </select>

                        <input type="hidden" name="temporada" value="{{ $temporadaObligatoria }}">

                    </div>
                    <div class="col-md-6">
                        <label for="equipo_local">Equipo Local</label>
                        <input type="text" name="equipo_local" id="equipo_local" class="form-control"
                            value="{{ old('equipo_local') }}">
                    </div>
                    <div class="col-md-6">
                        <label for="equipo_visitante">Equipo Visitante</label>
                        <input type="text" name="equipo_visitante" id="equipo_visitante" class="form-control"
                            value="{{ old('equipo_visitante') }}">
                    </div>



                    <div class="col-md-6">
                        <label for="goles_local">Goles Local</label>
                        <input type="number" name="goles_local" id="goles_local" class="form-control"
                            value="{{ old('goles_local') }}">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_visitante">Goles Visitante</label>
                        <input type="number" name="goles_visitante" id="goles_visitante" class="form-control"
                            value="{{ old('goles_visitante') }}">
                    </div>


                    <div class="col-md-6">
                        <label for="fecha">Fecha</label>
                        <input type="datetime-local" name="fecha" id="fecha" class="form-control"
                            value="{{ old('fecha') }}">
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
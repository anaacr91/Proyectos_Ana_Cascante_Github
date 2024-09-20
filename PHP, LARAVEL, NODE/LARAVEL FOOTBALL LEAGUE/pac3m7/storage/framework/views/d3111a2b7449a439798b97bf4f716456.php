<body>
    <center>
        <form method="POST" action="<?php echo e(route('partidos.store')); ?>">
            <?php echo csrf_field(); ?>
            <div class=" container">
                <div class="row">
                    <div class="col-12">
                        <h1>Crear partidos</h1>
                    </div>

                    <div class="col-md-12">
                        <label for="temporada">Temporada</label>


                        <select name="temporadaselect" id="temporada" class="form-control" disabled>
                            <?php $__currentLoopData = $temporadas; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $tempo): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                            <option value="<?php echo e($tempo->id); ?>" <?php echo e($tempo->id == $temporadaObligatoria ? 'selected' : ''); ?>>
                                <?php echo e($tempo->fecha_inicio); ?> /
                                <?php echo e($tempo->fecha_fin); ?>

                            </option>
                            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                        </select>

                        <input type="hidden" name="temporada" value="<?php echo e($temporadaObligatoria); ?>">

                    </div>
                    <div class="col-md-6">
                        <label for="equipo_local">Equipo Local</label>
                        <input type="text" name="equipo_local" id="equipo_local" class="form-control"
                            value="<?php echo e(old('equipo_local')); ?>">
                    </div>
                    <div class="col-md-6">
                        <label for="equipo_visitante">Equipo Visitante</label>
                        <input type="text" name="equipo_visitante" id="equipo_visitante" class="form-control"
                            value="<?php echo e(old('equipo_visitante')); ?>">
                    </div>



                    <div class="col-md-6">
                        <label for="goles_local">Goles Local</label>
                        <input type="number" name="goles_local" id="goles_local" class="form-control"
                            value="<?php echo e(old('goles_local')); ?>">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_visitante">Goles Visitante</label>
                        <input type="number" name="goles_visitante" id="goles_visitante" class="form-control"
                            value="<?php echo e(old('goles_visitante')); ?>">
                    </div>


                    <div class="col-md-6">
                        <label for="fecha">Fecha</label>
                        <input type="datetime-local" name="fecha" id="fecha" class="form-control"
                            value="<?php echo e(old('fecha')); ?>">
                    </div>


                    <div class="col-12">
                        <br>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>

                    <?php if($errors->any()): ?>
                    <div class="alert alert-danger">
                        <ul>
                            <?php $__currentLoopData = $errors->all(); $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $error): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                            <li><?php echo e($error); ?></li>
                            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                        </ul>
                    </div>
                    <?php endif; ?>
                </div>
            </div>

        </form>
    </center>
</body><?php /**PATH /Users/carlos/Documents/pac3m7/resources/views/createPartidos.blade.php ENDPATH**/ ?>
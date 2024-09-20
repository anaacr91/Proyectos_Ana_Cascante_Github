<body>
    <center>

        <form method="POST" action="<?php echo e(route('partidos.update', $partido->id)); ?>">
            <?php echo csrf_field(); ?>
            <?php echo method_field('PUT'); ?>
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h1>Editar partido</h1>
                    </div>

                    <div class="col-12">
                        <label for="temporada">Temporada</label>
                        <select name="temporada" class="form-control">
                            <?php $__currentLoopData = $temporadas; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $temporada): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                            <option value="<?php echo e($temporada->id); ?>" <?php echo e($temporada->id == $partido->temporadas_id ? 'selected' : ''); ?>>
                                <?php echo e($temporada->fecha_inicio); ?> / <?php echo e($temporada->fecha_fin); ?>

                            </option>
                            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="equipo_local">Equipo Local</label>
                        <input type="text" name="equipo_local" value="<?php echo e($partido->equipo_local); ?>" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="equipo_visitante">Equipo Visitante</label>
                        <input type="text" name="equipo_visitante" value="<?php echo e($partido->equipo_visitante); ?>" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_local">Goles Local</label>
                        <input type="number" name="goles_local" value="<?php echo e($partido->goles_local); ?>" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="goles_visitante">Goles Visitante</label>
                        <input type="number" name="goles_visitante" value="<?php echo e($partido->goles_visitante); ?>" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label for="fecha">Fecha</label>
                        <input type="datetime-local" name="fecha" value="<?php echo e($partido->fecha); ?>" class="form-control">
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
</body><?php /**PATH /Users/carlos/Documents/pac3m7/resources/views/editPartido.blade.php ENDPATH**/ ?>
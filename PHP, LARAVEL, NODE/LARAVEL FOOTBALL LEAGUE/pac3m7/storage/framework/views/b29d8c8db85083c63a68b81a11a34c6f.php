<center>
    <div class="relative sm:flex sm:justify-center sm:items-center min-h-screen bg-dots-darker bg-center bg-gray-100 dark:bg-dots-lighter dark:bg-gray-900 selection:bg-red-500 selection:text-white">
        <div class="sm:w-1/2">
            <div class="flex flex-col items-center justify-center space-y-4">
                <h1 class="text-4xl font-bold text-center text-gray-800 dark:text-gray-100">Temporadas</h1>
                <table class="w-full p-4 bg-white border border-gray-200 dark:bg-gray-800 dark:border-gray-700 rounded-lg shadow-md">
                    <thead>
                        <tr>
                            <th class="p-4 text-sm font-semibold text-gray-500 dark:text-gray-300 text-center">ID</th>
                            <th class="p-4 text-sm font-semibold text-gray-500 dark:text-gray-300 text-center">Fecha de
                                inicio</th>
                            <th class="p-4 text-sm font-semibold text-gray-500 dark:text-gray-300 text-center">Fecha de
                                fin</th>
                            <th class="p-4 text-sm font-semibold text-gray-500 dark:text-gray-300 text-center">Acciones
                            </th>
                        </tr>
                    </thead>
                    <tbody>


                        <?php $__currentLoopData = $temporadas; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $temporada): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                        <tr>

                            <td class="p-4 text-sm text-gray-800 dark:text-gray-100 text-center"><?php echo e($temporada->id); ?>

                            </td>
                            <td class="p-4 text-sm text-gray-800 dark:text-gray-100 text-center">
                                <?php echo e($temporada->fecha_inicio); ?>

                            </td>
                            <td class="p-4 text-sm text-gray-800 dark:text-gray-100 text-center">
                                <?php echo e($temporada->fecha_fin); ?>

                            </td>
                            <td class="text-center">
                                <a href="<?php echo e(url('/temporadas/' . $temporada->id . '/partidos')); ?>" class="btn btn-warning">Ver</a>
                            </td>

                        </tr>

                        <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    </div>
</center><?php /**PATH C:\Users\LSC72\Desktop\Documents\ANA\M07 Backend PHP\pac3\pac3m7\resources\views/temporadas.blade.php ENDPATH**/ ?>
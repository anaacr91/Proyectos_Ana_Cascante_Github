<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <style>
        .negativo {
            background-color: red;
        }

        td {
            height: 30px;
            width: 30px;
        }
    </style>
    <?php
    /**
     * genera num rand
     */
    function generateRandomN()
    {
        return rand(-10, 10);
    }

    echo "<table>";
    for ($i = 0; $i < 10; $i++) {
        echo "<tr>";
        for ($j = 0; $j < 10; $j++) {
            $numRan = generateRandomN();
            $clase ="" ;
            if ($numRan <0) {
                $clase = "negativo";             
            }
            echo "<td class='".$clase."' >".$numRan."</td>";
        }
        echo "</tr>";
    }
    echo "</table>";
    ?>
</body>

</html>
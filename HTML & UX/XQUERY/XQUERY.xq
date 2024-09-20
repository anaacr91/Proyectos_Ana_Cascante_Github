(: TIP: BaseX sólo puede ejectuar una instrucción por hoja, para comentar y descomentar código rápidamente, usar Ctrl+K :)

(: XQUERY :)

(: a)  for $jugador in //playerStats/playerName[.='Marc Gasol']  return data(//playerStats[playerName=data($jugador)]/gamesPlayed) :)

(: b) for $jugador in //player[birthState='Spain']  
let $nombre:=data($jugador/playerName)
, $ciudad:=data($jugador/birthCity)
return concat('El jugador ', $nombre, ' ha nacido en ', $ciudad)
 :)

(: c) for $jugador in //playerStats[team='MIA']  
let $nombre:=data($jugador/playerName),
$posicion:=data($jugador/position),
$partidos:=data($jugador/gamesPlayed),
$puntos:=data($jugador/points)
return concat( $nombre, ' ' , $posicion ,' ' , $partidos ,' ' , $puntos)
 :)

(: d) for $jugador in //playerStats[playerName='Kyle Korver']  
let $equipo:=data($jugador/team),
$partidos:=data($jugador/gamesPlayed)
order by $partidos 
return concat( $equipo, ' ' , $partidos)
 :)

(: e) for $jugador in //playerStats 
where $jugador/points = max (for $player in //playerStats/points return $player)
return $jugador
 :)

(: f) for $name in distinct-values (/NBAPlayersStats/playerStats/playerName) 
for $nombre in (/NBAPlayers/player/playerName)
let $puntos:= sum(for $stats in /NBAPlayersStats/playerStats where $name=$stats/playerName return $stats/points)
where $name=$nombre
return if ($puntos>=500) 
then concat ($nombre, ' ', $puntos ) 
else ()
 :)

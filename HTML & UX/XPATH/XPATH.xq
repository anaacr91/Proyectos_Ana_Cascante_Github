(: TIP: BaseX sólo puede ejectuar una instrucción por hoja, para comentar y descomentar código rápidamente, usar Ctrl+K :)

(: XPATH :)

(: a) /NBAPlayers/player[playerName[contains(text(),'Gasol')]] :)

(: b) count (/NBAPlayers/player[bornYear>1996]) :)

(: c)/NBAPlayers/player[playerName='Michael Jordan']/birthCity/text() :)

(: d)distinct-values (/NBAPlayers/player[bornYear>=1980 and 190>height and 180<height]/college/text()) :)

(: e) string(/NBAPlayers/player[last()-1]/@id) :)

(: f) avg (/NBAPlayers/player[birthState="Minnesota"]/weight/text()) :)
